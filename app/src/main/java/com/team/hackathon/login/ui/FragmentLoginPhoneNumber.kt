package com.dietTracker.login.ui

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.team.hackathon.login.util.LoginViewModel
import com.team.hackathon.databinding.FragmentLoginPhoneNumberBinding
import com.team.hackathon.login.util.isValidPhoneNumber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.team.hackathon.LoginActivity
import com.team.hackathon.login.util.isValidId
import kotlinx.coroutines.delay
import java.util.regex.Pattern


class FragmentLoginPhoneNumber : Fragment() {


    private val binding by lazy { FragmentLoginPhoneNumberBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by activityViewModels()
    private val auth = FirebaseAuth.getInstance()

    companion object {
        fun getInstance() = FragmentLoginPhoneNumber()
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

        binding.countryCodePicker.registerCarrierNumberEditText(binding.phoneNumberBox)
        binding.phoneNumberBox.setText("8789355466")
        binding.idEditText.setText("assam@gm.com")


        val ccp = binding.countryCodePicker
        ccp.registerCarrierNumberEditText(binding.phoneNumberBox)

        binding.btnToSendOtp.setOnClickListener {
            onSendOTPClicked()
        }

    }

    private fun onSendOTPClicked(){
        val ccp = binding.countryCodePicker
        viewModel.setInstituteID(binding.idEditText.text.toString())
        if (isValidPhoneNumber(ccp.fullNumber)  && isValidId(binding.idEditText.text.toString())) {
            // for registration number
            viewModel.setPhoneNumberRegistration(binding.phoneNumberBox.text.toString())
            viewModel.setInstituteID(binding.idEditText.text.toString())
            Log.d("tahir",viewModel.phoneNumber.value.toString())
            viewModel.setphoneNumber(binding.countryCodePicker.fullNumberWithPlus)
            studentExists()
            hideKeyboard()
        }
        else if(!isValidId(binding.idEditText.text.toString())){
            Toast.makeText(context,"invalid id",Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context,"invalid Number",Toast.LENGTH_SHORT).show()
        }

    }

    private fun hideKeyboard() {
        val inputManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun studentExists(){
        binding.frag1.visibility=View.INVISIBLE
        binding.progressBar.visibility=View.VISIBLE
        val docRef = FirebaseFirestore.getInstance().collection("users").document(viewModel.phoneNumber.value.toString())
        docRef.get()
            .addOnSuccessListener{ document->
                if (document.exists()){

                    viewModel.setLoginState(LoginActivity.LOGIN_STATE_ENTER_OTP)
                }
                else
                {
                    viewModel.setLoginState(LoginActivity.LOGIN_STATE_USER_VALIDATION)

                }

            }
            .addOnFailureListener{exception->
                Log.d(ContentValues.TAG,"get failed with",exception)
            }
    }

}


