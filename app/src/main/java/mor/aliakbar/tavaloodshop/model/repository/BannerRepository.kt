package mor.aliakbar.tavaloodshop.model.repository

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.Banner

interface BannerRepository {

    fun get(): Flow<List<Banner>>
}