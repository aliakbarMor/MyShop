package mor.aliakbar.tavaloodshop.view.productlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseActivity
import mor.aliakbar.tavaloodshop.databinding.ActivityProductListBinding
import mor.aliakbar.tavaloodshop.model.dataclass.Product
import mor.aliakbar.tavaloodshop.utils.Variable
import mor.aliakbar.tavaloodshop.view.common.adapter.ProductAdapter
import mor.aliakbar.tavaloodshop.view.common.adapter.ProductListener
import mor.aliakbar.tavaloodshop.view.productdetail.ProductDetailActivity
import javax.inject.Inject

@AndroidEntryPoint
class ProductListActivity : BaseActivity<ActivityProductListBinding>(), ProductListener {

    override val bindingInflater: (LayoutInflater) -> ActivityProductListBinding =
        { layoutInflater: LayoutInflater -> ActivityProductListBinding.inflate(layoutInflater) }
    val viewModel: ProductListViewModel by viewModels()

    @Inject
    lateinit var productAdapter: ProductAdapter

    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productAdapter.productListener = this
        productAdapter.viewType = Variable.PRODUCT_VIEW_TYPE_GRID
        gridLayoutManager = GridLayoutManager(this, 2)
        binding.productRecyclerViewProductList.layoutManager = gridLayoutManager
        binding.productRecyclerViewProductList.adapter = productAdapter

        observe()
        setListener()

        binding.toolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
    }

    private fun setListener() {
        binding.viewTypeImageViewProductList.setOnClickListener {
            if (productAdapter.viewType == Variable.PRODUCT_VIEW_TYPE_GRID) {
                productAdapter.viewType = Variable.PRODUCT_VIEW_TYPE_LARGE
                binding.viewTypeImageViewProductList.setImageResource(R.drawable.ic_product_large_24dp)
                gridLayoutManager.spanCount = 1
                productAdapter.notifyDataSetChanged()

            } else {
                productAdapter.viewType = Variable.PRODUCT_VIEW_TYPE_GRID
                binding.viewTypeImageViewProductList.setImageResource(R.drawable.ic_product_grid_24dp)
                gridLayoutManager.spanCount = 2
                productAdapter.notifyDataSetChanged()
            }
        }


        binding.chooseSortViewProductList.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.sortBy))
                .setSingleChoiceItems(
                    R.array.productSortArray,
                    viewModel.sort.value!!
                ) { dialog, selectedSortIndex ->
                    viewModel.onSelectedSortChangedByUser(selectedSortIndex)
                    dialog.dismiss()
                }
                .show()
        }
    }

    private fun observe() {
        viewModel.products.observe(this) {
            productAdapter.products = it as ArrayList<Product>
        }

        viewModel.sort.observe(this) {
            binding.productSortTextViewProductList.text = getString(viewModel.sortTitles[it])
        }
    }

    override fun onProductClick(product: Product) {
        val intent = Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(Variable.EXTRA_KEY_DATA, product)
        }
        startActivity(intent)
    }

    override fun onFavoriteIconClick(product: Product) {
        viewModel.addToFavorite(product)
    }
}