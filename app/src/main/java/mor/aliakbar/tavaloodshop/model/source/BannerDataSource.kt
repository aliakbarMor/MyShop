package mor.aliakbar.tavaloodshop.model.source

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.Banner

interface BannerDataSource {

    fun get(): Flow<List<Banner>>
}