package mor.aliakbar.tavaloodshop.model.repository

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.*

interface CartRepository {

    fun addToCart(productId: Int): Flow<AddToCartResponse>

    fun get(): Flow<CartResponse>

    fun remove(cartItemId: Int): Flow<MessageResponse>

    fun changeCount(cartItemId: Int, count: Int): Flow<AddToCartResponse>

    fun getCartItemCount(): Flow<CartItemCount>

}