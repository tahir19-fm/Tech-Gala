package com.team.hackathon.home.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    private val _userState = MutableLiveData<Int>()
    val userState : LiveData<Int>
        get() = _userState
    fun setUserState(state:Int){
        _userState.value=state
    }

}