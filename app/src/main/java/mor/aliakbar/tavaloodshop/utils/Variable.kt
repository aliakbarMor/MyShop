package mor.aliakbar.tavaloodshop.utils

object Variable {

    //base url use for creating retrofit instance
//    const val BASE_URL = "http://192.168.1.5:8080/tavaloodshop/"
    const val BASE_URL = "http://expertdevelopers.ir/api/v1/"

    const val EXTRA_KEY_DATA = "EXTRA_KEY_DATA"
    const val EXTRA_KEY_ID = "EXTRA_KEY_ID"

    const val EXTRA_KEY_CATEGORY_ID = "EXTRA_KEY_CATEGORY_ID"
    const val EXTRA_KEY_CATEGORY_PARENT_ID = "EXTRA_KEY_CATEGORY_PARENT_ID"

    //receive Product list from server
    const val PRODUCT_SORT_LATEST = 0
    const val PRODUCT_SORT_POPULAR = 1
    const val PRODUCT_SORT_PRICE_DESC = 2
    const val PRODUCT_SORT_PRICE_ASC = 3

    //view types for product adapter
    const val PRODUCT_VIEW_TYPE_ROUNDED = 0
    const val PRODUCT_VIEW_TYPE_GRID = 1
    const val PRODUCT_VIEW_TYPE_LARGE = 2

    //json values for sending parameters to the server
    const val GRANT_TYPE_VALUE = "password"
    const val CLIENT_ID = 2
    const val CLIENT_SECRET = "kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC"

    //shared preferences keys
    const val SHARED_ACCESS_TOKE_KEY = "access_token"
    const val SHARED_REFRESH_TOKEN_KEY = "refresh_token"
    const val SHARED_EMAIL_KEY = "email"
    const val SHARED_FIRST_NAME_KEY = "first_name"
    const val SHARED_LAST_NAME_KEY = "last_name"
    const val SHARED_PHONE_NUMBER_KEY = "phone_number"
    const val SHARED_POSTAL_CODE_KEY = "postal_code"
    const val SHARED_ADDRESS_KEY = "address"

    //view type for cart adapter
    const val CART_VIEW_TYPE_CART_ITEM = 0
    const val CART_VIEW_TYPE_PURCHASE = 1

    //payment method
    const val PAYMENT_METHOD_ONLINE = "online"
    const val PAYMENT_METHOD_CASH_ON_DELIVERY = "cash_on_delivery"

    //view types for product adapter
    const val CATEGORY_VIEW_TYPE_LINEAR = 0
    const val CATEGORY_VIEW_TYPE_COMPACT = 1


}