package com.team.hackathon.eventregisteration.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.razorpay.PaymentResultListener
import com.team.hackathon.R
import com.team.hackathon.baseActivity.BaseActivity
import com.team.hackathon.databinding.ActivityEventRegistrationBinding
import com.team.hackathon.eventregisteration.util.EventRegistrationViewModel
import com.team.hackathon.home.data.EventDataModel
import com.team.hackathon.home.ui.FragmentEventRegistered

class EventRegistrationActivity : AppCompatActivity() , PaymentResultListener {
    private val binding by lazy { ActivityEventRegistrationBinding.inflate(layoutInflater) }
    private val viewModel:EventRegistrationViewModel by viewModels()
    private val db=FirebaseFirestore.getInstance()

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
        getDataFromFireBase()
    }

    private fun getDataFromFireBase() {
        val docRef = db.collection("events").document(viewModel.userId.value.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                Log.d("TAG", "readFromFirebaseData: ${document}")
                if(document.exists()){

                     val res=  EventDataModel(
                            document.getString("image").toString(),
                            document.getString("heading").toString(),
                            document.getString("totalRegister").toString()+" Registered",
                            "last date : "+document.getString("lastDate").toString(),
                            document.getString("teamType").toString(),
                            "Rupees: "+document.getString("entryfee").toString(),
                            document.id )
                    viewModel.fetchUserData(res)
                }else{
                    Log.d("Data", "No such document")
                }
            }.addOnFailureListener{ exception ->
                Log.d("Data", "get failed with ", exception)
            }
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
        viewModel.setUserState(EVENT_DETAILS_FRAGMENT)
    }

    private fun registerUser() {
        val user = viewModel.userDataEvents.value as EventDataModel
    val  id=FirebaseAuth.getInstance().currentUser!!.phoneNumber.toString()
        db.collection("eventsregistered").document(id).collection("events").document(viewModel.userId.value.toString())
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
       Toast.makeText(this,"Payment Unsuccessful",Toast.LENGTH_LONG).show()
    }
}