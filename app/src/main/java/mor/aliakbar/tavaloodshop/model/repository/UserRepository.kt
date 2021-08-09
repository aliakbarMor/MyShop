package mor.aliakbar.tavaloodshop.model.repository

import mor.aliakbar.tavaloodshop.model.dataclass.UserInformation


interface UserRepository {

    fun logIn(username: String, password: String): Boolean

    fun signUp(username: String, password: String): Boolean

    fun loadToken()

    fun signOut()

    fun getUserInformation(): UserInformation

    fun saveUserInformation(userInformation: UserInformation)

}