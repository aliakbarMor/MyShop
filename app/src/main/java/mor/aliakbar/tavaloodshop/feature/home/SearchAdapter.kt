package mor.aliakbar.tavaloodshop.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mor.aliakbar.tavaloodshop.databinding.ItemSearchProductBinding
import mor.aliakbar.tavaloodshop.model.dataclass.Product
import mor.aliakbar.tavaloodshop.services.loaddingImage.FrescoLoadingImage
import javax.inject.Inject

class SearchAdapter @Inject constructor(
    private val imageLoadingService: FrescoLoadingImage,
) : RecyclerView.Adapter<SearchAdapter.SearchListViewHolder>() {

    var products = ArrayList<Product>()
        set(value) {
            field.clear()
            field = value
            notifyDataSetChanged()
        }

    var onProductClick: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        return SearchListViewHolder(
            ItemSearchProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class SearchListViewHolder(_binding: ItemSearchProductBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding

        fun bind(product: Product) {
            binding.apply {
                imageLoadingService.loadImage(searchProductImageView, product.imageUrl)
                searchProductTitle.text = product.title

                binding.root.setOnClickListener {
                    onProductClick?.invoke(product)
                }
            }
        }
    }
}