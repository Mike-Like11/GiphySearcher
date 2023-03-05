package com.ivantsov.giphysearcher.data.remote

import com.ivantsov.giphysearcher.utils.Constants
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class RetrofitServiceBuilder {
    companion object {
        private fun provideOfflineCacheInterceptor(): Interceptor {
            return Interceptor { chain ->
                try {
                    return@Interceptor chain.proceed(chain.request())
                } catch (e: Exception) {
                    val cacheControl = CacheControl.Builder()
                        .onlyIfCached()
                        .maxStale(1, TimeUnit.DAYS)
                        .build()
                    val offlineRequest: Request = chain.request().newBuilder()
                        .cacheControl(cacheControl)
                        .removeHeader("Pragma")
                        .build()
                    return@Interceptor chain.proceed(offlineRequest)
                }
            }
        }

        fun getService(cacheDir: File): ApiService = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .cache(Cache(File(cacheDir, "offlineCache"), 10 * 1024 * 1024))
                    .addInterceptor(provideOfflineCacheInterceptor())
                    .build()
            )
            .build()
            .create(ApiService::class.java)
    }
}