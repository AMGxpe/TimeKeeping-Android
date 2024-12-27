package com.axpe.timekeeping.di

import com.axpe.timekeeping.BuildConfig
import com.axpe.timekeeping.core.TimeKeepingDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesJson() = Json { ignoreUnknownKeys = true }

    @Provides
    fun providesRetrofit(networkJson: Json) = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(
            networkJson.asConverterFactory(MediaType.parse("application/json; charset=UTF8")!!)
        )
        .build()

    @Provides
    fun providesTimeKeepingDataSource(retrofit: Retrofit) =
        retrofit.create(TimeKeepingDataSource::class.java)

}