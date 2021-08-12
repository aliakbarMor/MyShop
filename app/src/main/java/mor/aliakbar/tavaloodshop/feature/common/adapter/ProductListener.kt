package mor.aliakbar.tavaloodshop.feature.common.adapter

import mor.aliakbar.tavaloodshop.model.dataclass.Product

interface ProductListener {
    fun onProductClick(product: Product)
    fun onFavoriteIconClick(product: Product)
}