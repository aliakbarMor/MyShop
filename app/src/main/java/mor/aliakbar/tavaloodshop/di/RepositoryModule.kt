package mor.aliakbar.tavaloodshop.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mor.aliakbar.tavaloodshop.model.api.ApiService
import mor.aliakbar.tavaloodshop.model.database.AppDatabase
import mor.aliakbar.tavaloodshop.model.repository.*
import mor.aliakbar.tavaloodshop.model.source.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideProductRepository(
        apiService: ApiService, appDatabase: AppDatabase
    ): ProductRepository {
        return ProductRepositoryImpl(ProductRemoteSource(apiService), appDatabase.productDao)
    }

    @Provides
    fun provideBannerRepository(apiService: ApiService): BannerRepository {
        return BannerRepositoryImpl(BannerRemoteSource(apiService))
    }

    @Provides
    fun provideCommentRepository(apiService: ApiService): CommentRepository {
        return CommentRepositoryImpl(CommentRemoteSource(apiService))
    }

    @Provides
    fun provideCartRepository(apiService: ApiService): CartRepository {
        return CartRepositoryImpl(CartRemoteSource(apiService))
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        apiService: ApiService, @ApplicationContext context: Context
    ): UserRepository {
        return UserRepositoryImpl(
            UserRemoteSource(apiService),
            UserLocalSource(context.getSharedPreferences("app", Application.MODE_PRIVATE))
        )
    }

    @Provides
    fun provideOrderRepository(apiService: ApiService): OrderRepository {
        return OrderRepositoryImpl(OrderRemoteSource(apiService))
    }

    @Provides
    fun provideCategoryRepository(apiService: ApiService): CategoryRepository {
        return CategoryRepositoryImpl(CategoryRemoteSource(apiService))
    }


}