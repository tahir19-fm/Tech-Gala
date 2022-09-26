package com.team.hackathon.eventregisteration.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.razorpay.PaymentResultListener
import com.team.hackathon.R
import com.team.hackathon.baseActivity.BaseActivity
import com.team.hackathon.databinding.ActivityBaseBinding
import com.team.hackathon.databinding.ActivityEventRegistrationBinding
import com.team.hackathon.eventregisteration.util.EventRegistrationViewModel
import com.team.hackathon.home.ui.FragmentEventRegistered

class EventRegistrationActivity : AppCompatActivity() , PaymentResultListener {
    private val binding by lazy { ActivityEventRegistrationBinding.inflate(layoutInflater) }
    private val viewModel:EventRegistrationViewModel by viewModels()

    companion object{
        const val EVENT_DETAILS_FRAGMENT=1
        const val EVENT_REGISTER_FRAGMENT=2
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.setUserState(1)
        getId()
        setupObservers()
    }

    private fun getId() {
        val id=intent.getStringExtra("id")
        if (id != null) {
            viewModel.setUserId(id)
        }
        Log.d("gay", "getId: ${id}")
    }

    private fun setupObservers() {
        viewModel.userState.observe(this){
            when(it){
                EVENT_DETAILS_FRAGMENT->{
                    openEventDetailFragment()
                }
                EVENT_REGISTER_FRAGMENT->{
                    openEventRegisterFragment()
                }
            }
        }
    }

    private fun openEventRegisterFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,RegisterForEventFragment()).commit()
    }

    private fun openEventDetailFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,EventDetailsFragment()).commit()
    }

    override fun onPaymentSuccess(p0: String?) {
        registerUser()
    }

    private fun registerUser() {

    }

    override fun onPaymentError(p0: Int, p1: String?) {
       Toast.makeText(this,"Payment Unsuccessful",Toast.LENGTH_LONG).show()
    }
}