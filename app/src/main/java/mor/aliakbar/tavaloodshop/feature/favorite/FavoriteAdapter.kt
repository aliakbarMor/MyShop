package mor.aliakbar.tavaloodshop.feature.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import mor.aliakbar.tavaloodshop.databinding.ItemFavoriteProductBinding
import mor.aliakbar.tavaloodshop.model.dataclass.Product
import mor.aliakbar.tavaloodshop.services.loaddingImage.LoadingImageServices
import mor.aliakbar.tavaloodshop.utils.DiffUtilCallBack
import javax.inject.Inject

class FavoriteAdapter @Inject constructor(var loadingImageServices: LoadingImageServices) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    var products = ArrayList<Product>()
        set(value) {
            DiffUtil.calculateDiff(DiffUtilCallBack(field, value)).dispatchUpdatesTo(this)
            field = value
        }

    lateinit var favoriteListener: FavoriteListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFavoriteProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun remove(position: Int) {
        products.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(private val binding: ItemFavoriteProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                loadingImageServices.loadImage(productImageView, product.imageUrl)
                productTitleTv.text = product.title
                root.setOnClickListener {
                    favoriteListener.onClick(product)
                }
            }
        }
    }

}