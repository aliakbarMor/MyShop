package mor.aliakbar.tavaloodshop.feature.productlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.dataclass.Product
import mor.aliakbar.tavaloodshop.model.repository.ProductRepository
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import mor.aliakbar.tavaloodshop.utils.Variable
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    state: SavedStateHandle,
    private val productRepository: ProductRepository
) : BaseViewModel() {

    val sort = MutableLiveData<Int>()
    val products = MutableLiveData<List<Product>>()
    val sortTitles = arrayOf(
        R.string.sortLatestProduct,
        R.string.sortPopularProduct,
        R.string.sortPriceHighToLowProduct,
        R.string.sortPriceLowToHighProduct
    )

    private var categoryId = state.get<Int>(Variable.EXTRA_KEY_CATEGORY_ID)
    private val categoryParentId = state.get<Int>(Variable.EXTRA_KEY_CATEGORY_PARENT_ID)

    init {
        sort.value = state.get<Int>(Variable.EXTRA_KEY_DATA)!!
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            productRepository.get(sort.value!!, categoryId, categoryParentId)
                .flowCatch()
                .collect {
                    products.value = it
                }
        }
    }

    fun onSelectedSortChangedByUser(selectedSortIndex: Int) {
        sort.value = selectedSortIndex
        getProducts()
    }

    fun addToFavorite(product: Product) {
        viewModelScope.launch {
            productRepository.addToFavorites(product)
        }
    }

}