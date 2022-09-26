package com.team.hackathon.eventregisteration.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team.hackathon.eventregisteration.data.EventDetailsModalClass

class EventRegistrationViewModel:  ViewModel() {
    private val _userState = MutableLiveData<Int>()
    val userState : LiveData<Int>
        get() = _userState
    fun setUserState(state:Int){
        _userState.value=state
    }

    private val _userId = MutableLiveData<String>()
    val userId : LiveData<String>
        get() = _userId
    fun setUserId(state:String){
        _userId.value=state
    }

    private val _userDataEventsDetail = MutableLiveData<EventDetailsModalClass>()
    val userDataEventsDetail: MutableLiveData<EventDetailsModalClass>
        get() = _userDataEventsDetail

    fun fetchUserDataDetail(data :EventDetailsModalClass) {
        _userDataEventsDetail.value=data
    }

    private val _registerDone = MutableLiveData<Boolean>()
    val registerDone : LiveData<Boolean>
        get() = _registerDone
    fun setUserExists(state:Boolean){
        _registerDone.value=state
    }



}