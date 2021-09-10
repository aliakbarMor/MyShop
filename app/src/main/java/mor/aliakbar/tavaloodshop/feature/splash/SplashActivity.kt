package mor.aliakbar.tavaloodshop.feature.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mor.aliakbar.tavaloodshop.R
import mor.aliakbar.tavaloodshop.base.BaseActivity
import mor.aliakbar.tavaloodshop.databinding.ActivitySplashBinding
import mor.aliakbar.tavaloodshop.feature.main.MainActivity
import mor.aliakbar.tavaloodshop.utils.ActivityUtils.startActivityWithoutExtra

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding = {
        ActivitySplashBinding.inflate(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val splashAnimation = AnimationUtils.loadAnimation(this@SplashActivity, R.anim.anim_splash)
        binding.tvAppName.animation = splashAnimation

        splashAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(500)
                    startActivityWithoutExtra(MainActivity::class.java)
                    finish()
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })


    }

}