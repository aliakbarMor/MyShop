package mor.aliakbar.tavaloodshop.feature.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mor.aliakbar.tavaloodshop.databinding.ItemCategoryChildBinding
import mor.aliakbar.tavaloodshop.model.dataclass.CategoryItem
import mor.aliakbar.tavaloodshop.services.loaddingImage.FrescoLoadingImage
import javax.inject.Inject

class CategoryChildAdapter @Inject constructor(private val loadingImage: FrescoLoadingImage) :
    RecyclerView.Adapter<CategoryChildAdapter.ViewHolder>() {

    var categories = ArrayList<CategoryItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCategoryChildClick: ((CategoryItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCategoryChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(categories[position])


    override fun getItemCount(): Int = categories.size

    inner class ViewHolder(_binding: ItemCategoryChildBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding
        fun bind(categoryItem: CategoryItem) {
            binding.apply {
                loadingImage.loadImage(categoryImageView, categoryItem.image)
                titleCategory.text = categoryItem.title
            }
        }
    }

}