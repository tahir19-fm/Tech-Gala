package com.team.hackathon.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dietTracker.login.ui.FragmentLoginPhoneNumber
import com.google.firebase.auth.FirebaseAuth
import com.team.hackathon.R
import com.team.hackathon.databinding.FragmentLoginPhoneNumberBinding
import com.team.hackathon.databinding.FragmentSignInWithDetailsBinding
import com.team.hackathon.login.util.LoginViewModel


class FragmentSignInWithDetails : Fragment() {
    private val binding by lazy { FragmentSignInWithDetailsBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by activityViewModels()

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
    }
}