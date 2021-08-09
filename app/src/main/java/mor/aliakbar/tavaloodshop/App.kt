package mor.aliakbar.tavaloodshop

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp
import mor.aliakbar.tavaloodshop.model.repository.UserRepository
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate() {
        super.onCreate()

        userRepository.loadToken()

        Fresco.initialize(this)


    }
}