package mor.aliakbar.tavaloodshop.view.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.dataclass.Banner
import mor.aliakbar.tavaloodshop.model.dataclass.Product
import mor.aliakbar.tavaloodshop.model.repository.BannerRepository
import mor.aliakbar.tavaloodshop.model.repository.ProductRepository
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowSetProgressBar
import mor.aliakbar.tavaloodshop.utils.Variable
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val bannerRepository: BannerRepository,
) :
    BaseViewModel() {

    var products = MutableLiveData<Pair<Int, List<Product>>>()
    val searchProducts = MutableLiveData<List<Product>?>()

    var lastSearch = ""

    init {
        progressbarStatusLiveData.value = true
        try {
            viewModelScope.launch {

                productRepository.get(Variable.PRODUCT_SORT_LATEST)
                    .flowCatch()
                    .collect { products.value = Pair(Variable.PRODUCT_SORT_LATEST, it) }
                productRepository.get(Variable.PRODUCT_SORT_POPULAR)
                    .flowCatch()
                    .collect { products.value = Pair(Variable.PRODUCT_SORT_POPULAR, it) }
            }
        } finally {
            progressbarStatusLiveData.value = false
        }
    }


    var banners: LiveData<List<Banner>> = liveData {
        bannerRepository.get()
            .flowCatch()
            .collect {
                emit(it)
            }
    }

    fun addToFavorite(product: Product) {
        viewModelScope.launch {
            productRepository.addToFavorites(product)
        }
    }

    fun removeFromFavorite(product: Product) {
        viewModelScope.launch {
            productRepository.deleteFromFavorites(product)
        }
    }

    fun search(productTitle: String) {
        if (productTitle != lastSearch) {
            if (productTitle != "") {
                lastSearch = productTitle
                viewModelScope.launch {
                    productRepository.searchInProductsWithProductTitle(productTitle)
                        .flowSetProgressBar(progressbarStatusLiveData)
                        .flowCatch()
                        .collect {
                            searchProducts.value = it
                        }
                }
            } else {
                searchProducts.value = null
            }
        }

    }

}