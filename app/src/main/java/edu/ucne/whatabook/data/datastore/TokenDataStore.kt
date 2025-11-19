package edu.ucne.whatabook.data.datastore


import android.content.Context

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context.dataStore by preferencesDataStore("app_prefs")


class TokenDataStore @Inject constructor(private val context: Context) {
    companion object {
        val KEY_TOKEN = stringPreferencesKey("auth_token")
        val KEY_USERID = stringPreferencesKey("user_id")
        val KEY_TOKEN_TIME = stringPreferencesKey("token_time")
    }


    val token: Flow<String?> = context.dataStore.data.map { prefs -> prefs[KEY_TOKEN] }
    val userId: Flow<String?> = context.dataStore.data.map { prefs -> prefs[KEY_USERID] }


    suspend fun saveToken(tokenValue: String, userIdValue: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_TOKEN] = tokenValue
            prefs[KEY_USERID] = userIdValue
            prefs[KEY_TOKEN_TIME] = System.currentTimeMillis().toString()
        }
    }


    suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_TOKEN)
            prefs.remove(KEY_USERID)
            prefs.remove(KEY_TOKEN_TIME)
        }
    }
}