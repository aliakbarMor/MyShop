package mor.aliakbar.tavaloodshop.model.dataclass

import com.google.gson.annotations.SerializedName

data class SubmitOrderResult(
    @SerializedName("bank_gateway_url")
    val bankGatewayUrl: String,
    @SerializedName("order_id")
    val orderId: Int,
)