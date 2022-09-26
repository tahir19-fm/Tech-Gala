package com.team.hackathon.login.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.team.hackathon.R
import com.team.hackathon.databinding.FragmentStudentRegistrationBinding
import com.team.hackathon.databinding.FragmentUploadImageBinding
import com.team.hackathon.login.util.LoginViewModel


class FragmentUploadImage : Fragment() {
    private val binding by lazy { FragmentUploadImageBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by activityViewModels()
    private val fireBaseStorage = FirebaseStorage.getInstance()



    companion object {
        fun getInstance() = FragmentUploadImage()
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