package mor.aliakbar.tavaloodshop.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseFragment
import mor.aliakbar.tavaloodshop.databinding.FragmentHomeBinding
import mor.aliakbar.tavaloodshop.view.auth.AuthActivity
import mor.aliakbar.tavaloodshop.view.favorite.FavoriteActivity
import mor.aliakbar.tavaloodshop.view.orderhistory.OrderHistoryActivity
import mor.aliakbar.tavaloodshop.view.profiledetail.ProfileDetailActivity
import mor.aliakbar.tavaloodshop.databinding.FragmentProfileBinding as FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            FragmentProfileBinding.inflate(layoutInflater, viewGroup, b)
        }
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isSignIn.observe(viewLifecycleOwner) {
            if (it)
                viewModel.getUserName()
            else
                binding.apply {
                    authBtn.text = getString(R.string.signIn)
                    authBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sign_in, 0)
                    usernameTv.text = getString(R.string.guest_user)
                    authBtn.setOnClickListener {
                        startActivity(Intent(requireContext(), AuthActivity::class.java))
                    }
                }
        }

        viewModel.userName.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    authBtn.text = getString(R.string.signOut)
                    authBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sign_out, 0)
                    usernameTv.text = viewModel.userName.value
                    authBtn.setOnClickListener {
                        viewModel.signOut()
                    }
                }
            }

        }

        binding.editProfileBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileDetailActivity::class.java))
        }

        binding.favoriteProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext(), FavoriteActivity::class.java))
        }

        binding.orderHistoryBtn.setOnClickListener {
            startActivity(Intent(requireContext(), OrderHistoryActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.setIsSignIn()
    }
}