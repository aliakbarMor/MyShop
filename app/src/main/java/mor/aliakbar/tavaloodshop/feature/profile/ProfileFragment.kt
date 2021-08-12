package mor.aliakbar.tavaloodshop.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseFragment
import mor.aliakbar.tavaloodshop.databinding.FragmentProfileBinding
import mor.aliakbar.tavaloodshop.feature.auth.AuthActivity
import mor.aliakbar.tavaloodshop.feature.favorite.FavoriteActivity
import mor.aliakbar.tavaloodshop.feature.orderhistory.OrderHistoryActivity
import mor.aliakbar.tavaloodshop.feature.profiledetail.ProfileDetailActivity
import mor.aliakbar.tavaloodshop.model.dataclass.CartItemCount
import mor.aliakbar.tavaloodshop.utils.ActivityUtils.startActivityWithoutExtra
import org.greenrobot.eventbus.EventBus

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = { layoutInflater: LayoutInflater, viewGroup: ViewGroup?, b: Boolean ->
            FragmentProfileBinding.inflate(layoutInflater, viewGroup, b)
        }
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        observes()

    }

    override fun onStart() {
        super.onStart()
        viewModel.setIsSignIn()
    }

    private fun observes() {
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
            if (!it.isNullOrEmpty()) {
                binding.apply {
                    authBtn.text = getString(R.string.signOut)
                    authBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sign_out, 0)
                    usernameTv.text = viewModel.userName.value
                    authBtn.setOnClickListener {
                        viewModel.signOut()
                        EventBus.getDefault().postSticky(CartItemCount(0))
                    }
                }
            }
        }
    }

    private fun initialize() {
        binding.editProfileBtn.setOnClickListener {
            requireActivity().startActivityWithoutExtra(ProfileDetailActivity::class.java)
        }

        binding.favoriteProductsBtn.setOnClickListener {
            requireActivity().startActivityWithoutExtra(FavoriteActivity::class.java)
        }

        binding.orderHistoryBtn.setOnClickListener {
            requireActivity().startActivityWithoutExtra(OrderHistoryActivity::class.java)
        }
    }

}