package mor.aliakbar.tavaloodshop.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mor.aliakbar.tavaloodshop.services.loaddingImage.FrescoLoadingImage
import mor.aliakbar.tavaloodshop.services.loaddingImage.LoadingImageServices

@Module
@InstallIn(SingletonComponent::class)
abstract class BinderModule {

    @Binds
    abstract fun bindFrescoLoadingImage(frescoLoadingImage: FrescoLoadingImage): LoadingImageServices

}