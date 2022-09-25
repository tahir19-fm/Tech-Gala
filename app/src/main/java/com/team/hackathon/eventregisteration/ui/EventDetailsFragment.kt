package com.team.hackathon.eventregisteration.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.dietTracker.login.ui.FragmentLoginOtp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.team.hackathon.R
import com.team.hackathon.databinding.FragmentEventDetailsBinding
import com.team.hackathon.databinding.FragmentEventRegisteredBinding
import com.team.hackathon.eventregisteration.data.EventDetailsModalClass
import com.team.hackathon.eventregisteration.util.EventRegistrationViewModel
import com.team.hackathon.home.data.EventDataModel
import com.team.hackathon.home.ui.FragmentEventRegistered
import com.team.hackathon.home.util.HomeViewModel

class EventDetailsFragment : Fragment() {
    private val binding by lazy { FragmentEventDetailsBinding.inflate(layoutInflater) }
    private val viewModel: EventRegistrationViewModel by activityViewModels()
    private val db = Firebase.firestore

    companion object {
        const val RS="Rs "
        fun getInstance() = FragmentLoginOtp()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readFromFirebaseData()
        setupObserver()
        setupViews()

    }

    private fun setupViews() {


    }

    private fun setupObserver() {
    viewModel.userDataEventsDetail.observe(this.requireActivity()){
        val data =it as EventDetailsModalClass
        if (data.image.isNotEmpty()) {
            Picasso.get().load(data.image).into(binding.EventImage)
        }
            binding.headingOf.text = data.heading
            binding.subHeadingOf.text = data.subHeading
            binding.adress.text = data.address
            binding.lastdateDeadline.text = data.lastDate
            if (data.entryfees != null) {
                binding.entryfee.text = RS + data.entryfees
            } else {
                binding.entryfee.text = "free"
            }
            binding.teamLength.text = data.teamType
            binding.location.text = data.location


    }
    }

    private fun readFromFirebaseData() {
        Log.d("ray", "readFromFirebaseData:${viewModel.userId.value.toString()} ")
        val docRef = db.collection("events").document(viewModel.userId.value.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                Log.d("gf", "readFromFirebaseData: ${document}")
                if(document.exists()){


                       val res= EventDetailsModalClass(
                            document.getString("address").toString(),
                            document.getString("entryfees").toString(),
                            document.getString("heading").toString(),
                            document.getString("image").toString(),
                            document.getString("lastDate").toString(),
                            document.getString("location").toString(),
                            document.getString("subHeading").toString(),
                            document.getString("teamType").toString(),
                            document.getString("totalRegister").toString()


                        )
                    viewModel.fetchUserDataDetail(res)
                    Log.d("press", "dsata${res.address}")
                }else{
                    Log.d("Data", "No such document")
                }
            }.addOnFailureListener{ exception ->
                Log.d("Data", "get failed with ", exception)
            }
    }
}