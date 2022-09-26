package com.team.hackathon.UserProfile.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team.hackathon.UserProfile.data.UserDataForProfile


class UserProfileViewModel : ViewModel() {

    private val _userData = MutableLiveData<UserDataForProfile>()
    val userData: MutableLiveData<UserDataForProfile>
    get() = _userData

    fun setUserData(data:UserDataForProfile){
        _userData.value = data
    }

    private val _userProfileState = MutableLiveData<Int>()
    val userProfileState : LiveData<Int>
    get() = _userProfileState

    fun setUserProfileState(state : Int){
        _userProfileState.value = state
    }

}