package mor.aliakbar.tavaloodshop.feature.checkout

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseActivity
import mor.aliakbar.tavaloodshop.databinding.ActivityCheckOutBinding
import mor.aliakbar.tavaloodshop.feature.main.MainActivity
import mor.aliakbar.tavaloodshop.feature.orderhistory.OrderHistoryActivity
import mor.aliakbar.tavaloodshop.utils.ActivityUtils.startActivityWithoutExtra
import mor.aliakbar.tavaloodshop.utils.TextUtils.formatPrice

@AndroidEntryPoint
class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCheckOutBinding =
        { layoutInflater: LayoutInflater -> ActivityCheckOutBinding.inflate(layoutInflater) }
    val viewModel: CheckOutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        observes()

    }

    private fun observes() {
        viewModel.orderId.observe(this) {
            if (it == null) {
                val data: Uri? = intent.data
                data?.let { uri ->
                    val orderIdPayment = uri.getQueryParameter("order_id")!!.toInt()
                    viewModel.setOrderIdPaymentResult(orderIdPayment)
                }
            }
            viewModel.checkResult()
        }

        viewModel.paymentResult.observe(this) {
            binding.apply {
                orderPriceTv.text = formatPrice(it.payable_price)
                orderStatusTv.text = it.payment_status
                purchaseStatusTv.text =
                    if (it.purchase_success)
                        getString(R.string.paymentSuccessfully)
                    else
                        getString(R.string.paymentFailed)
            }
        }

        viewModel.progressbarStatusLiveData.observe(this) {
            showProgressbar(it)
        }

    }

    private fun initialize() {
        binding.orderHistoryBtn.setOnClickListener {
            startActivityWithoutExtra(OrderHistoryActivity::class.java)
            finish()
        }

        binding.returnHomeBtn.setOnClickListener {
            startActivityWithoutExtra(MainActivity::class.java)
            finish()
        }

        binding.toolbar.onBackButtonClickListener = View.OnClickListener {
            startActivityWithoutExtra(MainActivity::class.java)
            finish()
        }

    }
}