package mor.aliakbar.tavaloodshop.model.repository

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.runBlocking
import mor.aliakbar.tavaloodshop.model.dataclass.TokenContainer
import mor.aliakbar.tavaloodshop.model.dataclass.TokenResponse
import mor.aliakbar.tavaloodshop.model.dataclass.UserInformation
import mor.aliakbar.tavaloodshop.model.source.UserDataSource
import mor.aliakbar.tavaloodshop.utils.CoroutineUtils.flowCatch
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteSource: UserDataSource,
    private val userLocalSource: UserDataSource
) : UserRepository {

    override fun logIn(username: String, password: String): Boolean {
        var isSuccess = false
        runBlocking {
            userRemoteSource.logIn(username, password).flowCatch().collect {
                isSuccess = true
                successLogIn(it, username)
            }
        }
        return isSuccess
    }

    override fun signUp(username: String, password: String): Boolean {
        var isSuccess = false
        runBlocking {
            userRemoteSource.signUp(username, password).flowCatch()
                .flatMapMerge {
                    userRemoteSource.logIn(username, password)
                }
                .collect {
                    isSuccess = true
                    successLogIn(it, username)
                }
        }
        return isSuccess
    }

    override fun loadToken() {
        userLocalSource.loadToken()
    }

    override fun signOut() {
        userLocalSource.signOut()
        TokenContainer.update(null, null)
    }

    override fun getUserInformation(): UserInformation {
        return userLocalSource.getUserInformation()
    }

    override fun saveUserInformation(userInformation: UserInformation) {
        userLocalSource.saveUserInformation(userInformation)
    }

    private fun successLogIn(response: TokenResponse, username: String) {
        userLocalSource.saveToken(response.access_token, response.refresh_token)
        userLocalSource.saveUserName(username)
    }

}