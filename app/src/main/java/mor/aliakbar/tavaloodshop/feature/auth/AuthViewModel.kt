package mor.aliakbar.tavaloodshop.feature.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    fun userSignIn(username: String, password: String): Boolean {
        progressbarStatusLiveData.value = true
        return userRepository.logIn(username, password)
    }

    fun userSignUp(username: String, password: String): Boolean {
        progressbarStatusLiveData.value = true
        return userRepository.signUp(username, password)
    }

}