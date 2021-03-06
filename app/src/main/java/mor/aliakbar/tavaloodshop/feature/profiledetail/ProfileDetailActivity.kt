package mor.aliakbar.tavaloodshop.feature.profiledetail

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
import mor.aliakbar.tavaloodshop.databinding.ActivityProfileDetailBinding
import mor.aliakbar.tavaloodshop.feature.auth.AuthActivity
import mor.aliakbar.tavaloodshop.model.dataclass.TokenContainer
import mor.aliakbar.tavaloodshop.model.dataclass.UserInformation
import mor.aliakbar.tavaloodshop.utils.ActivityUtils.backToPreviousPageListener
import mor.aliakbar.tavaloodshop.utils.TextUtils.validationIranianPhoneNumber

@AndroidEntryPoint
class ProfileDetailActivity : BaseActivity<ActivityProfileDetailBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityProfileDetailBinding =
        { layoutInflater -> ActivityProfileDetailBinding.inflate(layoutInflater) }
    val viewModel: ProfileDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
        observe()

    }

    private fun observe() {
        viewModel.userInformation.observe(this) {
            binding.apply {
                firstNameEt.setText(it.firstName)
                lastNameEt.setText(it.lastName)
                phoneNumberEt.setText(it.phoneNumber)
                addressEt.setText(it.address)
                postalCodeEt.setText(it.postalCode)
                it.userName.let { emailEt.setText(it) }
            }
        }
    }

    private fun initialize() {
        if (TokenContainer.token != null) {
            viewModel.loadUserInformation()
        } else {
            val emptyStateView =
                showEmptyState(R.layout.view_empty_state, R.id.emptyStateRootView)
            emptyStateView?.let { view ->
                view.findViewById<TextView>(R.id.emptyStateMessageTv).text =
                    getString(R.string.cartEmptyStateLoginRequired)
                view.findViewById<ImageView>(R.id.imageEmptyState)
                    .setImageResource(R.drawable.ic_empty_state_profile)
                view.findViewById<Button>(R.id.emptyStateCtaBtn).setOnClickListener {
                    startActivity(Intent(this, AuthActivity::class.java))
                }
            }
        }

        binding.saveInformationButton.setOnClickListener {
            if (!checkError()) {
                binding.apply {
                    viewModel.saveUserInformation(
                        UserInformation(
                            firstNameEt.text.toString(),
                            lastNameEt.text.toString(),
                            postalCodeEt.text.toString(),
                            phoneNumberEt.text.toString(),
                            addressEt.text.toString()
                        )
                    )
                }
                finish()
            }
        }

        binding.toolbar.onBackButtonClickListener = backToPreviousPageListener()
    }

    private fun checkError(): Boolean {
        var error = false
        if (!binding.phoneNumberEt.text.toString().validationIranianPhoneNumber()
            && binding.phoneNumberEt.text.toString() != ""
        ) {
            binding.phoneNumberEt.error = getString(R.string.phoneNumberSchemeError)
            error = true
        }
        if (binding.postalCodeEt.text!!.length != 10 && binding.postalCodeEt.text.toString() != "") {
            binding.postalCodeEt.error = getString(R.string.postalCardSchemeError)
            error = true
        }
        if (binding.addressEt.text!!.length <= 20 && binding.addressEt.text.toString() != "") {
            binding.addressEt.error = getString(R.string.addressSchemeError)
            error = true
        }
        return error
    }

}