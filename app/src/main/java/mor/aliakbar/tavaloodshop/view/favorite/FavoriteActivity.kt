package mor.aliakbar.tavaloodshop.view.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseActivity
import mor.aliakbar.tavaloodshop.databinding.ActivityFavoriteBinding
import mor.aliakbar.tavaloodshop.model.dataclass.Product
import mor.aliakbar.tavaloodshop.utils.Variable
import mor.aliakbar.tavaloodshop.view.productdetail.ProductDetailActivity
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>(), FavoriteListener {

    override val bindingInflater: (LayoutInflater) -> ActivityFavoriteBinding =
        { layoutInflater: LayoutInflater -> ActivityFavoriteBinding.inflate(layoutInflater) }
    val viewModel: FavoriteViewModel by viewModels()

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoriteAdapter.favoriteListener = this
        binding.favoriteProductsRv.adapter = favoriteAdapter

        viewModel.products.observe(this) {
            if (it.isNotEmpty()) {
                favoriteAdapter.products = it as ArrayList<Product>
            } else {
                val emptyStateView =
                    showEmptyState(R.layout.view_empty_state, R.id.emptyStateRootView)
                emptyStateView?.let { view ->
                    view.findViewById<TextView>(R.id.emptyStateMessageTv).text =
                        getString(R.string.emptyStateFavorites)
                    view.findViewById<ImageView>(R.id.imageEmptyState)
                        .setImageResource(R.drawable.ic_empty_state_favorite)
                    view.findViewById<Button>(R.id.emptyStateCtaBtn).visibility = View.GONE
                }
            }
        }
        viewModel.progressbarStatusLiveData.observe(this) {
            showProgressbar(it)
        }

        binding.toolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.favoriteProductsRv)
    }

    override fun onClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(Variable.EXTRA_KEY_DATA, product)
        })
    }


    private var simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
            Toast.makeText(
                applicationContext, getString(R.string.removeFavoriteItem), Toast.LENGTH_SHORT
            ).show()
            val position = viewHolder.adapterPosition
            viewModel.remove(viewModel.products.value!![position])
            favoriteAdapter.remove(position)
        }
    }

}