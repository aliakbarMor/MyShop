package mor.aliakbar.tavaloodshop.feature.category

import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.databinding.ItemCategoryCompactBinding
import mor.aliakbar.tavaloodshop.databinding.ItemCategoryLinearBinding
import mor.aliakbar.tavaloodshop.model.dataclass.CategoryItemParent
import mor.aliakbar.tavaloodshop.services.loaddingImage.FrescoLoadingImage
import mor.aliakbar.tavaloodshop.utils.DiffUtilCallBack
import mor.aliakbar.tavaloodshop.utils.Variable
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
class CategoryParentAdapter @Inject constructor(
    private val loadingImage: FrescoLoadingImage,
    @ApplicationContext private var context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var categories = ArrayList<CategoryItemParent>()
        set(value) {
            DiffUtil.calculateDiff(DiffUtilCallBack(field, value)).dispatchUpdatesTo(this)
            field = value
        }
    var viewType: Int = Variable.CATEGORY_VIEW_TYPE_LINEAR

    var onCategoryParentClick: ((CategoryItemParent) -> Unit)? = null

    var lastItemSelected = -1

    val colorSurface = TypedValue()
    val colorGray = context.theme.resources.getColor(R.color.gray, context.theme)

    init {
        context.theme.resolveAttribute(R.attr.colorSurface, colorSurface, true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Variable.CATEGORY_VIEW_TYPE_LINEAR -> {
                LinearViewHolder(
                    ItemCategoryLinearBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            Variable.CATEGORY_VIEW_TYPE_COMPACT -> {
                CompactViewHolder(
                    ItemCategoryCompactBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> throw IllegalStateException("Category adapter type not set")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val category = categories[position]
        if (holder is LinearViewHolder) {
            holder.bind(category)
        } else if (holder is CompactViewHolder)
            holder.bind(category)
        holder.itemView.setOnClickListener {
            onCategoryParentClick?.invoke(categories[position])

            category.isSelected = true
            holder.itemView.setBackgroundColor(colorSurface.data)

            if (lastItemSelected != -1 && lastItemSelected != position) {
                categories[lastItemSelected].isSelected = false
                notifyItemChanged(lastItemSelected)
            }

            lastItemSelected = position
        }
    }

    override fun getItemCount(): Int = categories.size

    override fun getItemViewType(position: Int): Int = viewType

    inner class LinearViewHolder(_binding: ItemCategoryLinearBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding
        fun bind(categoryItemParent: CategoryItemParent) {
            binding.apply {
                loadingImage.loadImage(categoryImageView, categoryItemParent.image)
                titleCategory.text = categoryItemParent.title
            }
        }
    }

    inner class CompactViewHolder(_binding: ItemCategoryCompactBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding
        fun bind(categoryItemParent: CategoryItemParent) {
            binding.apply {
                loadingImage.loadImage(categoryImageView, categoryItemParent.image)
                titleCategory.text = categoryItemParent.title
                if (categoryItemParent.isSelected) {
                    binding.root.setBackgroundColor(colorSurface.data)
                } else
                    binding.root.setBackgroundColor(colorGray)
            }
        }
    }
}