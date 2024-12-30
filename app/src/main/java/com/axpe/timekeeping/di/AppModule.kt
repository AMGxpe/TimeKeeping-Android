package com.axpe.timekeeping.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.axpe.timekeeping.BuildConfig
import com.axpe.timekeeping.core.HourControlApi
import com.axpe.timekeeping.core.ReportingDataSource
import com.axpe.timekeeping.core.ReportingApi
import com.axpe.timekeeping.core.TimeKeepingDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    @HourControlApi
    fun providesHourControlRetrofit(networkJson: Json): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(
            networkJson.asConverterFactory(MediaType.parse("application/json; charset=UTF8")!!)
        )
        .build()

    @Provides
    @Singleton
    @ReportingApi
    fun providesReportingRetrofit(networkJson: Json): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.REPORTING_API_URL)
        .addConverterFactory(
            networkJson.asConverterFactory(MediaType.parse("application/json; charset=UTF8")!!)
        )
        .build()

    @Provides
    @Singleton
    fun providesTimeKeepingDataSource(@HourControlApi retrofit: Retrofit): TimeKeepingDataSource =
        retrofit.create(TimeKeepingDataSource::class.java)

    @Provides
    @Singleton
    fun providesReportingDataSource(@ReportingApi retrofit: Retrofit): ReportingDataSource =
        retrofit.create(ReportingDataSource::class.java)


    @Provides
    @Singleton
    fun providesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.dataStoreFile("user.preferences_pb") }
        )
}