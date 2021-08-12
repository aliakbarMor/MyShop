package mor.aliakbar.tavaloodshop.feature.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.dataclass.CategoryItem
import mor.aliakbar.tavaloodshop.model.dataclass.CategoryItemParent
import mor.aliakbar.tavaloodshop.model.repository.CategoryRepository
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowSetProgressBar
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(categoryRepository: CategoryRepository) :
    BaseViewModel() {

    val parentCategories = MutableLiveData<List<CategoryItemParent>>()
    private val allCategoryItem = MutableLiveData<List<CategoryItem>>()
    val childCategories = MutableLiveData<ArrayList<CategoryItem>>()

    init {
        viewModelScope.launch {
            categoryRepository.getParentCategories()
                .flowCatch()
                .flowSetProgressBar(progressbarStatusLiveData)
                .collect { parentCategories.value = it }

            categoryRepository.getCategories().flowCatch().collect {
                allCategoryItem.value = it
            }
        }
    }

    fun setCategory(categoryParentId: Int) {
        val categories = ArrayList<CategoryItem>()
        allCategoryItem.value?.forEach {
            if (it.categoryParentId == categoryParentId)
                categories.add(it)
        }
        childCategories.value = categories
    }

}