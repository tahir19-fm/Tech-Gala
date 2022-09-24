package com.team.hackathon.UserProfile.ui

import com.team.hackathon.BaseActivity.BaseActivity



import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.team.hackathon.R
import com.team.hackathon.SplashActivity
import com.team.hackathon.UserProfile.util.UserProfileViewModel
import com.team.hackathon.databinding.ActivityUserProfileBinding
import com.team.hackathon.home.ui.FragmentEventList


class UserProfileActivity : BaseActivity() {
    private val binding by lazy { ActivityUserProfileBinding.inflate(layoutInflater) }
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
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainUserProfile, FragmentEventList()).commit()
    }

    private fun splashscreen() {
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
    }



}

