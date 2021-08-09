package mor.aliakbar.tavaloodshop.model.dataclass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Banner(
    var id: Long,
    var title: String,
    @SerializedName("image")
    var url: String
) : Parcelable