package mor.aliakbar.tavaloodshop.services.exception

import mor.aliakbar.tavaloodshop.R
import org.json.JSONObject
import retrofit2.HttpException

class AppExceptionMapper {

    companion object {

        fun map(throwable: Throwable): AppException {
            when (throwable) {

                is HttpException -> {
                    when (throwable.code()) {
                        //authentication error
                        401 -> {
                            //getting json body from exception
                            val errorJsonObject =
                                JSONObject(throwable.response()?.errorBody()!!.string())
                            //getting message key from json body
                            val errorMessage = errorJsonObject.getString("message")
                            //only when this message thrown that username or password wrong
                            return if (errorMessage == "The user credentials were incorrect.")
                                AppException(
                                    ExceptionType.SIMPLE,
                                    resourceStringMessage = R.string.usernameOrPasswordIsIncorrect
                                )
                            //this is the default message when thrown Http 401 exception
                            else {
                                AppException(
                                    ExceptionType.AUTH,
                                    exceptionMessage = "$errorMessage!"
                                )
                            }
                        }
                        //internal server error (proxy set)
                        500 -> {
                            return AppException(
                                ExceptionType.SIMPLE,
                                resourceStringMessage = R.string.errorReceivingInformation
                            )
                        }
                        //exciting username in server error
                        422 -> {
                            return AppException(
                                ExceptionType.SIMPLE,
                                resourceStringMessage = R.string.signUpUsernameErrorMessage
                            )
                        }
                        else -> {
                            return AppException(
                                ExceptionType.SIMPLE,
                                resourceStringMessage = R.string.unspecifiedError
                            )
                        }
                    }
                }

                else -> {
                    return AppException(
                        ExceptionType.SIMPLE,
                        resourceStringMessage = R.string.unspecifiedError
                    )
                }

            }
        }
    }
}