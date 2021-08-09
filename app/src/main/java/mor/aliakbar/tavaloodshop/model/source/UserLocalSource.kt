package mor.aliakbar.tavaloodshop.model.source

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.MessageResponse
import mor.aliakbar.tavaloodshop.model.dataclass.TokenContainer
import mor.aliakbar.tavaloodshop.model.dataclass.TokenResponse
import mor.aliakbar.tavaloodshop.model.dataclass.UserInformation
import mor.aliakbar.tavaloodshop.utils.Variable
import javax.inject.Inject

class UserLocalSource @Inject constructor(private var sharedPreferences: SharedPreferences) :
    UserDataSource {

    override fun logIn(username: String, password: String): Flow<TokenResponse> {
        TODO("Not yet implemented")
    }

    override fun signUp(username: String, password: String): Flow<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(
            sharedPreferences.getString(Variable.SHARED_ACCESS_TOKE_KEY, null),
            sharedPreferences.getString(Variable.SHARED_REFRESH_TOKEN_KEY, null)
        )
    }

    override fun saveToken(token: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString(Variable.SHARED_ACCESS_TOKE_KEY, token)
            putString(Variable.SHARED_REFRESH_TOKEN_KEY, refreshToken)
        }.apply()
        TokenContainer.update(token, refreshToken)
    }

    override fun saveUserName(username: String) {
        sharedPreferences.edit().apply {
            putString(Variable.SHARED_EMAIL_KEY, username)
        }.apply()
    }

    override fun signOut() {
        sharedPreferences.edit().apply {
            clear()
        }.apply()
    }

    override fun saveUserInformation(userInformation: UserInformation) {
        sharedPreferences.edit().apply {
            putString(Variable.SHARED_FIRST_NAME_KEY, userInformation.firstName)
            putString(Variable.SHARED_LAST_NAME_KEY, userInformation.lastName)
            putString(Variable.SHARED_PHONE_NUMBER_KEY, userInformation.phoneNumber)
            putString(Variable.SHARED_POSTAL_CODE_KEY, userInformation.postalCode)
            putString(Variable.SHARED_ADDRESS_KEY, userInformation.address)
        }.apply()
    }

    override fun getUserInformation(): UserInformation {
        val userInformation = UserInformation(
            sharedPreferences.getString(Variable.SHARED_FIRST_NAME_KEY, "")!!,
            sharedPreferences.getString(Variable.SHARED_LAST_NAME_KEY, "")!!,
            sharedPreferences.getString(Variable.SHARED_PHONE_NUMBER_KEY, "")!!,
            sharedPreferences.getString(Variable.SHARED_POSTAL_CODE_KEY, "")!!,
            sharedPreferences.getString(Variable.SHARED_ADDRESS_KEY, "")!!
        )
        userInformation.userName = sharedPreferences.getString(Variable.SHARED_EMAIL_KEY, null)
        return userInformation
    }


}