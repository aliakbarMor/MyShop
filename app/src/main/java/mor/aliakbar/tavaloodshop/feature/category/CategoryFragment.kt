package mor.aliakbar.tavaloodshop.feature.category

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.base.BaseFragment
import mor.aliakbar.tavaloodshop.databinding.FragmentCategoryBinding
import mor.aliakbar.tavaloodshop.feature.productlist.ProductListActivity
import mor.aliakbar.tavaloodshop.model.dataclass.CategoryItem
import mor.aliakbar.tavaloodshop.model.dataclass.CategoryItemParent
import mor.aliakbar.tavaloodshop.utils.Variable
import mor.aliakbar.tavaloodshop.utils.convertDpToPixel
import javax.inject.Inject

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoryBinding
        get() = { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            FragmentCategoryBinding.inflate(layoutInflater, viewGroup, b)
        }
    val viewModel: CategoryViewModel by viewModels()

    @Inject
    lateinit var categoryParentAdapter: CategoryParentAdapter

    @Inject
    lateinit var categoryChildAdapter: CategoryChildAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        observes()

    }

    override fun onStart() {
        super.onStart()
        viewModel.parentCategories.value?.forEach {
            if (it.isSelected) {
//                onCategoryParentClick(it)
//                categoryParentAdapter.lastItemSelected = viewModel.parentCategories.value!!.indexOf(it)
                it.isSelected = false
                return@forEach
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initialize() {
        categoryParentAdapter.onCategoryParentClick = { categoryItemParent ->
            onCategoryParentClick(categoryItemParent)
        }
        categoryChildAdapter.onCategoryChildClick = { categoryItem ->
            onCategoryChildClick(categoryItem)
        }
        binding.recyclerViewParentCategory.adapter = categoryParentAdapter
        binding.recyclerViewCategory.adapter = categoryChildAdapter

        binding.showAllBtn.setOnClickListener {
            onShowAllClick()
        }
    }

    private fun observes() {
        viewModel.parentCategories.observe(viewLifecycleOwner) {
            categoryParentAdapter.categories = it as ArrayList<CategoryItemParent>
        }

        viewModel.childCategories.observe(viewLifecycleOwner) {
            categoryChildAdapter.categories = it
        }

    }

    private fun onCategoryParentClick(categoryItemParent: CategoryItemParent) {
        if (categoryParentAdapter.viewType != Variable.CATEGORY_VIEW_TYPE_COMPACT) {
            categoryParentAdapter.viewType = Variable.CATEGORY_VIEW_TYPE_COMPACT
            val layoutParams = binding.frameLayoutRecyclerView.layoutParams
            layoutParams.width = convertDpToPixel(90f, requireContext()).toInt()
            binding.frameLayoutRecyclerView.layoutParams = layoutParams
            binding.frameLayoutRecyclerView.layoutTransition.enableTransitionType(
                LayoutTransition.CHANGING
            )

        }
        viewModel.setCategory(categoryItemParent.id)
        binding.titleCategory.text = categoryItemParent.title

    }

    private fun onCategoryChildClick(categoryItem: CategoryItem) {
        startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
            putExtra(Variable.EXTRA_KEY_DATA, 0)
            putExtra(Variable.EXTRA_KEY_CATEGORY_ID, categoryItem.id)
        })
    }

    private fun onShowAllClick() {
        if (viewModel.childCategories.value!!.size > 0) {
            startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
                putExtra(Variable.EXTRA_KEY_DATA, 0)
                putExtra(
                    Variable.EXTRA_KEY_CATEGORY_PARENT_ID,
                    viewModel.childCategories.value!![0].categoryParentId
                )
            })
        }
    }
}