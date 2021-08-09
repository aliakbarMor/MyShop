package mor.aliakbar.tavaloodshop.services.exception

import androidx.annotation.StringRes

data class AppException constructor(
    val exceptionType: ExceptionType,
    @StringRes val resourceStringMessage: Int = 0,
    val exceptionMessage: String? = null,
) : Throwable()