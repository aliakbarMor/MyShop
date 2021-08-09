package mor.aliakbar.tavaloodshop.view.favorite

import mor.aliakbar.tavaloodshop.model.dataclass.Product

interface FavoriteListener {
    fun onClick(product: Product)
}