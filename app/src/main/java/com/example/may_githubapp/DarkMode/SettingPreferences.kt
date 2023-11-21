package com.example.may_githubapp.DarkMode

//import androidx.datastore.core.DataStore
//import androidx.datastore.preferences.core.Preferences
//import androidx.datastore.preferences.core.booleanPreferencesKey
//import androidx.datastore.preferences.core.edit
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map

//class SettingPreferences private constructor(
//    private val dataStore: DataStore<Preferences>,
//    private val sharedPreferences: SharedPreferences
//) {
//    private val THEME_KEY = "theme_setting"
//
//    // Use a backing field to store the current theme setting
//    private var currentThemeSetting: Boolean = false
//
//    fun getThemeSetting(): Flow<Boolean> {
//        return dataStore.data.map { preferences ->
//            preferences[booleanPreferencesKey(THEME_KEY)] ?: currentThemeSetting
//        }
//    }
//
//    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
//        // Save the theme setting to the data store
//        dataStore.edit { preferences ->
//            preferences[booleanPreferencesKey(THEME_KEY)] = isDarkModeActive
//        }
//        // Save the theme setting to SharedPreferences
//        sharedPreferences.edit().putBoolean(THEME_KEY, isDarkModeActive).apply()
//        // Update the current theme setting
//        currentThemeSetting = isDarkModeActive
//
//        // Set the theme immediately after saving the preference
//        if (isDarkModeActive) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
//    }
//
//    companion object {
//        @Volatile
//        private var INSTANCE: SettingPreferences? = null
//
//        fun getInstance(
//            dataStore: DataStore<Preferences>,
//            sharedPreferences: SharedPreferences
//        ): SettingPreferences {
//            return INSTANCE ?: synchronized(this) {
//                val instance = SettingPreferences(dataStore, sharedPreferences)
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun themeSetting(): Flow<Boolean> {
        return dataStore.data.map { pref -> pref[THEME_KEY] ?: false }
    }

    suspend fun saveSetting (darkModeActivated: Boolean){
        dataStore.edit { pref -> pref[THEME_KEY] = darkModeActivated }
    }

    companion object{
        @Volatile
        private var INSTANCE: SettingPreferences?= null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
