package com.dietTracker.invite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.team.hackathon.login.util.LoginViewModel
import com.team.hackathon.R
import com.team.hackathon.databinding.FragmentUserRegistrationBinding
import com.team.hackathon.login.util.UtilForMonth
import com.team.hackathon.login.util.isValidPhoneNumber
import com.team.hackathon.LoginActivity
import com.team.hackathon.login.data.Address
import com.team.hackathon.login.data.User
import com.team.hackathon.login.data.UserRegistrationDto
import java.util.*

class FragmentUserRegistration : Fragment() {

    var hashMap: HashMap<String, String> = HashMap<String, String>()
    var hashMapDOB: HashMap<String, Int> = HashMap<String, Int>()

    var date: String? = null
    var month: String? = null
    var year: String? = null
    var gender: String? = null
    var country: String? = null
    var dateInt: Int? = null
    var monthInt: Int? = null
    var yearInt: Int? = null

    private val viewModel: LoginViewModel by activityViewModels()
    private val binding by lazy { FragmentUserRegistrationBinding.inflate(layoutInflater) }

    companion object {
        fun getInstance() = FragmentUserRegistration()
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


        // Default Values Please Do Not Remove It
    }
}

