package com.axpe.timekeeping.core

import android.util.Log
import com.axpe.timekeeping.BuildConfig
import com.axpe.timekeeping.core.model.NetworkLogin
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
class TimeKeepingRepository {
    private val networkJson = Json { ignoreUnknownKeys = true }
    private val timeKeepingDataSource: TimeKeepingDataSource =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(
                networkJson.asConverterFactory(MediaType.parse("application/json; charset=UTF8")!!)
            )
            .build()
            .create(TimeKeepingDataSource::class.java)

    suspend fun doLogin(username: String, password: String): NetworkLogin {
        val shieldedPassword = Base64.encode("$username//:$password".toByteArray())
        return timeKeepingDataSource.doLogin("Basic $shieldedPassword")
    }
}