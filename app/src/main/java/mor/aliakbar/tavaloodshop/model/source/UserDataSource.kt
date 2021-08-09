package mor.aliakbar.tavaloodshop.model.source

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.MessageResponse
import mor.aliakbar.tavaloodshop.model.dataclass.TokenResponse
import mor.aliakbar.tavaloodshop.model.dataclass.UserInformation

interface UserDataSource {

    fun logIn(username: String, password: String): Flow<TokenResponse>

    fun signUp(username: String, password: String): Flow<MessageResponse>

    fun loadToken()

    fun saveToken(token: String, refreshToken: String)

    fun saveUserName(username: String)

    fun signOut()

    fun saveUserInformation(userInformation: UserInformation)

    fun getUserInformation(): UserInformation

}