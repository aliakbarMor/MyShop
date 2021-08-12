package mor.aliakbar.tavaloodshop.feature.profile

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.dataclass.TokenContainer
import mor.aliakbar.tavaloodshop.model.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    var isSignIn = MutableLiveData<Boolean>()
    var userName = MutableLiveData<String?>()

    fun setIsSignIn() {
        isSignIn.value = TokenContainer.token != null
    }

    fun getUserName() {
        userName.value = userRepository.getUserInformation().userName
    }

    fun signOut() {
        userRepository.signOut()
        isSignIn.value = false
        userName.value = null
    }
}