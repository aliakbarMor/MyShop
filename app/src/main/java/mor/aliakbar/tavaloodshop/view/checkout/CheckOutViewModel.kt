package mor.aliakbar.tavaloodshop.view.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.dataclass.PaymentResult
import mor.aliakbar.tavaloodshop.model.repository.OrderRepository
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowSetProgressBar
import mor.aliakbar.tavaloodshop.utils.Variable
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(
    state: SavedStateHandle,
    private val orderRepository: OrderRepository
) : BaseViewModel() {

    val orderId = MutableLiveData(state.get<Int>(Variable.EXTRA_KEY_ID))
    val paymentResult = MutableLiveData<PaymentResult>()

    fun checkResult() {
        viewModelScope.launch {
            orderRepository.paymentResult(orderId.value!!)
                .flowCatch()
                .flowSetProgressBar(progressbarStatusLiveData)
                .collect {
                    paymentResult.value = it
                }
        }
    }

    fun setOrderIdPaymentResult(orderIdPayment: Int) {
        orderId.value = orderIdPayment
    }

}