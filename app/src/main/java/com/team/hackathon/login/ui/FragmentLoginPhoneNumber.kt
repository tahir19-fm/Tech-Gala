package com.dietTracker.login.ui

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.team.hackathon.LoginActivity
import com.team.hackathon.databinding.FragmentLoginPhoneNumberBinding
import com.team.hackathon.login.util.LoginViewModel
import com.team.hackathon.login.util.isValidId
import com.team.hackathon.login.util.isValidPhoneNumber

class FragmentLoginPhoneNumber : Fragment() {

    private val binding by lazy { FragmentLoginPhoneNumberBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by activityViewModels()
    private val auth = FirebaseAuth.getInstance()
    private val ccp = binding.countryCodePicker
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




        viewModel.phoneNumberRegistration.observe(viewLifecycleOwner) {
            binding.phoneNumberBox.setText(it)
        }

        binding.countryCodePicker.registerCarrierNumberEditText(binding.phoneNumberBox)
        val ccp = binding.countryCodePicker
        ccp.registerCarrierNumberEditText(binding.phoneNumberBox)

        binding.btnToSendOtp.setOnClickListener {
            onSendOTPClicked()
        }


//        Firebase.firestore.collection("users").document("cyc").set(studentDetails("hh","hh"))

    }




    private fun onSendOTPClicked(){
        val button = binding.btnToSendOtp
//        val buttonCover = binding.btncover
        if (isValidPhoneNumber(ccp.fullNumber) && isValidId(binding.idEditText.text.toString())) {
            // for registration number

//            button.visibility= View.VISIBLE
//            buttonCover.visibility =View.INVISIBLE
//            button.isEnabled
            viewModel.setPhoneNumberRegistration(binding.phoneNumberBox.text.toString())
            viewModel.setInstituteID(binding.idEditText.text.toString())
         //   viewModel.setLoginState(LoginActivity.LOGIN_STATE_REGISTER_USER)
            studentExists()
            viewModel.setphoneNumber(binding.countryCodePicker.fullNumberWithPlus)
            hideKeyboard()
        }

        else if(!isValidId(binding.idEditText.text.toString())){
            Toast.makeText(context,"ill mail",Toast.LENGTH_SHORT).show()

        }
        else {
            Toast.makeText(context, "Invalid Number", Toast.LENGTH_SHORT).show()
        }

    }

    private fun hideKeyboard() {
        val inputManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
    private fun studentExists() {
        Toast.makeText(context,viewModel.institute_data.value.toString(),Toast.LENGTH_SHORT).show()

        val docRef = FirebaseFirestore.getInstance().collection("users").document(viewModel.institute_data.value.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                 //   Log.d("TAG", "studentExists: ${document.exists()}")
                    viewModel.setLoginState(LoginActivity.LOGIN_STATE_ENTER_OTP)

                } else {
                  //  Toast.makeText(requireContext(), "", Toast.LENGTH_LONG).show()
                    viewModel.setLoginState(LoginActivity.LOGIN_STATE_REGISTER_USER)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }

    }


}


