package mor.aliakbar.tavaloodshop.model.dataclass

data class CategoryItem(
    val id: Int,
    val title: String,
    val image: String,
    val categoryParentId: Int
)