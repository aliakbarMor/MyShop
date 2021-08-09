package mor.aliakbar.tavaloodshop.model.api

import android.util.Log
import com.google.gson.JsonObject
import mor.aliakbar.tavaloodshop.model.dataclass.TokenContainer
import mor.aliakbar.tavaloodshop.model.dataclass.TokenResponse
import mor.aliakbar.tavaloodshop.model.source.UserDataSource
import mor.aliakbar.tavaloodshop.utils.Variable.CLIENT_ID
import mor.aliakbar.tavaloodshop.utils.Variable.CLIENT_SECRET
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AppAuthenticator : Authenticator {
    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var userLocalDataSource: UserDataSource
    override fun authenticate(route: Route?, response: Response): Request? {
        if (TokenContainer.token != null && TokenContainer.refreshToken != null && !response.request.url.pathSegments.last()
                .equals("token", false)
        ) {
            try {
                val token = refreshToken()
                if (token.isEmpty())
                    return null

                return response.request.newBuilder().header("Authorization", "Bearer $token")
                    .build()

            } catch (exception: Exception) {
                Log.e("App Authenticator error", exception.message.toString())
            }
        }

        return null
    }

    private fun refreshToken(): String {
        val response: retrofit2.Response<TokenResponse> =
            apiService.refreshToken(JsonObject().apply {
                addProperty("grant_type", "refresh_token")
                addProperty("refresh_token", TokenContainer.refreshToken)
                addProperty("client_id", CLIENT_ID)
                addProperty("client_secret", CLIENT_SECRET)
            }).execute()
        response.body()?.let {
            userLocalDataSource.saveToken(it.access_token, it.refresh_token)
            return it.access_token
        }

        return ""
    }

}