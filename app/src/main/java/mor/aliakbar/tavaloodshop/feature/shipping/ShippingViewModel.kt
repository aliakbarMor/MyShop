package mor.aliakbar.tavaloodshop.feature.shipping

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.dataclass.PurchaseDetail
import mor.aliakbar.tavaloodshop.model.dataclass.SubmitOrderResult
import mor.aliakbar.tavaloodshop.model.dataclass.UserInformation
import mor.aliakbar.tavaloodshop.model.repository.OrderRepository
import mor.aliakbar.tavaloodshop.model.repository.UserRepository
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowSetProgressBar
import mor.aliakbar.tavaloodshop.utils.Variable
import javax.inject.Inject

@HiltViewModel
class ShippingViewModel @Inject constructor(
    state: SavedStateHandle,
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
) : BaseViewModel() {

    val submitOrderStatus = MutableLiveData<SubmitOrderResult>()
    val purchaseDetail = liveData {
        emit(state.get<PurchaseDetail>(Variable.EXTRA_KEY_DATA))
    }
    var userInformation = liveData {
        emit(userRepository.getUserInformation())
    }


    fun submitOrder(userInformation: UserInformation, paymentMethod: String) {
        viewModelScope.launch {
            orderRepository.submit(userInformation, paymentMethod)
                .flowCatch()
                .flowSetProgressBar(progressbarStatusLiveData)
                .collect {
                    submitOrderStatus.value = it
                }
        }
    }

}