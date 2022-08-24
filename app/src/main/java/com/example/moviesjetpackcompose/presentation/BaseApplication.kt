package com.example.moviesjetpackcompose.presentation

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application(){

    // should be saved in data store
    var isDark by mutableStateOf(false)

    fun toggleLightTheme(){
        isDark= !isDark
    }

}