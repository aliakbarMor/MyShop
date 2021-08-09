package mor.aliakbar.tavaloodshop.view.checkout

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseActivity
import mor.aliakbar.tavaloodshop.databinding.ActivityAuthBinding
import mor.aliakbar.tavaloodshop.databinding.ActivityCheckOutBinding
import mor.aliakbar.tavaloodshop.utils.formatPrice
import mor.aliakbar.tavaloodshop.view.main.MainActivity

@AndroidEntryPoint
class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityCheckOutBinding =
        { layoutInflater: LayoutInflater -> ActivityCheckOutBinding.inflate(layoutInflater) }
    val viewModel: CheckOutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        binding.orderHistoryBtn.setOnClickListener {
//            Todo
        }

        binding.returnHomeBtn.setOnClickListener {
            gotoHome()
        }

        binding.toolbar.onBackButtonClickListener = View.OnClickListener {
            gotoHome()
        }

    }

    private fun gotoHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}