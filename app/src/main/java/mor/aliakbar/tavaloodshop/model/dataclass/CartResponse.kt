package mor.aliakbar.tavaloodshop.model.dataclass

data class CartResponse(
    val cart_items: List<CartItem>,
    val payable_price: Long,
    val shipping_cost: Long,
    val total_price: Long
)