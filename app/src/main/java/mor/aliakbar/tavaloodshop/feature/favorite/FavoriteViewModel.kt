package mor.aliakbar.tavaloodshop.feature.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.dataclass.Product
import mor.aliakbar.tavaloodshop.model.repository.ProductRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val productRepository: ProductRepository) :
    BaseViewModel() {

    val products = MutableLiveData<List<Product>>()

    init {
        viewModelScope.launch {
            productRepository.getFavoriteProducts()
                .collect {
                    products.value = it
                }
        }
    }

    fun remove(product: Product) {
        viewModelScope.launch {
            productRepository.deleteFromFavorites(product)
        }
    }

}