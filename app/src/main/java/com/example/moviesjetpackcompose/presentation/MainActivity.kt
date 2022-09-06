package com.example.moviesjetpackcompose.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import com.example.moviesjetpackcompose.presentation.theme.AppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


//https://androidrepo.com/repo/meshramaravind-MoviesApp-Android
@SuppressLint("RestrictedApi")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var application: BaseApplication

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDefaultLanguage("US")
        setContent {
            val isDark = application.isDark
            AppTheme(darkTheme = isDark) {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
    private fun setDefaultLanguage(lang: String?) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        this.resources.updateConfiguration(config, this.resources.displayMetrics)
    }
}

