package mor.aliakbar.tavaloodshop.model.dataclass

data class CategoryItemParent(
    val id: Int,
    val title: String,
    val image: String
) {
    var isSelected: Boolean = false

}