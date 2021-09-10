package mor.aliakbar.tavaloodshop.model.repository

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.CategoryItem
import mor.aliakbar.tavaloodshop.model.dataclass.CategoryItemParent

interface CategoryRepository {

    fun getCategories(): Flow<List<CategoryItem>>

    fun getParentCategories(): Flow<List<CategoryItemParent>>

}
