package mor.aliakbar.tavaloodshop.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mor.aliakbar.tavaloodshop.model.api.ApiService
import mor.aliakbar.tavaloodshop.model.api.AppAuthenticator
import mor.aliakbar.tavaloodshop.model.dataclass.TokenContainer
import mor.aliakbar.tavaloodshop.utils.Variable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun createApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun createRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Variable.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { oldInterceptor ->

                val oldRequest = oldInterceptor.request()
                val newRequest = oldRequest.newBuilder()

                //pass access token if that not null
                if (!TokenContainer.token.isNullOrEmpty())
                    newRequest.addHeader("Authorization", "Bearer ${TokenContainer.token}")
                //set application/json header for all requests
                newRequest.addHeader("Accept", "application/json")
                //set the old request method and body to the new request
                newRequest.method(oldRequest.method, oldRequest.body)
                return@addInterceptor oldInterceptor.proceed(newRequest.build())
            }
            .addInterceptor(httpLoggingInterceptor)
            .authenticator(AppAuthenticator())
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }


}