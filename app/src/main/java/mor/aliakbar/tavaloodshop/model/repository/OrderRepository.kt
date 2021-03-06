package mor.aliakbar.tavaloodshop.model.repository

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.OrderHistoryItem
import mor.aliakbar.tavaloodshop.model.dataclass.PaymentResult
import mor.aliakbar.tavaloodshop.model.dataclass.SubmitOrderResult
import mor.aliakbar.tavaloodshop.model.dataclass.UserInformation

interface OrderRepository {

    fun submit(userInformation: UserInformation, paymentMethod: String): Flow<SubmitOrderResult>

    fun paymentResult(orderId: Int): Flow<PaymentResult>

    fun list(): Flow<List<OrderHistoryItem>>
}