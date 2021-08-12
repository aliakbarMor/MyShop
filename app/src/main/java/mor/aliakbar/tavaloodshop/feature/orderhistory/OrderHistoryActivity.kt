package mor.aliakbar.tavaloodshop.feature.orderhistory

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseActivity
import mor.aliakbar.tavaloodshop.databinding.ActivityOrderHistoryBinding
import mor.aliakbar.tavaloodshop.feature.auth.AuthActivity
import mor.aliakbar.tavaloodshop.model.dataclass.OrderHistoryItem
import mor.aliakbar.tavaloodshop.utils.ActivityUtils.backToPreviousPageListener
import javax.inject.Inject

@AndroidEntryPoint
class OrderHistoryActivity : BaseActivity<ActivityOrderHistoryBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityOrderHistoryBinding =
        { layoutInflater: LayoutInflater -> ActivityOrderHistoryBinding.inflate(layoutInflater) }
    val viewModel: OrderHistoryViewModel by viewModels()

    @Inject
    lateinit var orderHistoryAdapter: OrderHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        observes()

    }

    private fun observes() {
        viewModel.orderHistories.observe(this) {
            if (it != null) {
                orderHistoryAdapter.orders = it as ArrayList<OrderHistoryItem>
            } else {
                val emptyStateView =
                    showEmptyState(R.layout.view_empty_state, R.id.emptyStateRootView)
                emptyStateView?.let { view ->
                    view.findViewById<TextView>(R.id.emptyStateMessageTv).text =
                        getString(R.string.cartEmptyStateLoginRequired)
                    view.findViewById<ImageView>(R.id.imageEmptyState)
                        .setImageResource(R.drawable.ic_empty_state_order_history)
                    view.findViewById<Button>(R.id.emptyStateCtaBtn).setOnClickListener {
                        startActivity(Intent(this, AuthActivity::class.java))
                    }
                }
            }
        }

        viewModel.progressbarStatusLiveData.observe(this) {
            showProgressbar(it)
        }
    }

    private fun initialize() {
        binding.recyclerViewOrderHistory.adapter = orderHistoryAdapter
        orderHistoryAdapter.onOrderDetailButtonClick = {
//            Todo go to activity order detail
        }

        binding.toolbar.onBackButtonClickListener = backToPreviousPageListener()
    }
}