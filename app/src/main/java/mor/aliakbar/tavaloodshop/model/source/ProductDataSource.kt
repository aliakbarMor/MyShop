package mor.aliakbar.tavaloodshop.model.source

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.Product

interface ProductDataSource {

    fun get(sort: Int, categoryId: Int?, categoryParentId: Int?): Flow<List<Product>>

    fun getFavoriteProducts(): Flow<List<Product>>

    suspend fun addToFavorites(product: Product)

    suspend fun deleteFromFavorites(product: Product)

    fun searchInProductsWithProductTitle(productTitle: String): Flow<List<Product>>

}