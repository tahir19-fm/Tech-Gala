package com.team.hackathon.UserProfile.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.team.hackathon.R
import com.team.hackathon.SplashActivity
import com.team.hackathon.UserProfile.util.UserProfileViewModel
import com.team.hackathon.databinding.ActivityUserProfileBinding
import com.team.hackathon.home.ui.FragmentEventList


class UserProfileActivity : AppCompatActivity() , PaymentResultListener {
    private val binding by lazy {ActivityUserProfileBinding.inflate(layoutInflater) }
    private val viewModel : UserProfileViewModel by viewModels()

    companion object{
        const val USER_PROFILE = 1
        const val USER_PROFILE_EDIT = 2
        const val DIET_ACTIVITY = 3
        const val SPLASH_SCREEN = 4
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.setUserProfileState(USER_PROFILE)
        setupObserver()

    }

    private fun setupObserver(){
        viewModel.userProfileState.observe(this){
            when(it){
                USER_PROFILE -> {
                    userProfile()
                }
                USER_PROFILE_EDIT -> {
                    userProfileEdit()
                }
                DIET_ACTIVITY ->{
                    dietActivity()
                }
                SPLASH_SCREEN ->{
                    splashscreen()
                }
            }
        }
    }


    private fun userProfile(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainUserProfile,FragmentUserProfile()).commit()
    }


    private fun userProfileEdit(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainUserProfile,FragmentUserProfileEdit()).commit()
    }

    private fun dietActivity() {
     splashscreen()
    }
    private fun splashscreen() {
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onPaymentSuccess(p0: String?) {
        Log.d("sucess","Success")
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d("not","NOT")
        //Toast.makeText(UserProfileActivity::class.java, "Payment Failed due to error : " +  Toast.LENGTH_SHORT).show();
    }


}


