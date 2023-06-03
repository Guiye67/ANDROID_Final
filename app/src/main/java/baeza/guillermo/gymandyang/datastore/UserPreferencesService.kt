package baeza.guillermo.gymandyang.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import baeza.guillermo.gymandyang.ui.models.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserPreferenceService @Inject constructor(
    private val dataStore: DataStore<Preferences>
): IUserPreferences {
    override suspend fun addUser(key: String, value: String) {
        val preferenceKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    override suspend fun getUser(key: String): User? {
        return try {
            val preferenceKey = stringPreferencesKey(key)
            val preferences = dataStore.data.first()
            Gson().fromJson(preferences[preferenceKey], User::class.java)
        } catch (e: Exception) {
            Log.e("Gym", "error: ${e.message}")
            null
        }
    }
}