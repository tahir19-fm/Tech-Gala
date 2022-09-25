package com.team.hackathon.home.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team.hackathon.home.data.EventDataModel

class HomeViewModel: ViewModel() {
    private val _userState = MutableLiveData<Int>()
    val userState : LiveData<Int>
        get() = _userState
    fun setUserState(state:Int){
        _userState.value=state
    }
    private val _userDataEvents = MutableLiveData<ArrayList<EventDataModel>>()
    val userDataEvents: MutableLiveData<ArrayList<EventDataModel>>
        get() = _userDataEvents

    fun fetchUserData(data :ArrayList<EventDataModel>) {
       _userDataEvents.value=data
    }
    private val _userDataEventsRegistered = MutableLiveData<ArrayList<EventDataModel>>()
    val userDataEventsRegistered: MutableLiveData<ArrayList<EventDataModel>>
        get() = _userDataEventsRegistered

    fun fetchUserDataRegistered(data :ArrayList<EventDataModel>) {
       _userDataEventsRegistered.value=data
    }

}