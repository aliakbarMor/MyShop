package mor.aliakbar.tavaloodshop.model.source

import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mor.aliakbar.tavaloodshop.model.api.ApiService
import mor.aliakbar.tavaloodshop.model.dataclass.*
import javax.inject.Inject

class CartRemoteSource @Inject constructor(var apiService: ApiService) : CartDataSource {

    override fun addToCart(productId: Int): Flow<AddToCartResponse> {
        return flow {
            val json = JsonObject().apply {
                addProperty("product_id", productId)
            }
            emit(apiService.addToCart(json))
        }.flowOn(Dispatchers.IO)
    }

    override fun get(): Flow<CartResponse> {
        return flow {
            emit(apiService.getCart())
        }.flowOn(Dispatchers.IO)
    }

    override fun remove(cartItemId: Int): Flow<MessageResponse> {
        return flow {
            emit(apiService.removeItemFromCart(JsonObject().apply {
                addProperty("cart_item_id", cartItemId)
            }))
        }.flowOn(Dispatchers.IO)
    }

    override fun changeCount(cartItemId: Int, count: Int): Flow<AddToCartResponse> {
        return flow {
            emit(apiService.changeCount(JsonObject().apply {
                addProperty("cart_item_id", cartItemId)
                addProperty("count", count)
            }))
        }.flowOn(Dispatchers.IO)
    }

    override fun getCartItemCount(): Flow<CartItemCount> {
        return flow {
            emit(apiService.getCartItemsCount())
        }
    }
}