package mor.aliakbar.tavaloodshop.feature.common.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.customview.imageview.AppImageView
import mor.aliakbar.tavaloodshop.model.dataclass.Product
import mor.aliakbar.tavaloodshop.services.loaddingImage.LoadingImageServices
import mor.aliakbar.tavaloodshop.utils.DiffUtilCallBack
import mor.aliakbar.tavaloodshop.utils.TextUtils.formatPrice
import mor.aliakbar.tavaloodshop.utils.Variable
import mor.aliakbar.tavaloodshop.utils.implementSpringAnimationTrait
import javax.inject.Inject

class ProductAdapter @Inject constructor(val frescoLoadingImage: LoadingImageServices) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var products = ArrayList<Product>()
        set(value) {
            DiffUtil.calculateDiff(DiffUtilCallBack(field, value)).dispatchUpdatesTo(this)
            field = value
        }
    var viewType: Int = Variable.PRODUCT_VIEW_TYPE_ROUNDED

    lateinit var productListener: ProductListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutResId = when (viewType) {
            Variable.PRODUCT_VIEW_TYPE_LARGE -> R.layout.item_product_large
            Variable.PRODUCT_VIEW_TYPE_GRID -> R.layout.item_product_grid
            Variable.PRODUCT_VIEW_TYPE_ROUNDED -> R.layout.item_product_rounded
            else -> throw IllegalAccessException("Type not set")
        }
        return ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(products[position])

    override fun getItemViewType(position: Int): Int {
        return viewType
    }


    inner class ProductViewHolder(iteView: View) :
        RecyclerView.ViewHolder(iteView) {

        private val productTitle: TextView =
            itemView.findViewById(R.id.productTitleTextViewItem)
        private val productCurrentPrice: TextView =
            itemView.findViewById(R.id.productCurrentPriceTextViewItem)
        private val productPreviousPrice: TextView =
            itemView.findViewById(R.id.productPreviousPriceTextViewItem)
        private val productImageView: AppImageView =
            itemView.findViewById(R.id.productImageViewItem)
        private val favoriteBtn: ImageView =
            itemView.findViewById(R.id.productFavoriteImageViewItem)

        fun bind(product: Product) {
            frescoLoadingImage.loadImage(productImageView, product.imageUrl)
            productTitle.text = product.title
            productCurrentPrice.text = formatPrice(product.currentPrice)
            productPreviousPrice.text =
                formatPrice(product.currentPrice + product.discount)
            productPreviousPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.implementSpringAnimationTrait()
            itemView.setOnClickListener {
                productListener.onProductClick(product)
            }

            if (product.isFavorite)
                favoriteBtn.setImageResource(R.drawable.ic_favorite_fill)
            else
                favoriteBtn.setImageResource(R.drawable.ic_favorites)
            favoriteBtn.setOnClickListener {
                productListener.onFavoriteIconClick(product)
                product.isFavorite = !product.isFavorite
                notifyItemChanged(adapterPosition)
            }
        }

    }
}