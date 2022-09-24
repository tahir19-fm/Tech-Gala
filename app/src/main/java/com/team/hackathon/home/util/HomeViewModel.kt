package com.team.hackathon.home.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.hackathon.home.data.EventDataModel
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _userState = MutableLiveData<Int>()
    val userState : LiveData<Int>
        get() = _userState
    fun setUserState(state:Int){
        _userState.value=state
    }
    private val _userData = MutableLiveData<EventDataModel>()
    val userData: MutableLiveData<EventDataModel>
        get() = _userData

    fun fetchUserData(data :EventDataModel) {
       _userData.value=data
    }

}