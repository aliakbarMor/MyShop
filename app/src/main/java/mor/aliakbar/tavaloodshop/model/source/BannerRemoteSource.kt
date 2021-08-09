package mor.aliakbar.tavaloodshop.model.source

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import mor.aliakbar.tavaloodshop.model.api.ApiService
import mor.aliakbar.tavaloodshop.model.dataclass.Banner
import javax.inject.Inject

class BannerRemoteSource @Inject constructor(val apiService: ApiService) : BannerDataSource {

    override fun get(): Flow<List<Banner>> {
        return flow {
            emit(apiService.getBanners())
        }.flowOn(Dispatchers.IO)
    }
}