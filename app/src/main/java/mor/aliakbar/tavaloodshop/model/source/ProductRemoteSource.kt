package mor.aliakbar.tavaloodshop.model.source

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mor.aliakbar.tavaloodshop.model.dataclass.Product
import mor.aliakbar.tavaloodshop.model.api.ApiService
import javax.inject.Inject

class ProductRemoteSource @Inject constructor(private val apiService: ApiService) :
    ProductDataSource {

    override fun get(sort: Int, categoryId: Int?, categoryParentId: Int?): Flow<List<Product>> {
        if (categoryId != null) {
//        TODO get products with category id
        } else if (categoryParentId != null) {
//        TODO get products with category parent id
        }

        return flow {
            emit(apiService.getProducts(sort.toString()))
        }.flowOn(Dispatchers.IO)
    }

    override fun searchInProductsWithProductTitle(productTitle: String): Flow<List<Product>> {
        return flow {
            emit(apiService.searchInProducts(productTitle))
        }.flowOn(Dispatchers.IO)
    }

    override fun getFavoriteProducts(): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToFavorites(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromFavorites(product: Product) {
        TODO("Not yet implemented")
    }


}