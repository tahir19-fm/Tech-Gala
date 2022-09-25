package com.team.hackathon.home.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.team.hackathon.R
import com.team.hackathon.baseActivity.BaseActivity
import com.team.hackathon.databinding.ActivityBaseBinding
import com.team.hackathon.databinding.ActivityHomeBinding
import com.team.hackathon.home.util.HomeViewModel

class HomeActivity : AppCompatActivity() {
    private val binding by lazy{ActivityHomeBinding.inflate(layoutInflater)}
    private val viewModel:HomeViewModel by viewModels()
    companion object{
        const val OPEN_FRAGMENT_EVENT_LIST=1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.setUserState(1)
        setupViews()
        setupObservers()


    }

    private fun setupViews() {
        binding.bottomNavigationView.setOnItemSelectedListener {item->
            when(item.itemId){
                R.id.users -> {Toast.makeText(this,"user",Toast.LENGTH_LONG).show()}
                R.id.requests ->{}
            }
            true
        }
    }

    private fun setupObservers() {
        viewModel.userState.observe(this){
            when(it){
                OPEN_FRAGMENT_EVENT_LIST ->{
                    openFragmentEventList()
                }
            }
        }
    }

    private fun openFragmentEventList() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction= fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.homeActivityFragment, FragmentEventList()).commit()


    }
}