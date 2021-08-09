package mor.aliakbar.tavaloodshop.model.repository

import kotlinx.coroutines.flow.Flow
import mor.aliakbar.tavaloodshop.model.dataclass.Banner
import mor.aliakbar.tavaloodshop.model.source.BannerDataSource
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(private val bannerRemoteSource: BannerDataSource) :
    BannerRepository {

    override fun get(): Flow<List<Banner>> {
        return bannerRemoteSource.get()
    }

}