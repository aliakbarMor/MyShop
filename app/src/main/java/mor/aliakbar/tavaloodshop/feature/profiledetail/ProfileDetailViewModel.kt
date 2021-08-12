package mor.aliakbar.tavaloodshop.feature.profiledetail

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import mor.aliakbar.tavaloodshop.base.BaseViewModel
import mor.aliakbar.tavaloodshop.model.dataclass.UserInformation
import mor.aliakbar.tavaloodshop.model.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class ProfileDetailViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    var userInformation = MutableLiveData<UserInformation>()

    fun loadUserInformation() {
        userInformation.value = userRepository.getUserInformation()
    }

    fun saveUserInformation(userInformation: UserInformation) {
        userRepository.saveUserInformation(userInformation)
    }


}