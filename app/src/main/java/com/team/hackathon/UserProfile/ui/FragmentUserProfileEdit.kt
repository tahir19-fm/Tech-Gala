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
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.team.hackathon.R
import com.team.hackathon.UserProfile.data.*
import com.team.hackathon.UserProfile.util.UserProfileViewModel
import com.team.hackathon.databinding.FragmentUserProfileEditBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.collections.HashMap

class FragmentUserProfileEdit : Fragment() {
    private var hashMap: HashMap<String, String> = HashMap()
    private var branches: String?=null
    private var year: String?=null
    private var gender: String?=null
    private var age : String?=null
    private var preferences: String?=null
    private val binding by lazy { FragmentUserProfileEditBinding.inflate(layoutInflater)}
    private val viewModel : UserProfileViewModel by activityViewModels()
    private val db = Firebase.firestore.collection("Users")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAllDropDown()

        setAllValuesFromUserProfile()

        setData()

        selectSelectedFromDropDown()

        setAllCursorAtTheEnd()




        binding.saveButton.setOnClickListener{
            val oldData = getOldUserData()
            val newData = getNewUserData()
            update(oldData,newData)
            Log.d("testingHashmap",hashMap.toString())
        }

        binding.backButtonEdit.setOnClickListener{
            viewModel.setUserProfileState(UserProfileActivity.DIET_ACTIVITY)
        }
        binding.progressBar.visibility = View.GONE



    }



    private fun setAllDropDown(){
        val branches = resources.getStringArray(R.array.braches)
        val arrayAdapterDate = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, branches) }
        binding.selectBranch.setAdapter(arrayAdapterDate)

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapterGender = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, gender) }
        binding.actSelectGender.setAdapter(arrayAdapterGender)


        val years = resources.getStringArray(R.array.years)
        val arrayAdapterYears = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, years) }
        binding.selectYear.setAdapter(arrayAdapterYears)

        val age = resources.getStringArray(R.array.age)
        val arrayAdapterAge = context?.let { ArrayAdapter(it,R.layout.dropdown_layout,age) }
        binding.actSelectAge.setAdapter(arrayAdapterAge)


    }

    private fun selectSelectedFromDropDown(){

        binding.selectBranch.setOnItemClickListener { parent, _, position, _ ->
            this.branches = parent.getItemAtPosition(position) as String
            if (branches!!.contains(this.branches!!)) {
                Toast.makeText(context, branches, Toast.LENGTH_LONG).show()
                hashMap["branchForEdit"] = this.branches!!
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

        binding.actSelectAge.setOnItemClickListener{ parent ,_, poition, _ ->
            age = parent.getItemAtPosition(poition) as String
            if (age!!.contains(this.age!!)){
                Toast.makeText(context,age,Toast.LENGTH_LONG).show()
                hashMap["ageForEdit"] = this.age!!
            }
        }



    }

    private fun setAllCursorAtTheEnd(){
        binding.selectBranch.setSelection(binding.selectBranch.length())
        binding.selectYear.setSelection(binding.selectYear.length())
        binding.actSelectGender.setSelection(binding.actSelectGender.length())
        binding.edpreferences.setSelection(binding.edpreferences.length())
        binding.actSelectAge.setSelection(binding.actSelectAge.length())
        binding.edCollageName.setSelection(binding.edCollageName.length())
        binding.actSelectCity.setSelection(binding.actSelectCity.length())
    }


    private fun setData(){
            val name = binding.nameEditText.text
            hashMap["nameForEdit"] = name.toString()

            val phoneNumber = binding.edphoneNumber.text
            hashMap["phoneNumberForEdit"] = phoneNumber.toString()

            val interest = binding.edpreferences.text
            hashMap["interestForEdit"] = interest.toString()

            val collageName = binding.edCollageName.text
            hashMap["collageNameForEdit"] = collageName.toString()

            val city = binding.actSelectCity.text
            hashMap["cityForEdit"] = city.toString()

            val defaultBranch = "B.TECH CSE"
            val defaultGender = "Male"
            val defaultYear = "1"
            val defaultAge = "20"

            hashMap["branchForEdit"] = defaultBranch
            hashMap["genderForEdit"] = defaultGender
            hashMap["yearForEdit"] = defaultYear
            hashMap["ageForEdit"] = defaultAge
            Log.d("testingHashmap",hashMap.toString())

    }

    private fun setAllValuesFromUserProfile(){
        viewModel.userData.observe(requireActivity()){
            val modal = it as UserDataForProfile
            binding.nameEditText.setText(modal.user.name)
            binding.edphoneNumber.setText(modal.user.phoneNumber)
            binding.actSelectGender.setText(modal.user.gender)
            binding.edCollageName.setText(modal.user.collageName)
            binding.selectBranch.setText(modal.user.branch)
            binding.selectYear.setText(modal.user.year)
            binding.edpreferences.setText(modal.user.interest)
            binding.actSelectAge.setText(modal.user.age)
            binding.actSelectCity.setText(modal.user.city)
        }

    }

    private fun getOldUserData() : UserDataClassForEdit{
        val name = hashMap["nameForEdit"]
        val phoneNumber = hashMap["phoneNumberForEdit"]
        val branch = hashMap["branchForEdit"]
        val year = hashMap["yearForEdit"]
        val gender = hashMap["genderForEdit"]
        val age = hashMap["ageForEdit"]
        val interest = hashMap["interestForEdit"]
        val collageName = hashMap["collageNameForEdit"]
        val city = hashMap["cityForEdit"]
        return UserDataClassForEdit(
            UserForProfileEdit(
            "abc@gmail.com",
            name.toString(), year.toString(),branch.toString(),gender.toString(),age.toString(),phoneNumber.toString(),interest.toString(),city.toString(),
                collageName.toString()

        )
        )
    }

    private fun getNewUserData() :Map<String , Any>{
        val name = binding.nameEditText.text
        val phoneNumber = binding.edphoneNumber.text
        val branch = hashMap["branchForEdit"]
        val year = hashMap["yearForEdit"]
        val gender = hashMap["genderForEdit"]
        val age = hashMap["ageForEdit"]
        val interest = binding.edpreferences.text
        val city = binding.actSelectCity.text
        val collageName = binding.edCollageName.text


        val map = mutableMapOf<String , Any>()
        map["name"] = name.toString()
        map["phone"] = phoneNumber.toString()
        map["branch"] = branch.toString()
        map["year"] = year.toString()
        map["gender"] = gender.toString()
        map["age"] = age.toString()
        map["interest"] = interest.toString()
        map["city"] = city.toString()
        map["collage_name"] = collageName.toString()
        return map
    }

    private fun update(data : UserDataClassForEdit , newData : Map<String, Any>) = CoroutineScope(Dispatchers.IO).launch {
        val userQuery = db
            .whereEqualTo("name" , data.user.name)
            .get()
            .await()
        if (userQuery.documents.isNotEmpty()){
            for(document in userQuery){
                try {
                    db.document(document.id).set(
                        newData,
                        SetOptions.merge()
                    )
                }catch (e:Exception){
                   withContext(Dispatchers.Main){
                       Toast.makeText(requireActivity(), e.message, Toast.LENGTH_LONG).show()
                   }
                }
            }
        }else{
            withContext(Dispatchers.Main){
                Toast.makeText(requireActivity(), "No User Found", Toast.LENGTH_LONG).show()
            }
        }

    }

}
