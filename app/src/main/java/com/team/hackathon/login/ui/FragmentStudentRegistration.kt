package com.team.hackathon.login.ui

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.team.hackathon.LoginActivity
import com.team.hackathon.R
import com.team.hackathon.databinding.FragmentStudentRegistrationBinding
import com.team.hackathon.login.util.LoginViewModel


class FragmentStudentRegistration : Fragment() {
    private val binding by lazy { FragmentStudentRegistrationBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by activityViewModels()


    companion object {
        fun getInstance() = FragmentStudentRegistration()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.idEditText.setText(viewModel.institute_data.value)
        binding.phoneNumberEditText.setText(viewModel.phoneNumberRegistration.value)
        Log.d("tag",viewModel.phoneNumber.value.toString())
        binding.btnToRegisterUser.setOnClickListener{
            viewModel.setLoginState(LoginActivity.LOGIN_STATE_ENTER_OTP)
            if (binding.idEditText!=null){
                viewModel.setInstituteID(binding.idEditText.text.toString())
            }
            if (binding.phoneNumberEditText!=null){
                viewModel.setInstituePhoneNumber("+91"+binding.phoneNumberEditText.text.toString())

            }
        }

    }


}