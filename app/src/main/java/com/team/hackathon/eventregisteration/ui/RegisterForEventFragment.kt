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

        val firstTeamMemberEmail = binding.email.text
        val secondTeamMemberEmail = binding.email2.text
        val thirdTeamMemberEmail = binding.email3.text
        val fourthTeamMemberEmail = binding.email4.text

        binding.teamMemeberOne2.visibility = View.GONE
        binding.teamMemeberOne3.visibility = View.GONE
        binding.teamMemeberOne4.visibility = View.GONE


        binding.inviteButton1.setOnClickListener{
            binding.teamMemeberOne2.visibility = View.VISIBLE
            binding.inviteButton1.visibility = View.GONE
        }

        binding.inviteButton2.setOnClickListener{
            binding.teamMemeberOne3.visibility = View.VISIBLE
            binding.inviteButton2.visibility = View.GONE
        }

        binding.inviteButton3.setOnClickListener{
            binding.teamMemeberOne4.visibility = View.VISIBLE
            binding.inviteButton3.visibility = View.GONE
        }


        binding.deleteSecondMember.setOnClickListener{
            binding.teamMemeberOne2.visibility = View.GONE
            binding.inviteButton1.visibility = View.VISIBLE
        }
        binding.deleteThirdMember.setOnClickListener{
            binding.teamMemeberOne3.visibility = View.GONE
            binding.inviteButton2.visibility = View.VISIBLE
        }
        binding.deleteFourthMember.setOnClickListener{
            binding.teamMemeberOne4.visibility = View.GONE
            binding.inviteButton3.visibility = View.VISIBLE
        }


//        readFromFirebaseData()
//        setupObserver()
//        setupViews()

    }

    private fun setupViews() {

    }

    private fun setupObserver() {

    }

    private fun readFromFirebaseData() {

    }

}