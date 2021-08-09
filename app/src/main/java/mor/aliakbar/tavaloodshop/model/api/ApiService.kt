package mor.aliakbar.tavaloodshop.model.api

import com.google.gson.JsonObject
import mor.aliakbar.tavaloodshop.model.dataclass.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("product/list")
    suspend fun getProducts(@Query("sort") sort: String): List<Product>

    @GET("banner/slider")
    suspend fun getBanners(): List<Banner>

    @GET("product/search")
    suspend fun searchInProducts(@Query("q") productTitle: String): List<Product>

    @GET("comment/list")
    suspend fun getComment(@Query("product_id") productId: String): List<Comment>

    //    TODO
    @POST("comment/add")
    suspend fun addComment(@Body jsonObject: JsonObject): Comment

    @POST("auth/token")
    suspend fun login(@Body jsonObject: JsonObject): TokenResponse

    @POST("user/register")
    suspend fun signUp(@Body jsonObject: JsonObject): MessageResponse

    @POST("auth/token")
    fun refreshToken(@Body jsonObject: JsonObject): Call<TokenResponse>

    @POST("cart/add")
    suspend fun addToCart(@Body jsonObject: JsonObject): AddToCartResponse

    @POST("cart/remove")
    suspend fun removeItemFromCart(@Body jsonObject: JsonObject): MessageResponse

    @GET("cart/list")
    suspend fun getCart(): CartResponse

    @POST("cart/changeCount")
    suspend fun changeCount(@Body jsonObject: JsonObject): AddToCartResponse

    @GET("cart/count")
    suspend fun getCartItemsCount(): CartItemCount

    @POST("order/submit")
    suspend fun submitOrder(@Body jsonObject: JsonObject): SubmitOrderResult

    @GET("order/checkout")
    suspend fun paymentResult(@Query("order_id") orderId: Int): PaymentResult

    @GET("order/list")
    suspend fun orders(): List<OrderHistoryItem>

}
