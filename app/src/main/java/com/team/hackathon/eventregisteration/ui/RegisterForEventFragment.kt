package com.team.hackathon.eventregisteration.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dietTracker.login.ui.FragmentLoginOtp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.team.hackathon.R
import com.team.hackathon.databinding.FragmentRegisterForEventBinding
import com.team.hackathon.eventregisteration.util.EventRegistrationViewModel


class RegisterForEventFragment : Fragment() {
    private val binding by lazy { FragmentRegisterForEventBinding.inflate(layoutInflater) }
    private val viewModel: EventRegistrationViewModel by activityViewModels()
    private val db = Firebase.firestore

    companion object {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        readFromFirebaseData()
//        setupObserver()
//        setupViews()

    }

    private fun setupViews() {
        TODO("Not yet implemented")
    }

    private fun setupObserver() {
        TODO("Not yet implemented")
    }

    private fun readFromFirebaseData() {
        TODO("Not yet implemented")
    }

}