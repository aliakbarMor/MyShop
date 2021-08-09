package mor.aliakbar.tavaloodshop.model.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Author(
    val email: String,
) : Parcelable