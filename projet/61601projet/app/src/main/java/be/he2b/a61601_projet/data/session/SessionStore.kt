package be.he2b.a61601_projet.data.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth_session")

/**
 * Persistent storage of the Supabase auth session.
 * Same singleton pattern as Repository: must be initialized once at app
 * startup via init(context).
 */
object SessionStore {

    private val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    private val KEY_USER_ID = stringPreferencesKey("user_id")

    private lateinit var appContext: Context

    fun init(context: Context) {
        if (::appContext.isInitialized) return
        appContext = context.applicationContext
    }

    val accessToken: Flow<String?>
        get() = appContext.dataStore.data.map { it[KEY_ACCESS_TOKEN] }

    suspend fun save(accessToken: String, refreshToken: String, userId: String) {
        appContext.dataStore.edit { prefs ->
            prefs[KEY_ACCESS_TOKEN] = accessToken
            prefs[KEY_REFRESH_TOKEN] = refreshToken
            prefs[KEY_USER_ID] = userId
        }
    }

    suspend fun clear() {
        appContext.dataStore.edit { it.clear() }
    }
}
