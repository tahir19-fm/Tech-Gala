package com.team.hackathon.login.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.team.hackathon.databinding.FragmentUserValidationBinding

import com.team.hackathon.LoginActivity
import com.team.hackathon.login.util.LoginViewModel

class FragmentUserValidation : Fragment() {

    private val binding by lazy { FragmentUserValidationBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by activityViewModels()

    companion object {
        fun getInstance() = FragmentUserValidation()
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
        setHead()
        binding.ivBackButton.setOnClickListener {

            viewModel.setLoginState(LoginActivity.LOGIN_STATE_ENTER_NUMBER)
        }

        binding.btnToNumber.setOnClickListener {
            viewModel.setphoneNumber("")
            viewModel.setLoginState(LoginActivity.LOGIN_STATE_ENTER_NUMBER)
        }
        binding.btnToRegistration.setOnClickListener {
            viewModel.setLoginState(LoginActivity.LOGIN_STATE_REGISTER_USER)
        }


    }

    private fun setHead() {
        binding.llNavbar.image.visibility = View.INVISIBLE
    }
}