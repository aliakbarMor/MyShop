package mor.aliakbar.tavaloodshop.model.dataclass

import android.util.Log


object TokenContainer {
    var token: String? = null
        private set
    var refreshToken: String? = null
        private set

    fun update(token: String?, refreshToken: String?) {
        Log.i(
            "update token",
            "Access Token-> ${token?.substring(0, 10)}, Refresh Token-> ${refreshToken?.substring(0, 10)}"
        )
        this.token = token
        this.refreshToken = refreshToken
    }
}