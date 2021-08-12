package mor.aliakbar.tavaloodshop.feature.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseFragment
import mor.aliakbar.tavaloodshop.databinding.FragmentSignUpBinding
import mor.aliakbar.tavaloodshop.feature.auth.AuthViewModel
import mor.aliakbar.tavaloodshop.utils.TextUtils.isValidEmail

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignUpBinding
        get() = { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            FragmentSignUpBinding.inflate(layoutInflater, viewGroup, b)
        }
    private val viewModel: AuthViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    private fun initialize() {
        binding.signInLinkMaterialButtonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.signUpMaterialButton.setOnClickListener {
            if (!checkErrorEditTexts()) {
                val isSuccess = viewModel.userSignUp(
                    binding.emailEditTextSignUp.text.toString(),
                    binding.passwordEditTextSignUp.text.toString()
                )
                if (isSuccess) {
                    requireActivity().finish()
                }
            }
        }
    }

    private fun checkErrorEditTexts(): Boolean {
        var error = false
        if (binding.emailEditTextSignUp.text.isEmpty()) {
            binding.emailEditTextSignUp.error = getString(R.string.pleaseEnterEmail)
            error = true
        }
        if (!binding.emailEditTextSignUp.text.isValidEmail()) {
            binding.emailEditTextSignUp.error = getString(R.string.pleaseEnterValidEmail)
            error = true
        }
        if (binding.passwordEditTextSignUp.text.isEmpty()) {
            binding.passwordEditTextSignUp.error = getString(R.string.pleaseEnterPassword)
            error = true
        }
        if (binding.passwordEditTextSignUp.text.length < 6) {
            binding.passwordEditTextSignUp.error =
                getString(R.string.passwordMustBeAtLeastSixDigits)
            error = true
        }
        return error
    }

}