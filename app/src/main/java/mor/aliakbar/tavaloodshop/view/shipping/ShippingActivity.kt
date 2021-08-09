package mor.aliakbar.tavaloodshop.view.shipping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseActivity
import mor.aliakbar.tavaloodshop.databinding.ActivityProductListBinding
import mor.aliakbar.tavaloodshop.databinding.ActivityShippingBinding
import mor.aliakbar.tavaloodshop.databinding.ItemPurchaseDetailsBinding
import mor.aliakbar.tavaloodshop.model.dataclass.UserInformation
import mor.aliakbar.tavaloodshop.utils.Variable.EXTRA_KEY_ID
import mor.aliakbar.tavaloodshop.utils.Variable.PAYMENT_METHOD_CASH_ON_DELIVERY
import mor.aliakbar.tavaloodshop.utils.Variable.PAYMENT_METHOD_ONLINE
import mor.aliakbar.tavaloodshop.utils.openUrlInCustomTab
import mor.aliakbar.tavaloodshop.utils.validationIranianPhoneNumber
import mor.aliakbar.tavaloodshop.view.cart.CartAdapter
import mor.aliakbar.tavaloodshop.view.checkout.CheckOutActivity

@AndroidEntryPoint
class ShippingActivity : BaseActivity<ActivityShippingBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityShippingBinding =
        { layoutInflater: LayoutInflater -> ActivityShippingBinding.inflate(layoutInflater) }
    val viewModel: ShippingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewHolder = CartAdapter.PurchaseViewHolder(binding.purchaseDetailView)
        viewModel.purchaseDetail.observe(this) {
            it?.let {
                viewHolder.bind(it)
            }
        }

        viewModel.userInformation.observe(this) {
            if (it.firstName != "") {
                binding.firstNameEt.setText(it.firstName)
            }
            if (it.lastName != "") {
                binding.lastNameEt.setText(it.lastName)
            }
            if (it.phoneNumber != "") {
                binding.phoneNumberEt.setText(it.phoneNumber)
            }
            if (it.postalCode != "") {
                binding.postalCodeEt.setText(it.postalCode)
            }
            if (it.address != "") {
                binding.addressEt.setText(it.address)
            }
        }

        viewModel.submitOrderStatus.observe(this) {
            if (it.bankGatewayUrl.isNotEmpty()) {
                openUrlInCustomTab(this@ShippingActivity, it.bankGatewayUrl)
            } else {
                startActivity(
                    Intent(this@ShippingActivity, CheckOutActivity::class.java).apply {
                        putExtra(EXTRA_KEY_ID, it.orderId)
                    }
                )
            }
            finish()
        }

        viewModel.progressbarStatusLiveData.observe(this) {
            showProgressbar(it)
        }

        val onClickListener = View.OnClickListener {
            if (!checkError()) {
                viewModel.submitOrder(
                    UserInformation(
                        binding.firstNameEt.text.toString(),
                        binding.lastNameEt.text.toString(),
                        binding.postalCodeEt.text.toString(),
                        binding.phoneNumberEt.text.toString(),
                        binding.addressEt.text.toString(),
                    ),
                    if (it.id == R.id.onlinePaymentBtn) PAYMENT_METHOD_ONLINE else PAYMENT_METHOD_CASH_ON_DELIVERY
                )
            }
        }
        binding.onlinePaymentBtn.setOnClickListener(onClickListener)
        binding.codBtn.setOnClickListener(onClickListener)

        binding.toolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
    }

    private fun checkError(): Boolean {
        var error = false
        if (binding.firstNameEt.text.isNullOrEmpty()) {
            binding.firstNameEt.error = getString(R.string.firstNameError)
            error = true
        }
        if (binding.lastNameEt.text.isNullOrEmpty()) {
            binding.lastNameEt.error = getString(R.string.lastNameError)
            error = true
        }
        if (binding.phoneNumberEt.text.isNullOrEmpty()) {
            binding.phoneNumberEt.error = getString(R.string.phoneNumberError)
            error = true
        }
        if (!validationIranianPhoneNumber(binding.phoneNumberEt.text.toString())) {
            binding.phoneNumberEt.error = getString(R.string.phoneNumberSchemeError)
            error = true
        }
        if (binding.postalCodeEt.text.isNullOrEmpty()) {
            binding.postalCodeEt.error = getString(R.string.postalCardError)
            error = true
        }
        if (binding.postalCodeEt.text!!.length != 10) {
            binding.postalCodeEt.error = getString(R.string.postalCardSchemeError)
            error = true
        }
        if (binding.addressEt.text.isNullOrEmpty()) {
            binding.addressEt.error = getString(R.string.addressError)
            error = true
        }
        if (binding.addressEt.text!!.length <= 20) {
            binding.addressEt.error = getString(R.string.addressSchemeError)
            error = true
        }
        return error
    }

}
