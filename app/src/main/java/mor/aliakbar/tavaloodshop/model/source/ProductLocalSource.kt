package mor.aliakbar.tavaloodshop.model.source

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.Product

@Dao
interface ProductLocalSource : ProductDataSource {

    @Query("SELECT * FROM products")
    override fun getFavoriteProducts(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override suspend fun addToFavorites(product: Product)

    @Delete
    override suspend fun deleteFromFavorites(product: Product)

    override fun get(sort: Int, categoryId: Int?, categoryParentId: Int?): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun searchInProductsWithProductTitle(productTitle: String): Flow<List<Product>> {
        TODO("Not yet implemented")
    }
}