package com.team.hackathon.login.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.team.hackathon.LoginActivity
import com.team.hackathon.R
import com.team.hackathon.databinding.FragmentStudentRegistrationBinding
import com.team.hackathon.login.data.studentDetails
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
        setUpAllDropdowns()
        binding.arrowToBack.setOnClickListener {
            viewModel.setLoginState(LoginActivity.LOGIN_STATE_USER_VALIDATION)
        }
        binding.idEditText.setText(viewModel.institute_data.value)
        binding.phoneNumberEditText.setText(viewModel.phoneNumberRegistration.value)
        Log.d("tag", viewModel.phoneNumber.value.toString())
        binding.btnToRegisterUser.setOnClickListener {
            viewModel.setLoginState(LoginActivity.LOGIN_STATE_ENTER_OTP)
            detailsSave()

        }

    }

    private fun setUpAllDropdowns() {
        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapterGender = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, gender) }
        binding.actSelectGender.setAdapter(arrayAdapterGender)

        val branch = resources.getStringArray(R.array.branch)
        val arrayAdapterBranch = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, branch) }
        binding.actSelectBranch.setAdapter(arrayAdapterBranch)


        val interest = resources.getStringArray(R.array.interest)
        val arrayAdapterInterest =
            context?.let { ArrayAdapter(it, R.layout.dropdown_layout, interest) }
        binding.actSelectInterests.setAdapter(arrayAdapterInterest)

    }

    private fun detailsSave() {
        viewModel.setStudentDetailedInfo(

            studentDetails(
                binding.idEditText.text.toString(),
                "+91" + binding.phoneNumberEditText.text.toString(),
                binding.nameEditText.text.toString(),
                binding.instituteEditText.text.toString(),
                binding.actSelectBranch.text.toString(),
                binding.actSelectGender.text.toString(),
                binding.actSelectInterests.text.toString()
            )
        )
    }

}
