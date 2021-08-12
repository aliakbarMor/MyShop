package mor.aliakbar.tavaloodshop.feature.auth

import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import mor.aliakbar.tavaloodshop.base.BaseActivity
import mor.aliakbar.tavaloodshop.databinding.ActivityAuthBinding

@AndroidEntryPoint
class AuthActivity : BaseActivity<ActivityAuthBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityAuthBinding =
        { layoutInflater: LayoutInflater -> ActivityAuthBinding.inflate(layoutInflater) }

}