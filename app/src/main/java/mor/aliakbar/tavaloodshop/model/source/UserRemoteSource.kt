package mor.aliakbar.tavaloodshop.model.source

import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mor.aliakbar.tavaloodshop.model.api.ApiService
import mor.aliakbar.tavaloodshop.model.dataclass.MessageResponse
import mor.aliakbar.tavaloodshop.model.dataclass.TokenResponse
import mor.aliakbar.tavaloodshop.model.dataclass.UserInformation
import mor.aliakbar.tavaloodshop.utils.Variable.CLIENT_ID
import mor.aliakbar.tavaloodshop.utils.Variable.CLIENT_SECRET
import mor.aliakbar.tavaloodshop.utils.Variable.GRANT_TYPE_VALUE
import javax.inject.Inject

class UserRemoteSource @Inject constructor(private val apiService: ApiService) : UserDataSource {

    override fun logIn(username: String, password: String): Flow<TokenResponse> {
        return flow {
            emit(
                apiService.login(JsonObject().apply {
                    addProperty("username", username)
                    addProperty("password", password)
                    addProperty("grant_type", GRANT_TYPE_VALUE)
                    addProperty("client_id", CLIENT_ID)
                    addProperty("client_secret", CLIENT_SECRET)
                })
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun signUp(username: String, password: String): Flow<MessageResponse> {
        return flow {
            emit(
                apiService.signUp(JsonObject().apply {
                    addProperty("email", username)
                    addProperty("password", password)
                })
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refreshToken: String) {
        TODO("Not yet implemented")
    }

    override fun saveUserName(username: String) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

    override fun saveUserInformation(userInformation: UserInformation) {
        TODO("Not yet implemented")
    }

    override fun getUserInformation(): UserInformation {
        TODO("Not yet implemented")
    }
}