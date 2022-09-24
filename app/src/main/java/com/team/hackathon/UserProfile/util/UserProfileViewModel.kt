package com.team.hackathon.UserProfile.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.hackathon.UserProfile.data.DataClassForEdit
import com.team.hackathon.UserProfile.data.UserDataForProfile
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

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

    private val _userDataForEdit = MutableLiveData<UserDataForProfile>()
    val userDataForEdit : LiveData<UserDataForProfile>
    get() = _userDataForEdit

    fun setUserDataForEdit(data:UserDataForProfile){
        _userDataForEdit.value = data
    }

    private val _newUserData = MutableLiveData<DataClassForEdit>()
    val newUserData : LiveData<DataClassForEdit>
    get() = _newUserData

    fun setNewUserData(data:DataClassForEdit){
        _newUserData.value = data
    }

    private val _patchUserData = MutableLiveData<DataClassForEdit>()
    val patchUserData : MutableLiveData<DataClassForEdit>
    get() = _patchUserData





}