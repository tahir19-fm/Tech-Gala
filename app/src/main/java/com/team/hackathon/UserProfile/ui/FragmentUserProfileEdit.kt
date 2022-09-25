package com.team.hackathon.UserProfile.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.team.hackathon.R
import com.team.hackathon.UserProfile.data.*
import com.team.hackathon.UserProfile.util.UserProfileViewModel
import com.team.hackathon.UserProfile.util.UtilForMonth
import com.team.hackathon.databinding.FragmentUserProfileEditBinding
import java.util.*
import kotlin.collections.HashMap

class FragmentUserProfileEdit : Fragment() {
    private var hashMap: HashMap<String, String> = HashMap()
    private var date: String?=null
    private var year: String?=null
    private var gender: String?=null
    private var preferences: String?=null

    private val binding by lazy { FragmentUserProfileEditBinding.inflate(layoutInflater)}
    private val viewModel : UserProfileViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButtonEdit.setOnClickListener{
            viewModel.setUserProfileState(UserProfileActivity.DIET_ACTIVITY)
        }
        binding.progressBar.visibility = View.GONE
        //setAllValuesFromProfile()

        setAllDropDown()
        selectSelectedFromDropDown()


        //setNewUserData()
        onCLickSaveButton()

        setAllCursorAtTheEnd()

        setAllValuesFromUserProfile()


    }

    private fun setAllDropDown(){
        val date = resources.getStringArray(R.array.date)
        val arrayAdapterDate = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, date) }
        binding.selectDate.setAdapter(arrayAdapterDate)

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapterGender = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, gender) }
        binding.actSelectGender.setAdapter(arrayAdapterGender)


        val years = resources.getStringArray(R.array.years)
        val arrayAdapterYears = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, years) }
        binding.selectYear.setAdapter(arrayAdapterYears)


    }

    private fun selectSelectedFromDropDown(){

        binding.selectDate.setOnItemClickListener { parent, _, position, _ ->
            this.date = parent.getItemAtPosition(position) as String
            if (date!!.contains(this.date!!)) {
                Toast.makeText(context, date, Toast.LENGTH_LONG).show()
                hashMap["dateForEdit"] = this.date!!
            }
        }

        binding.actSelectGender.setOnItemClickListener { parent, _, position, _ ->
            gender = parent.getItemAtPosition(position) as String
            if (gender!!.contains(this.gender!!)) {
                Toast.makeText(context, gender, Toast.LENGTH_LONG).show()
                hashMap["genderForEdit"] = this.gender!!

            }
        }


        binding.selectYear.setOnItemClickListener { parent, _, position, _ ->
            year = parent.getItemAtPosition(position) as String
            if (year!!.contains(this.year!!)) {
                Toast.makeText(context, year, Toast.LENGTH_LONG).show()
                hashMap["yearForEdit"] = this.year!!

            }
        }



    }

    private fun setAllCursorAtTheEnd(){
        binding.selectDate.setSelection(binding.selectDate.length())
        binding.selectYear.setSelection(binding.selectYear.length())

        binding.actSelectGender.setSelection(binding.actSelectGender.length())
        binding.preferences.setSelection(binding.preferences.length())

        binding.edCollageName.setSelection(binding.edCollageName.length())
    }


    private fun onCLickSaveButton(){
        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text
            hashMap["nameForEdit"] = name.toString()

            val collageName = binding.edCollageName.text
            hashMap["allergiesForEdit"] = collageName.toString()

            Log.d("testingHashmap",hashMap.toString())

        }

    }

    private fun setAllValuesFromUserProfile(){
        viewModel.userData.observe(requireActivity()){
            val modal = it as UserDataForProfile
            binding.nameEditText.setText(modal.user.name)
            binding.edphoneNumber.setText(modal.user.phoneNumber)
            binding.actSelectGender.setText(modal.user.gender)
            binding.edCollageName.setText(modal.user.collageName)
            binding.selectDate.setText(modal.user.branch)
            binding.selectYear.setText(modal.user.year)
            binding.preferences.setText(modal.user.interest)

        }

    }




}