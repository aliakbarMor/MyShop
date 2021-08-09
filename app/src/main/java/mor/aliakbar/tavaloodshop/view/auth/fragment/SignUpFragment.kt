package mor.aliakbar.tavaloodshop.view.auth.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseFragment
import mor.aliakbar.tavaloodshop.databinding.FragmentSignInBinding
import mor.aliakbar.tavaloodshop.databinding.FragmentSignUpBinding
import mor.aliakbar.tavaloodshop.utils.isValidEmail
import mor.aliakbar.tavaloodshop.view.auth.AuthViewModel

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSignUpBinding
        get() = { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            FragmentSignUpBinding.inflate(layoutInflater, viewGroup, b)
        }
    private val viewModel: AuthViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
    }

    private fun initializeViews() {
        binding.signInLinkMaterialButtonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.signUpMaterialButton.setOnClickListener {
            if (validationOfEditTexts()) {
                val isSuccess = viewModel.userSignUp(
                    binding.emailEditTextSignUp.text.toString(),
                    binding.passwordEditTextSignUp.text.toString()
                )
                if (isSuccess) {
                    requireActivity().finish()
                }
            } else
                setErrorForEditTexts()
        }
    }

    private fun setErrorForEditTexts() {
        if (binding.emailEditTextSignUp.text.isEmpty()) {
            binding.emailEditTextSignUp.error = getString(R.string.pleaseEnterEmail)
        }
        if (!binding.emailEditTextSignUp.text.isValidEmail()) {
            binding.emailEditTextSignUp.error = getString(R.string.pleaseEnterValidEmail)
        }
        if (binding.passwordEditTextSignUp.text.isEmpty()) {
            binding.passwordEditTextSignUp.error = getString(R.string.pleaseEnterPassword)
        }
        if (binding.passwordEditTextSignUp.text.length < 6) {
            binding.passwordEditTextSignUp.error =
                getString(R.string.onlyPasswordsLongerThan8CharIsValid)
        }
    }

    private fun validationOfEditTexts(): Boolean {
        return binding.emailEditTextSignUp.text.isNotEmpty()
                && binding.emailEditTextSignUp.text.isValidEmail()
                && binding.passwordEditTextSignUp.text.isNotEmpty()
                && binding.passwordEditTextSignUp.text.length >= 6
    }

}