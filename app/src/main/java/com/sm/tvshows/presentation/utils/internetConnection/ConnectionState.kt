package com.sm.tvshows.presentation.utils.internetConnection


sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}