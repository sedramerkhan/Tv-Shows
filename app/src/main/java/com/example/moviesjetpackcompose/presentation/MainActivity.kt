package com.example.moviesjetpackcompose.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesjetpackcompose.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


}

