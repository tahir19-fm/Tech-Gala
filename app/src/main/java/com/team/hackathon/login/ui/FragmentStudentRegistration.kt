package com.team.hackathon.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dietTracker.login.ui.FragmentLoginOtp
import com.team.hackathon.LoginActivity
import com.team.hackathon.R
import com.team.hackathon.databinding.FragmentLoginPhoneNumberBinding
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
        binding.btnToRegisterUser.setOnClickListener{
            viewModel.setLoginState(LoginActivity.LOGIN_STATE_ENTER_OTP)
            if (binding.idEditText!=null){
                viewModel.setInstituteID(binding.idEditText.toString())
            }
            if (binding.phoneNumberEditText!=null){
                viewModel.setInstituePhoneNumber("+91"+binding.phoneNumberEditText.toString())
            }
        }

    }


}