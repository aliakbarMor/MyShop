package mor.aliakbar.tavaloodshop.model.repository

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.PaymentResult
import mor.aliakbar.tavaloodshop.model.dataclass.OrderHistoryItem
import mor.aliakbar.tavaloodshop.model.dataclass.SubmitOrderResult
import mor.aliakbar.tavaloodshop.model.dataclass.UserInformation
import mor.aliakbar.tavaloodshop.model.source.OrderDataSource


class OrderRepositoryImpl(private val orderDataSource: OrderDataSource) : OrderRepository {

    override fun submit(userInformation: UserInformation, paymentMethod: String):
            Flow<SubmitOrderResult> {
        return orderDataSource.submit(userInformation, paymentMethod)
    }

    override fun paymentResult(orderId: Int): Flow<PaymentResult> {
        return orderDataSource.paymentResult(orderId)
    }

    override fun list(): Flow<List<OrderHistoryItem>> {
        return orderDataSource.list()
    }

}