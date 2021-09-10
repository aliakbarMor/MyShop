package mor.aliakbar.tavaloodshop.model.repository

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.AddToCartResponse
import mor.aliakbar.tavaloodshop.model.dataclass.CartItemCount
import mor.aliakbar.tavaloodshop.model.dataclass.CartResponse
import mor.aliakbar.tavaloodshop.model.dataclass.MessageResponse
import mor.aliakbar.tavaloodshop.model.source.CartDataSource
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val cartRemoteSource: CartDataSource) :
    CartRepository {

    override fun addToCart(productId: Int): Flow<AddToCartResponse> {
        return cartRemoteSource.addToCart(productId)
    }

    override fun get(): Flow<CartResponse> {
        return cartRemoteSource.get()
    }

    override fun remove(cartItemId: Int): Flow<MessageResponse> {
        return cartRemoteSource.remove(cartItemId)
    }

    override fun changeCount(cartItemId: Int, count: Int): Flow<AddToCartResponse> {
        return cartRemoteSource.changeCount(cartItemId, count)
    }

    override fun getCartItemCount(): Flow<CartItemCount> {
        return cartRemoteSource.getCartItemCount()
    }
}