package mor.aliakbar.tavaloodshop.model.dataclass

data class AddToCartResponse(
    var count: Int,
    val id: Int,
    val product_id: Int
)