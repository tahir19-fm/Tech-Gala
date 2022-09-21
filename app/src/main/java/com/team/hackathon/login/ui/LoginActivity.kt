package com.team.hackathon.login.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.team.hackathon.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    companion object{
        ///
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}