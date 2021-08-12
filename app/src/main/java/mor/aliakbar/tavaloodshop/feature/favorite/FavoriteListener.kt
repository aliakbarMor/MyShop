package mor.aliakbar.tavaloodshop.feature.favorite

import mor.aliakbar.tavaloodshop.model.dataclass.Product

interface FavoriteListener {
    fun onClick(product: Product)
}