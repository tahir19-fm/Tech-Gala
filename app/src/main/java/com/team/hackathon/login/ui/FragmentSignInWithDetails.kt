package com.team.hackathon.login.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.dietTracker.login.ui.FragmentLoginPhoneNumber
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.team.hackathon.R
import com.team.hackathon.databinding.FragmentLoginPhoneNumberBinding
import com.team.hackathon.databinding.FragmentSignInWithDetailsBinding
import com.team.hackathon.login.data.studentDetails
import com.team.hackathon.login.util.LoginViewModel


class FragmentSignInWithDetails : Fragment() {
    private val binding by lazy { FragmentSignInWithDetailsBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by activityViewModels()
    val signInId =binding.idEditText.text.toString()
    val signInPhone = binding.idPhoneNumberBox.text.toString()
    val mAuth = FirebaseAuth.getInstance()
    val firebaseFireStore = FirebaseFirestore.getInstance()
    companion object {
        fun getInstance() = FragmentSignInWithDetails()
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

binding.btnVerifyOTP.setOnClickListener{
    studentExists()
}
    }

    private fun studentExists(){
        val docRef = firebaseFireStore.collection("details").document("id")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                  Toast.makeText(requireContext(),"hehehe",Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(),"hahaahhah",Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }


    }
}
