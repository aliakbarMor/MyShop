package mor.aliakbar.tavaloodshop.model.source

import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mor.aliakbar.tavaloodshop.model.api.ApiService
import mor.aliakbar.tavaloodshop.model.dataclass.OrderHistoryItem
import mor.aliakbar.tavaloodshop.model.dataclass.PaymentResult
import mor.aliakbar.tavaloodshop.model.dataclass.SubmitOrderResult
import mor.aliakbar.tavaloodshop.model.dataclass.UserInformation
import javax.inject.Inject

class OrderRemoteSource @Inject constructor(private val apiService: ApiService) : OrderDataSource {

    override fun submit(userInformation: UserInformation, paymentMethod: String):
            Flow<SubmitOrderResult> {
        return flow {
            emit(apiService.submitOrder(JsonObject().apply {
                addProperty("first_name", userInformation.firstName)
                addProperty("last_name", userInformation.lastName)
                addProperty("postal_code", userInformation.postalCode)
                addProperty("mobile", userInformation.phoneNumber)
                addProperty("address", userInformation.address)
                addProperty("payment_method", paymentMethod)
            }))
        }.flowOn(Dispatchers.IO)
    }

    override fun paymentResult(orderId: Int): Flow<PaymentResult> {
        return flow {
            emit(apiService.paymentResult(orderId))
        }.flowOn(Dispatchers.IO)
    }

    override fun list(): Flow<List<OrderHistoryItem>> {
        return flow {
            emit(apiService.orders())
        }.flowOn(Dispatchers.IO)
    }
}