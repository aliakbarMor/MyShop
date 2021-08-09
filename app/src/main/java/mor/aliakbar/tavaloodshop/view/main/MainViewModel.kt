package mor.aliakbar.tavaloodshop.view.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.dataclass.TokenContainer
import mor.aliakbar.tavaloodshop.model.repository.CartRepository
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var cartRepository: CartRepository) : BaseViewModel() {

    fun getCartItemsCount() {
        if (!TokenContainer.token.isNullOrEmpty()) {
            viewModelScope.launch {
                cartRepository.getCartItemCount()
                    .flowCatch()
                    .collect {
                        EventBus.getDefault().postSticky(it)
                    }
            }
        }
    }

}