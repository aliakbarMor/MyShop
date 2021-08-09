package mor.aliakbar.tavaloodshop.model.repository

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.CategoryItem
import mor.aliakbar.tavaloodshop.model.dataclass.CategoryItemParent
import mor.aliakbar.tavaloodshop.model.source.CategoryDataSource
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val categoryRemoteSource: CategoryDataSource) :
    CategoryRepository {

    override fun getCategories(): Flow<List<CategoryItem>> {
        return categoryRemoteSource.getCategories()
    }

    override fun getParentCategories(): Flow<List<CategoryItemParent>> {
        return categoryRemoteSource.getParentCategories()
    }
}