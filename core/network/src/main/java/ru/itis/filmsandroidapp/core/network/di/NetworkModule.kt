package ru.itis.filmsandroidapp.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.itis.filmsandroidapp.core.network.FilmsApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val FILMS_BASE_URL = "https://api.kinopoisk.dev/v1.4/"
    @Provides
    fun okHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val modifiedUrl = chain.request().url.newBuilder()
                    .build()

                val request = chain.request().newBuilder()
                    .url(modifiedUrl)
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("X-API-KEY", "HRVC9B8-1994S91-G8PF3KF-RW48YY2")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        if (BuildConfig.DEBUG) {
            client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun provideFilmsApi(okHttpClient: OkHttpClient): FilmsApiService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(FILMS_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitBuilder.create(FilmsApiService::class.java)
    }
}