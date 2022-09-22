package com.example.moviesjetpackcompose.presentation.utils.InternetConnection


sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}