package com.team.hackathon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.team.hackathon.databinding.ActivityMainBinding
import com.team.hackathon.home.ui.HomeActivity
import com.team.hackathon.home.util.HomeViewModel

class SplashActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val currUser = FirebaseAuth.getInstance().currentUser
        if (currUser == null) {
          //login
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        } else {

        }
    }
}