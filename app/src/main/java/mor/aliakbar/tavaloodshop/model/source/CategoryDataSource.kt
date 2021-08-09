package mor.aliakbar.tavaloodshop.model.source

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.CategoryItem
import mor.aliakbar.tavaloodshop.model.dataclass.CategoryItemParent

interface CategoryDataSource {

    fun getCategories():Flow<List<CategoryItem>>

    fun getParentCategories(): Flow<List<CategoryItemParent>>

}
