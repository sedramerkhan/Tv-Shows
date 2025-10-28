package com.sm.tvshows.presentation.utils.InternetConnection


sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}