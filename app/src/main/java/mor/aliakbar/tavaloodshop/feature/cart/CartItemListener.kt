package mor.aliakbar.tavaloodshop.feature.cart

import mor.aliakbar.tavaloodshop.model.dataclass.CartItem

interface CartItemListener {

    fun onIncreaseBtnClick(cartItem: CartItem)

    fun onDecreaseBtnClick(cartItem: CartItem)

    fun onRemoveBtnClick(cartItem: CartItem)

    fun onProductImageClick(cartItem: CartItem)

}