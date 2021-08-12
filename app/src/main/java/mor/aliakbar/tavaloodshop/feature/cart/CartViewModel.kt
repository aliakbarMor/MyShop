package mor.aliakbar.tavaloodshop.feature.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.dataclass.*
import mor.aliakbar.tavaloodshop.model.repository.CartRepository
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowSetProgressBar
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private var cartRepository: CartRepository) :
    BaseViewModel() {

    var cartItems = MutableLiveData<List<CartItem>>()
    var purchaseDetail = MutableLiveData<PurchaseDetail>()

    var emptyState = MutableLiveData<EmptyState>()

    fun refreshData() {
        viewModelScope.launch {
            if (!TokenContainer.token.isNullOrEmpty()) {
                emptyState.value = EmptyState(false)
                cartRepository.get()
                    .flowSetProgressBar(progressbarStatusLiveData)
                    .flowCatch()
                    .collect {
                        if (it.cart_items.isNotEmpty()) {
                            cartItems.value = it.cart_items
                            purchaseDetail.value =
                                PurchaseDetail(it.total_price, it.payable_price, it.shipping_cost)
                        } else
                            emptyState.value = EmptyState(true, R.string.cartEmptyState)
                    }
            } else
                emptyState.value = EmptyState(true, R.string.cartEmptyStateLoginRequired, true)
        }
    }

    fun changeCountCartItem(cartItemId: Int, count: Int): Flow<AddToCartResponse> {
        return cartRepository.changeCount(cartItemId, count)
    }

    fun removeCartItem(cartItemId: Int): Flow<MessageResponse> {
        return cartRepository.remove(cartItemId)
    }

    fun checkIsEmptyState() {
        cartItems.value?.let {
            if (it.isEmpty()) {
                emptyState.postValue(EmptyState(true, R.string.cartEmptyState))
            }
        }

    }

    fun calculateAndPublishPurchaseDetail() {
        cartItems.value?.let { cartItems ->
            purchaseDetail.value?.let {
                var totalPrice = 0L
                var payablePrice = 0L
                cartItems.forEach { cartItem ->
                    totalPrice += cartItem.product.currentPrice * cartItem.count
                    payablePrice += (cartItem.product.currentPrice - cartItem.product.discount) * cartItem.count
                }
                it.totalPrice = totalPrice
                it.payablePrice = payablePrice
                purchaseDetail.postValue(it)
            }
        }
    }

}