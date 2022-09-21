package com.team.hackathon.login.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModal  : ViewModel(){

    private val _userState = MutableLiveData<Int>()
    val userState : LiveData<Int>
        get() = _userState

    fun setUserState(state:Int){
        _userState.value=state
    }

}