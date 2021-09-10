package mor.aliakbar.tavaloodshop.model.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import mor.aliakbar.tavaloodshop.model.dataclass.Product
import mor.aliakbar.tavaloodshop.model.source.ProductDataSource
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private var remoteProductSource: ProductDataSource,
    private var localProductSource: ProductDataSource
) : ProductRepository {

    override suspend fun get(
        sort: Int,
        categoryId: Int?,
        categoryParentId: Int?
    ): Flow<List<Product>> {

        var products = emptyList<Product>()
        var favoriteProductsId = emptyList<Long>()

        CoroutineScope(Dispatchers.Main).launch {
            getFavoriteProducts().collect {
                favoriteProductsId = it.map { favorite -> favorite.id }
            }
        }
        remoteProductSource.get(sort, categoryId, categoryParentId)
            .collect {
                it.forEach { product ->
                    if (favoriteProductsId.contains(product.id)) {
                        product.isFavorite = true

                    }
                    products = it
                }
            }
        return flow { emit(products) }
    }

    override fun getFavoriteProducts(): Flow<List<Product>> {
        return localProductSource.getFavoriteProducts()
    }

    override suspend fun addToFavorites(product: Product) {
        return localProductSource.addToFavorites(product)
    }

    override suspend fun deleteFromFavorites(product: Product) {
        return localProductSource.deleteFromFavorites(product)
    }

    override fun searchInProductsWithProductTitle(productTitle: String): Flow<List<Product>> {
        return remoteProductSource.searchInProductsWithProductTitle(productTitle)
    }


}