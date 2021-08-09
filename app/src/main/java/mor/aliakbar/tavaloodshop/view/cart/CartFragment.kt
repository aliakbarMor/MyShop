package mor.aliakbar.tavaloodshop.view.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseFragment
import mor.aliakbar.tavaloodshop.databinding.FragmentCartBinding
import mor.aliakbar.tavaloodshop.model.dataclass.CartItem
import mor.aliakbar.tavaloodshop.model.dataclass.CartItemCount
import mor.aliakbar.tavaloodshop.view.shipping.ShippingActivity
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import mor.aliakbar.tavaloodshop.utils.Variable
import mor.aliakbar.tavaloodshop.view.auth.AuthActivity
import mor.aliakbar.tavaloodshop.services.exception.AppException
import mor.aliakbar.tavaloodshop.services.exception.ExceptionType
import mor.aliakbar.tavaloodshop.view.productdetail.ProductDetailActivity
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(), CartItemListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCartBinding
        get() = { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            FragmentCartBinding.inflate(layoutInflater, viewGroup, b)
        }
    val viewModel: CartViewModel by viewModels()

    @Inject
    lateinit var cartAdapter: CartAdapter

    override fun onStart() {
        super.onStart()
        viewModel.refreshData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observes()

    }

    private fun observes() {
        viewModel.cartItems.observe(viewLifecycleOwner) {
            cartAdapter.cartItems = it as ArrayList<CartItem>
        }

        viewModel.purchaseDetail.observe(viewLifecycleOwner) {
            cartAdapter.purchase = it
        }

        viewModel.emptyState.observe(viewLifecycleOwner) {
            val emptyStateView =
                showEmptyState(R.layout.view_empty_state, R.id.emptyStateRootView)
            if (it.mustShowEmptyState) {
                emptyStateView?.let { view ->
                    view.findViewById<TextView>(R.id.emptyStateMessageTv).text =
                        getString(it.emptyStateMessage)
                    view.findViewById<Button>(R.id.emptyStateCtaBtn).setOnClickListener {
                        startActivity(Intent(requireContext(), AuthActivity::class.java))
                    }
                }
            } else {
                emptyStateView?.visibility = View.GONE
            }
        }

        viewModel.progressbarStatusLiveData.observe(viewLifecycleOwner) {
            showProgressbar(it)
        }
    }

    private fun initViews() {

        cartAdapter.cartItemListener = this
        binding.cartItemsRv.adapter = cartAdapter

        binding.payBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ShippingActivity::class.java).apply {
                putExtra(Variable.EXTRA_KEY_DATA, viewModel.purchaseDetail.value)
            })

        }

    }

    override fun onIncreaseBtnClick(cartItem: CartItem) {
        if (cartItem.count != 5) {
            runBlocking {
                viewModel.changeCountCartItem(cartItem.cart_item_id, ++cartItem.count)
                    .catch { cartAdapter.updateCount(cartItem) }
                    .flowCatch()
                    .collect {
                        cartAdapter.updateCount(cartItem)
                        viewModel.calculateAndPublishPurchaseDetail()
                        EventBus.getDefault().getStickyEvent(CartItemCount::class.java).let {
                            it.count += 1
                            EventBus.getDefault().postSticky(it)
                        }
                    }
            }
        } else {
            cartAdapter.updateCount(cartItem)
            EventBus.getDefault().post(
                AppException(ExceptionType.SIMPLE, R.string.increaseCartItemError)
            )
        }
    }

    override fun onDecreaseBtnClick(cartItem: CartItem) {
        if (cartItem.count != 1) {
            runBlocking {
                viewModel.changeCountCartItem(cartItem.cart_item_id, cartItem.count--)
                    .flowCatch()
                    .catch { cartAdapter.updateCount(cartItem) }
                    .collect {
                        cartAdapter.updateCount(cartItem)
                        viewModel.calculateAndPublishPurchaseDetail()
                        val cartItemCount =
                            EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                        cartItemCount?.let {
                            it.count -= 1
                            EventBus.getDefault().postSticky(it)
                        }
                    }
            }
        } else {
            cartAdapter.updateCount(cartItem)
            EventBus.getDefault().post(
                AppException(ExceptionType.SIMPLE, R.string.decreaseCartItemError)
            )
        }

    }

    override fun onRemoveBtnClick(cartItem: CartItem) {
        runBlocking {
            viewModel.removeCartItem(cartItem.cart_item_id)
                .flowCatch()
                .collect {
                    cartAdapter.removeItem(cartItem)
                    viewModel.checkIsEmptyState()
                    viewModel.calculateAndPublishPurchaseDetail()
                    val cartItemCount =
                        EventBus.getDefault().getStickyEvent(CartItemCount::class.java)
                    cartItemCount?.let {
                        it.count -= cartItem.count
                        EventBus.getDefault().postSticky(it)
                    }
                }
        }
    }

    override fun onProductImageClick(cartItem: CartItem) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(Variable.EXTRA_KEY_DATA, cartItem.product)
        })
    }


}