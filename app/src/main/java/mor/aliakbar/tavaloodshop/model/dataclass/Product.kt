package mor.aliakbar.tavaloodshop.model.dataclass

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Products")
@Parcelize
data class Product(
    @PrimaryKey(autoGenerate = false)
    var id: Long,
    var title: String,
    @SerializedName("image")
    var imageUrl: String,
    @SerializedName("price")
    var currentPrice: Int,
    var discount: Int,
    var stock: Int,
    @SerializedName("views")
    var viewCount: Int,
    val categoryId: Int,

    var isFavorite: Boolean = false
) : Parcelable
