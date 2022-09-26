package com.team.hackathon.login.ui

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
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
import com.team.hackathon.databinding.FragmentLoginOtpBinding
import com.team.hackathon.login.util.LoginViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.team.hackathon.home.ui.HomeActivity
import com.team.hackathon.login.data.studentDetails
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.concurrent.TimeUnit


class FragmentLoginOtp : Fragment() {
private val dataCollection = Firebase.firestore.collection("details")
    private val binding by lazy { FragmentLoginOtpBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by activityViewModels()
    private var codeBySystem: String = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken


    companion object {
        fun getInstance() = FragmentLoginOtp()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setobservers()
        FirebaseApp.initializeApp(requireContext())
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            SafetyNetAppCheckProviderFactory.getInstance()
        )
        // forcefully opens the keyboard and moves the focus from one child to another in the pinView
        binding.pinView.requestFocus()
        InputMethodManager.SHOW_FORCED
        InputMethodManager.HIDE_IMPLICIT_ONLY


        binding.ivBackButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        if (binding.pinView.text.toString().length == 6) {
            binding.btnVerifyOTP.visibility = View.VISIBLE
        }

        binding.pinView.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                val ch = binding.pinView.text.toString().trim()
                if (ch.length != 6) {
                    binding.btnVerifyOTP.isEnabled = true
                    binding.btnVerifyOTP.visibility = View.INVISIBLE
                    binding.btnCover.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val ch = binding.pinView.text.toString().trim()
                if (ch.length == 6) {
                    binding.btnVerifyOTP.isEnabled = true
                    binding.btnVerifyOTP.visibility = View.VISIBLE
                    binding.btnCover.visibility = View.INVISIBLE
                }

            }
        })
        object : CountDownTimer(15000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {

                object : CountDownTimer(70000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        binding.otpResendBox.setText("Resend otp in : " + millisUntilFinished / 1000 + " Seconds")
                    }

                    override fun onFinish() {
                        //  binding.otpResendBox.setText("done!")
                        binding.otpResendBox.visibility = View.INVISIBLE
                        binding.otpLinkBox.visibility = View.VISIBLE

                    }
                }.start()

            }
        }.start()

        binding.otpLinkBox.setOnClickListener {

            sendOtp()

            Toast.makeText(context, "hyperlink", Toast.LENGTH_SHORT).show()
            binding.otpLinkBox.visibility = View.INVISIBLE
            binding.otpResendBox.visibility = View.VISIBLE
            object : CountDownTimer((60000).toLong(), 1000) {

                override fun onTick(millisUntilFinished: Long) {
                    binding.otpResendBox.setText("Resend otp in : " + millisUntilFinished / 1000 + " Seconds")
                }

                override fun onFinish() {
                    //  binding.otpResendBox.setText("done!")
                    binding.otpResendBox.visibility = View.INVISIBLE
                    binding.otpLinkBox.visibility = View.VISIBLE

                }
            }.start()

        }

        sendOtp()

        binding.btnVerifyOTP.setOnClickListener {

            val code = binding.pinView.text.toString()
            if (code.length == 6) {
                binding.btnVerifyOTP.isEnabled = true
                binding.progressBarVerify.visibility = View.VISIBLE
                binding.btnVerifyOTP.visibility = View.INVISIBLE
                verifyCode(code)
            } else if (code.length < 6) {
                binding.btnVerifyOTP.visibility = View.VISIBLE;
                binding.progressBarVerify.visibility = View.INVISIBLE
                Toast.makeText(context, "pin error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private fun setobservers() {
        viewModel.phoneNumber.observe(this.requireActivity()) {
            binding.otpSubText.text = "A 6 digit code has been sent to $it"
        }
        viewModel.verification.observe(this.requireActivity()) {
            codeBySystem = it.toString()
        }
    }





    val otpCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // val otp = credential.smsCode
            Log.d(TAG, "onVerificationCompleted:$credential")
         //-------------------   createUser()
            // new implementation
            val code = credential.smsCode
            if (code != null) {
                binding.pinView.setText(code)
                verifyCode(code)

                // this will work in automatic way but what if user enterd number that is't in his phone
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.i("error", "onVerificationFailed: " + e.message)
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            codeBySystem = verificationId
            //Assume phone number will never be empty on this screen.
            viewModel.setphoneNumber(viewModel.phoneNumber.value ?: "")
            viewModel.setVerificationId(verificationId)
            resendToken = token
        }
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(codeBySystem, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // move activity && built in flags ::
                  checkUserExists()
                    //Delay after login to give firebase some time to refresh auth token.


//


                } else {
                    binding.btnVerifyOTP.visibility = View.VISIBLE;
                    binding.progressBarVerify.visibility = View.INVISIBLE
                    Log.d(TAG, "get failed with ", task.exception)
                    Toast.makeText(context, "Fail", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun checkUserExists() {
        viewModel.userExists.observe(requireActivity()){
            when(it){
                true->{
                    moveTOHome()
                }

                else -> {
                    uploadData()
                }
            }
        }
    }

    private fun sendOtp() {
        // send verification code to user
        val optionsBuilder = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(viewModel.phoneNumber.value ?: "")       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(otpCallback)
        if (this::resendToken.isInitialized) {
            optionsBuilder.setForceResendingToken(resendToken)
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }
    private fun saveStudentDetails(StudentDetail: studentDetails) =CoroutineScope(Dispatchers.IO).launch {

        try {
            Firebase.firestore.collection("users").document(viewModel.phoneNumber.value.toString()).set(StudentDetail)
            withContext(Dispatchers.Main){
                moveTOHome()
                Toast.makeText(requireContext(),"data Uploaded",Toast.LENGTH_LONG).show()
            }
        }
        catch (e:Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun uploadData(){
//        val id = viewModel.institute_data.value.toString()
//        val phoneNumber = viewModel.institue_phoneNumber.value.toString()

    val data=viewModel.studentDetailedInfo.value as studentDetails
        saveStudentDetails(data)
    }
    private fun moveTOHome(){
        val i = Intent(requireActivity(), HomeActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(i)
        requireActivity().finish()
    }
}

