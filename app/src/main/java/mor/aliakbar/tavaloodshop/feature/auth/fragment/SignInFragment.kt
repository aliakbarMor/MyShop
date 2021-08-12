package mor.aliakbar.tavaloodshop.feature.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseFragment
import mor.aliakbar.tavaloodshop.databinding.FragmentSignInBinding
import mor.aliakbar.tavaloodshop.feature.auth.AuthViewModel
import mor.aliakbar.tavaloodshop.utils.TextUtils.isValidEmail

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignInBinding
        get() = { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            FragmentSignInBinding.inflate(layoutInflater, viewGroup, b)
        }
    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()

    }

    private fun initialize() {
        binding.singInMaterialButton.setOnClickListener {
            if (!checkErrorEditTexts()) {
                val isSuccess = viewModel.userSignIn(
                    binding.emailEditTextSignIn.text.toString(),
                    binding.passwordEditTextSignIn.text.toString()
                )
                if (isSuccess) {
                    requireActivity().finish()
                }
            }

        }

        binding.signUpLinkMaterialButtonSignIn.setOnClickListener {
            activity?.findNavController(R.id.authContainerView)!!
                .navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun checkErrorEditTexts(): Boolean {
        var error = false
        if (binding.emailEditTextSignIn.text.isEmpty()) {
            binding.emailEditTextSignIn.error = getString(R.string.pleaseEnterEmail)
            error = true
        }
        if (!binding.emailEditTextSignIn.text.isValidEmail()) {
            binding.emailEditTextSignIn.error = getString(R.string.pleaseEnterValidEmail)
            error = true
        }
        if (binding.passwordEditTextSignIn.text.isEmpty()) {
            binding.passwordEditTextSignIn.error = getString(R.string.pleaseEnterPassword)
            error = true
        }
        if (binding.passwordEditTextSignIn.text.length < 6) {
            binding.passwordEditTextSignIn.error =
                getString(R.string.passwordMustBeAtLeastSixDigits)
            error = true
        }
        return error
    }
}


