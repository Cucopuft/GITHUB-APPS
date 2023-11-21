package com.example.may_githubapp.DarkMode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DarkModeViewModel (private val pref: SettingPreferences) : ViewModel() {
    fun themeSetting(): LiveData<Boolean> {
        return pref.themeSetting().asLiveData()
    }

    fun saveSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            pref.saveSetting(isDarkMode)
        }
    }
}