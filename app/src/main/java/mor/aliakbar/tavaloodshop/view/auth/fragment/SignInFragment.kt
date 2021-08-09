package mor.aliakbar.tavaloodshop.view.auth.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseFragment
import mor.aliakbar.tavaloodshop.databinding.FragmentSignInBinding
import mor.aliakbar.tavaloodshop.utils.isValidEmail
import mor.aliakbar.tavaloodshop.view.auth.AuthViewModel

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignInBinding
        get() = { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            FragmentSignInBinding.inflate(layoutInflater, viewGroup, b)
        }
    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.singInMaterialButton.setOnClickListener {
            if (validationOfEditTexts()) {

                val isSuccess = viewModel.userSignIn(
                    binding.emailEditTextSignIn.text.toString(),
                    binding.passwordEditTextSignIn.text.toString()
                )
                if (isSuccess) {
                    requireActivity().finish()
                } else
                    setErrorForEditTexts()
            }

        }
        binding.signUpLinkMaterialButtonSignIn.setOnClickListener {
            activity?.findNavController(R.id.authContainerView)!!
                .navigate(R.id.action_signInFragment_to_signUpFragment)
        }

    }

    private fun validationOfEditTexts(): Boolean {
        return binding.emailEditTextSignIn.text.isNotEmpty()
                && binding.emailEditTextSignIn.text.isValidEmail()
                && binding.passwordEditTextSignIn.text.isNotEmpty()
                && binding.passwordEditTextSignIn.text.length >= 6
    }

    private fun setErrorForEditTexts() {
        if (binding.emailEditTextSignIn.text.isEmpty()) {
            binding.emailEditTextSignIn.error = getString(R.string.pleaseEnterEmail)
        }
        if (!binding.emailEditTextSignIn.text.isValidEmail()) {
            binding.emailEditTextSignIn.error = getString(R.string.pleaseEnterValidEmail)
        }
        if (binding.passwordEditTextSignIn.text.isEmpty()) {
            binding.passwordEditTextSignIn.error = getString(R.string.pleaseEnterPassword)
        }
        if (binding.passwordEditTextSignIn.text.length < 6) {
            binding.passwordEditTextSignIn.error =
                getString(R.string.onlyPasswordsLongerThan8CharIsValid)
        }
    }
}


