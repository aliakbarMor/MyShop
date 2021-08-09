package mor.aliakbar.tavaloodshop.model.dataclass

data class UserInformation(
    val firstName: String,
    val lastName: String,
    val postalCode: String,
    val phoneNumber: String,
    val address: String
){
    var userName:String? = null
}