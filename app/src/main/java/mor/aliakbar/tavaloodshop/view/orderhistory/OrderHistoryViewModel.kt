package mor.aliakbar.tavaloodshop.view.orderhistory

import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.dataclass.TokenContainer
import mor.aliakbar.tavaloodshop.model.repository.OrderRepository
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowSetProgressBar
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(val orderRepository: OrderRepository) :
    BaseViewModel() {

    val orderHistories = liveData {
        if (TokenContainer.token != null)
            orderRepository.list()
                .flowCatch()
                .flowSetProgressBar(progressbarStatusLiveData)
                .collect {
                    emit(it)
                }
        else emit(null)
    }

}