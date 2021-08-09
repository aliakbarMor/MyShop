package mor.aliakbar.tavaloodshop.model.dataclass

import androidx.annotation.StringRes

data class EmptyState(
    val mustShowEmptyState: Boolean,
    @StringRes val emptyStateMessage: Int = 0,
    val mustShowCallToActionButton: Boolean = false
)
