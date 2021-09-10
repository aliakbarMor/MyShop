package mor.aliakbar.tavaloodshop.feature.cart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import mor.aliakbar.tavaloodshop.databinding.ItemCartBinding
import mor.aliakbar.tavaloodshop.databinding.ItemPurchaseDetailsBinding
import mor.aliakbar.tavaloodshop.model.dataclass.CartItem
import mor.aliakbar.tavaloodshop.model.dataclass.PurchaseDetail
import mor.aliakbar.tavaloodshop.services.loaddingImage.LoadingImageServices
import mor.aliakbar.tavaloodshop.utils.DiffUtilCallBack
import mor.aliakbar.tavaloodshop.utils.TextUtils.formatPrice
import mor.aliakbar.tavaloodshop.utils.Variable
import javax.inject.Inject

class CartAdapter @Inject constructor(var loadingImageServices: LoadingImageServices) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var cartItems = ArrayList<CartItem>()
        set(value) {
            DiffUtil.calculateDiff(DiffUtilCallBack(field, value)).dispatchUpdatesTo(this)
            field = value
//            notifyDataSetChanged()
        }
    var purchase: PurchaseDetail? = null
        set(value) {
            field = value
            notifyItemChanged(cartItems.size)
        }
    lateinit var cartItemListener: CartItemListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Variable.CART_VIEW_TYPE_CART_ITEM) {
            CartViewHolder(
                ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else
            PurchaseViewHolder(
                ItemPurchaseDetailsBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartViewHolder) {
            holder.bind(cartItems[position])
        } else if (holder is PurchaseViewHolder)
            purchase?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return cartItems.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == cartItems.size)
            Variable.CART_VIEW_TYPE_PURCHASE
        else
            Variable.CART_VIEW_TYPE_CART_ITEM
    }

    fun updateCount(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1) {
            cartItems[index].changeCountProgressBarIsVisible = false
            notifyItemChanged(index)
        }
    }

    fun removeItem(cartItem: CartItem) {
        val index = cartItems.indexOf(cartItem)
        if (index > -1) {
            cartItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            binding.apply {
                loadingImageServices.loadImage(productIv, cartItem.product.imageUrl)
                productTitleTv.text = cartItem.product.title
                previousPriceTv.text =
                    formatPrice(cartItem.product.currentPrice + cartItem.product.discount)
                previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                priceTv.text = formatPrice(cartItem.product.currentPrice)
                cartItemCountTv.text = cartItem.count.toString()
                increaseBtn.setOnClickListener {
                    cartItem.changeCountProgressBarIsVisible = true
                    changeCountProgressBar.visibility = ViewGroup.VISIBLE
                    cartItemCountTv.visibility = ViewGroup.INVISIBLE
                    cartItemListener.onIncreaseBtnClick(cartItem)
                }
                decreaseBtn.setOnClickListener {
                    cartItem.changeCountProgressBarIsVisible = true
                    changeCountProgressBar.visibility = ViewGroup.VISIBLE
                    cartItemCountTv.visibility = ViewGroup.INVISIBLE
                    cartItemListener.onDecreaseBtnClick(cartItem)
                }
                removeFromCartBtn.setOnClickListener {
                    cartItemListener.onRemoveBtnClick(cartItem)
                }
                productIv.setOnClickListener {
                    cartItemListener.onProductImageClick(cartItem)
                }
                cartItemCountTv.visibility =
                    if (cartItem.changeCountProgressBarIsVisible) View.INVISIBLE else View.VISIBLE
                changeCountProgressBar.visibility =
                    if (!cartItem.changeCountProgressBarIsVisible) View.GONE else View.VISIBLE
            }
        }
    }

    class PurchaseViewHolder(var binding: ItemPurchaseDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(purchase: PurchaseDetail) {
            binding.apply {
                totalPriceTv.text = formatPrice(purchase.totalPrice)
                shippingCostTv.text = formatPrice(purchase.shippingCost)
                payablePriceTv.text = formatPrice(purchase.payablePrice)
            }
        }
    }
}
