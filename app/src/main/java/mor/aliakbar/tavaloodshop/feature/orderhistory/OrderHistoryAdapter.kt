package mor.aliakbar.tavaloodshop.feature.orderhistory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import mor.aliakbar.tavaloodshop.customview.imageview.AppImageView
import mor.aliakbar.tavaloodshop.databinding.ItemOrderHistoryBinding
import mor.aliakbar.tavaloodshop.model.dataclass.OrderHistoryItem
import mor.aliakbar.tavaloodshop.services.loaddingImage.LoadingImageServices
import mor.aliakbar.tavaloodshop.utils.DiffUtilCallBack
import mor.aliakbar.tavaloodshop.utils.TextUtils.convertEnglishNumberToPersianNumber
import mor.aliakbar.tavaloodshop.utils.TextUtils.formatPrice
import mor.aliakbar.tavaloodshop.utils.convertDpToPixel
import javax.inject.Inject


class OrderHistoryAdapter @Inject constructor(
    @ApplicationContext val context: Context,
    val loadingImageServices: LoadingImageServices
) :
    RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {

    var orders = ArrayList<OrderHistoryItem>()
        set(value) {
            DiffUtil.calculateDiff(DiffUtilCallBack(field, value)).dispatchUpdatesTo(this)
            field = value
        }

    var onOrderDetailButtonClick: ((orderHistoryItem: OrderHistoryItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int {
        return orders.size
    }


    inner class ViewHolder(val binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val size = convertDpToPixel(100f, context).toInt()
        private val margin = convertDpToPixel(8f, context).toInt()
        private var layoutParams = LinearLayout.LayoutParams(size, size)

        init {
            layoutParams.setMargins(margin, 0, margin, 0)
        }

        fun bind(orderHistoryItem: OrderHistoryItem) {
            binding.apply {
                orderIdTextViewItemOrderHistory.text =
                    convertEnglishNumberToPersianNumber(orderHistoryItem.id.toString())
                orderAmountTextViewItemOrder.text =
                    convertEnglishNumberToPersianNumber(formatPrice(orderHistoryItem.payable).toString())

                orderProductsLinearLayout.removeAllViews()
                orderHistoryItem.order_items.forEach {
                    val imageView = AppImageView(context)
                    imageView.layoutParams = layoutParams
                    loadingImageServices.loadImage(imageView, it.product.imageUrl)
//                    imageView.setImageURI()
                    orderProductsLinearLayout.addView(imageView)
                }
                orderDetailButton.setOnClickListener {
                    onOrderDetailButtonClick?.invoke(orderHistoryItem)
                }


            }

        }
    }
}