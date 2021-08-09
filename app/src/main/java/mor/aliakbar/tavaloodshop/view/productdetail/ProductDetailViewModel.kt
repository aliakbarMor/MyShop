package mor.aliakbar.tavaloodshop.view.productdetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.dataclass.AddToCartResponse
import mor.aliakbar.tavaloodshop.model.dataclass.Comment
import mor.aliakbar.tavaloodshop.model.dataclass.Product
import mor.aliakbar.tavaloodshop.model.repository.CartRepository
import mor.aliakbar.tavaloodshop.model.repository.CommentRepository
import mor.aliakbar.tavaloodshop.model.repository.ProductRepository
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import mor.aliakbar.tavaloodshop.utils.Variable
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    state: SavedStateHandle,
    private val commentRepository: CommentRepository,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : BaseViewModel() {

    val product = MutableLiveData<Product>()
    val comments = MutableLiveData<List<Comment>>()

    init {
        product.value = state.get<Product>(Variable.EXTRA_KEY_DATA)!!
    }

    fun getComments() {
        viewModelScope.launch {
            Log.d("AAAAAAAAaa", product.value!!.toString())
            commentRepository.getAll(product.value!!.id.toInt())
                .flowCatch()
                .collect {
                    comments.value = it
                }
        }
    }

    fun addToCart(): Flow<AddToCartResponse> {
        return cartRepository.addToCart(product.value!!.id.toInt())
    }

    fun onFavoriteIconClick() {
        viewModelScope.launch {
            if (product.value!!.isFavorite) {
                productRepository.addToFavorites(product.value!!)
            } else
                productRepository.deleteFromFavorites(product.value!!)
        }
    }

}