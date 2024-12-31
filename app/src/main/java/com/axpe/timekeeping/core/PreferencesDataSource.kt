package com.axpe.timekeeping.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.axpe.timekeeping.core.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val usernameKey = stringPreferencesKey("username")
private val userIdKey = longPreferencesKey("userId")
private val loggedKey = booleanPreferencesKey("logged")

class PreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    fun <T> withUserId(block: suspend (userId: Long) -> T): Flow<T> =
        getDataStoreUser().map { user -> block(user.userId) }


    fun getDataStoreUser(): Flow<UserData> {
        return dataStore.data.map { user ->
            val username = user[usernameKey]
            val userId = user[userIdKey]
            val logged = user[loggedKey] ?: false
            if (username == null || userId == null) {
                return@map UserData.notLogged()
            }
            UserData(
                username = username,
                userId = userId,
                isLogged = logged
            )
        }
    }

    fun getDataStoreUserId(): Flow<Long> {
        return dataStore.data.map { user ->
            user[longPreferencesKey("userId")] ?: 0L
        }
    }

    suspend fun clearUserSession() {
        dataStore.edit { user ->
            user.clear()
        }
    }

    suspend fun setDataStoreUsername(username: String) {
        dataStore.edit { user ->
            user[usernameKey] = username
        }
    }

    suspend fun setDataStoreUserId(userId: Long) {
        dataStore.edit { user ->
            user[userIdKey] = userId
        }
    }

    suspend fun setLogged() {
        dataStore.edit { user ->
            user[loggedKey] = true
        }
    }

}