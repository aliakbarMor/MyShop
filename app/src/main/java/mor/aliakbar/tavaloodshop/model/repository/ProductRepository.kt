package mor.aliakbar.tavaloodshop.model.repository

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.Product

interface ProductRepository {

    suspend fun get(
        sort: Int,
        categoryId: Int? = null,
        categoryParentId: Int? = null
    ): Flow<List<Product>>

    fun getFavoriteProducts(): Flow<List<Product>>

    suspend fun addToFavorites(product: Product)

    suspend fun deleteFromFavorites(product: Product)

    fun searchInProductsWithProductTitle(productTitle: String): Flow<List<Product>>

}