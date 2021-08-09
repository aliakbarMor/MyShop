package mor.aliakbar.tavaloodshop.view.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseActivity
import mor.aliakbar.tavaloodshop.databinding.ActivityFavoriteBinding
import mor.aliakbar.tavaloodshop.databinding.ActivityMainBinding
import mor.aliakbar.tavaloodshop.model.dataclass.CartItemCount
import mor.aliakbar.tavaloodshop.utils.convertDpToPixel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        { layoutInflater: LayoutInflater -> ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.mainNavigationContainer
        ) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        binding.bottomNavigationMain.setupWithNavController(navController)

        // Setup the ActionBar with navController and 4 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.category, R.id.cart, R.id.profile, R.id.home)
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCartItemsCountChangeEvent(cartItemCount: CartItemCount) {
        binding.bottomNavigationMain.getOrCreateBadge(R.id.cart).apply {
            badgeGravity = BadgeDrawable.BOTTOM_START
            backgroundColor =
                MaterialColors.getColor(binding.bottomNavigationMain, R.attr.colorPrimary)
            verticalOffset = convertDpToPixel(10f, this@MainActivity).toInt()
            number = cartItemCount.count
            isVisible = cartItemCount.count > 0
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCartItemsCount()
    }

}