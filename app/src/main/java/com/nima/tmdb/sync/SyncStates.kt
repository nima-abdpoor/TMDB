package com.nima.tmdb.sync

import com.nima.tmdb.login.state.State

sealed class SyncStates : State(){
    data class Failed(val statusCode : Int , val statusMessage : String) : SyncStates()
    data class TimeOutError(val message : String) : SyncStates()
    data class Success(val requestToken : String) : SyncStates()
}