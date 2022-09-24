package com.team.hackathon.login.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.hackathon.login.data.UserRegistrationDto
import com.team.hackathon.login.data.studentDetails
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
    private val _loginState = MutableLiveData<Int>()
    val loginState: LiveData<Int>
        get() = _loginState

    fun setLoginState(state: Int) {
        _loginState.value = state
    }

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    fun setphoneNumber(number: String) {
        _phoneNumber.value = number

    }

    private val _phoneNumberRegister = MutableLiveData<String>()
    val phoneNumberRegistration: LiveData<String>
        get() = _phoneNumberRegister

    fun setPhoneNumberRegistration(number: String) {
        _phoneNumberRegister.value = number;
    }

    private val _verificationID = MutableLiveData<String>()
    val verification: LiveData<String>
        get() = _verificationID

    fun setVerificationId(number: String) {
        _verificationID.value = number;
    }

    private val _userData = MutableLiveData<UserRegistrationDto>()
    val userData: LiveData<UserRegistrationDto>
        get() = _userData

    fun saveUserDataOnLocal(data: UserRegistrationDto) {
        _userData.value = data
    }
    fun setInstituteID(id:String){
        _institute_id.value=id
    }

    private val _institute_id= MutableLiveData<String>()
    val institute_data: LiveData<String>
    get() =_institute_id

    fun setInstituePhoneNumber(institutePhone : String){
        _institue_phoneNumber.value = institutePhone
    }
    private val _institue_phoneNumber = MutableLiveData<String>()
    val institue_phoneNumber : LiveData<String>
    get() = _institue_phoneNumber



}