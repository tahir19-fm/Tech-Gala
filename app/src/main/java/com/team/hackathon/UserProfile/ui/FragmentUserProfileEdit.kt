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
import com.team.hackathon.UserProfile.data.AddressForEdit
import com.team.hackathon.UserProfile.data.DataClassForEdit
import com.team.hackathon.UserProfile.data.FoodPreferencesForEdit
import com.team.hackathon.UserProfile.data.User
import com.team.hackathon.UserProfile.util.UserProfileViewModel
import com.team.hackathon.UserProfile.util.UtilForMonth
import com.team.hackathon.databinding.FragmentUserProfileEditBinding
import java.util.*
import kotlin.collections.HashMap

class FragmentUserProfileEdit : Fragment() {
    private var hashMap: HashMap<String, String> = HashMap()
    private var date: String?=null
    private var month: String?=null
    private var year: String?=null
    private var gender: String?=null
    private var country: String?=null
    private var foodPreferences: String?=null

    private var monthNumber : Int?=null

    private var dateForConverting : String?=null
    private var monthForConverting : String?=null
    private var yearForConverting  :String?=null

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
        //
        // Default Values Please Do Not Remove It
//        hashMap.put("date","12")
//        hashMap.put("month","May")
//        hashMap.put("year","2000")
//        hashMap.put("gender","Male")
//        hashMap.put("country","India")
//        hashMap.put("foodPreferences","Vegetarian")
//        hashMap.put("allergies","Egg Yolk , Chicken , Spinach")
        //
        // Default Values Please Do Not Remove It


    }

    private fun setAllDropDown(){
        val date = resources.getStringArray(R.array.date)
        val arrayAdapterDate = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, date) }
        binding.selectDate.setAdapter(arrayAdapterDate)

        val month = resources.getStringArray(R.array.month)
        val arrayAdapterMonth = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, month) }
        binding.selectMonth.setAdapter(arrayAdapterMonth)

        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapterGender = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, gender) }
        binding.actSelectGender.setAdapter(arrayAdapterGender)

        val country = resources.getStringArray(R.array.countries_array)
        val arrayAdapterCountry =
            context?.let { ArrayAdapter(it, R.layout.dropdown_layout, country) }
        binding.selectCountryd.setAdapter(arrayAdapterCountry)

        val years = resources.getStringArray(R.array.years)
        val arrayAdapterYears = context?.let { ArrayAdapter(it, R.layout.dropdown_layout, years) }
        binding.selectYear.setAdapter(arrayAdapterYears)

        val foodPreferences = resources.getStringArray(R.array.foodPreference)
        val arrayAdapterFoodPreferences = context?.let { ArrayAdapter(it,R.layout.dropdown_layout,foodPreferences) }
        binding.actSelectFoodPreferences.setAdapter(arrayAdapterFoodPreferences)
    }

    private fun selectSelectedFromDropDown(){

        binding.selectDate.setOnItemClickListener { parent, _, position, _ ->
            this.date = parent.getItemAtPosition(position) as String
            if (date!!.contains(this.date!!)) {
                Toast.makeText(context, date, Toast.LENGTH_LONG).show()
                hashMap["dateForEdit"] = this.date!!
            }
        }

        binding.selectMonth.setOnItemClickListener { parent, _, position, _ ->
            month = parent.getItemAtPosition(position) as String
            if (month!!.contains(this.month!!)) {
                monthNumber = UtilForMonth.returnMonthNumber(month.toString())
                Toast.makeText(context, month, Toast.LENGTH_LONG).show()
                hashMap["monthForEdit"] = monthNumber.toString()
            }
        }

        binding.actSelectGender.setOnItemClickListener { parent, _, position, _ ->
            gender = parent.getItemAtPosition(position) as String
            if (gender!!.contains(this.gender!!)) {
                Toast.makeText(context, gender, Toast.LENGTH_LONG).show()
                hashMap["genderForEdit"] = this.gender!!

            }
        }

        binding.selectCountryd.setOnItemClickListener { parent, _, position, _ ->
            country = parent.getItemAtPosition(position) as String
            if (country!!.contains(this.country!!)) {
                Toast.makeText(context, country, Toast.LENGTH_LONG).show()
                hashMap["countryForEdit"] = this.country!!
            }
        }

        binding.selectYear.setOnItemClickListener { parent, _, position, _ ->
            year = parent.getItemAtPosition(position) as String
            if (year!!.contains(this.year!!)) {
                Toast.makeText(context, year, Toast.LENGTH_LONG).show()
                hashMap["yearForEdit"] = this.year!!

            }
        }

        binding.actSelectFoodPreferences.setOnItemClickListener { parent, _, position, _ ->
            foodPreferences = parent.getItemAtPosition(position) as String
            if (foodPreferences!!.contains(this.foodPreferences!!)) {
                Toast.makeText(context, foodPreferences, Toast.LENGTH_LONG).show()
                hashMap["foodPreferencesForEdit"] = foodPreferences!!

            }
        }

    }

    private fun setAllCursorAtTheEnd(){
        binding.selectDate.setSelection(binding.selectDate.length())
        binding.selectMonth.setSelection(binding.selectMonth.length())
        binding.selectYear.setSelection(binding.selectYear.length())
        binding.selectCountryd.setSelection(binding.selectCountryd.length())
        binding.actSelectGender.setSelection(binding.actSelectGender.length())
        binding.actSelectFoodPreferences.setSelection(binding.actSelectFoodPreferences.length())
        binding.selectcityDropdown.setSelection(binding.selectcityDropdown.length())
        binding.actSelectHeight.setSelection(binding.actSelectHeight.length())
        binding.edSelectWeight.setSelection(binding.edSelectWeight.length())
        binding.edSelectAllergies.setSelection(binding.edSelectAllergies.length())
    }

//    private fun setAllValuesFromProfile(){
//        viewModel.userDataForEdit.observe(this.requireActivity()){
//            val modal = it as UserDataForProfile
//            binding.nameEditText.setText(modal.name)
//            binding.edphoneNumber.setText(modal.phoneNumber)
//
//            val calender = Calendar.getInstance()
//            calender.timeInMillis = modal.dob!!
//            monthForConverting = UtilsForMonthName.returnMonthName(calender.get(Calendar.MONTH))
//            dateForConverting = calender.get(Calendar.DAY_OF_MONTH).toString()
//            yearForConverting = calender.get(Calendar.YEAR).toString()
//
//            binding.selectDate.setText(dateForConverting.toString())
//            binding.selectMonth.setText(monthForConverting.toString())
//            binding.selectYear.setText(yearForConverting.toString())
//            binding.actSelectGender.setText(modal.gender)
//            binding.selectCountryd.setText(modal.address.country)
//            binding.selectcityDropdown.setText(modal.address.city)
//            binding.edSelectWeight.setText(modal.weight)
//            binding.actSelectHeight.setText(modal.Height)
//            binding.actSelectFoodPreferences.setText(modal.foodPreferences.type)
//            binding.edSelectAllergies.setText(modal.foodPreferences.allergies.toString())
//
//            hashMap["nameForEdit"] = modal.name!!
//            hashMap["phoneForEdit"] = modal.phoneNumber!!
//
//            hashMap["dateForEdit"] = dateForConverting.toString()
//            val monthNumber:Int ? = UtilForMonth.returnMonthNumber(monthForConverting.toString())
//            hashMap["monthForEdit"] = monthNumber.toString()
//            hashMap["yearForEdit"] = yearForConverting.toString()
//
//            hashMap["genderForEdit"] = modal.gender!!
//            hashMap["countryForEdit"] = modal.address.country!!
//            hashMap["cityForEdit"] = modal.address.city!!
//            hashMap["weightForEdit"] = modal.weight!!
//            hashMap["heightForEdit"] = modal.Height!!
//            hashMap["foodPreferencesForEdit"] = modal.foodPreferences.type!!
//            hashMap["allergiesForEdit"] = modal.foodPreferences.allergies.toString()
//        }
//
//
//    }

    private fun onCLickSaveButton(){
        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text
            hashMap["nameForEdit"] = name.toString()

            val city = binding.selectcityDropdown.text
            hashMap["cityForEdit"] = city.toString()

            val allergies = binding.edSelectAllergies.text
            hashMap["allergiesForEdit"] = allergies.toString()

            val height = binding.actSelectHeight.text
            hashMap["heightForEdit"] = height.toString()

            val weight = binding.edSelectWeight.text
            hashMap["weightForEdit"] = weight.toString()

            Log.d("testingHashmap",hashMap.toString())

            //patchData()
        }

    }

    private fun setNewUserData(){
        convertDOBtoMilliSecond()
        setNewDataOnEditDataClass()
    }

//    private fun  patchData(){
//        setNewUserData()
//        viewModel.newUserData.observe(this.requireActivity()){
//            val modal = it as DataClassForEdit
//            viewModel.patchUserData(modal)
//            apiObserver()
//        }
//    }

//    private fun apiObserver() {
//        viewModel.patchUserData.observe(requireActivity()) {
//            when (it) {
//                ApiResult.Loading -> {
//                    Toast.makeText(requireActivity(), "uploading data", Toast.LENGTH_SHORT).show()
//                    binding.saveButton.visibility = View.GONE
//                    binding.progressBar.visibility = View.VISIBLE
//                    //Show loading state. It can be progress bar or shimmer or something else.
//                }
//                is ApiResult.Success -> {
//                    val name = hashMap["nameForEdit"]
//                    binding.saveButton.visibility = View.VISIBLE
//                    binding.progressBar.visibility = View.GONE
//                    Toast.makeText(requireActivity(), "data added  + $name", Toast.LENGTH_SHORT).show()
//
//                }
//                is ApiResult.Error -> {
//                    Toast.makeText(
//                        this.requireActivity(),
//                        "error : ${it.message}",
//                        Toast.LENGTH_LONG
//                    ).show()
//                    Timber.i("apiResponse : ${it.message}")
//                }
//            }
//        }
//    }

    private fun convertDOBtoMilliSecond(){
        val finalYear = hashMap["yearForEdit"]
        val month = hashMap["monthForEdit"]
        val finalDate = hashMap["dateForEdit"]


        Log.d("month",finalDate.toString())
        Log.d("month1",month.toString())
        Log.d("month2",finalYear.toString())
        Log.d("main",month.toString())

        val calender = Calendar.getInstance()
        finalYear?.let { it1 ->
            month.let { it2 ->
                finalDate?.let { it3 ->
                    if (it2 != null) {
                        calender.set(
                            it1.toInt(),
                            it2.toInt(), it3.toInt()
                        )
                    }
                }
            }
        }
        val date = calender.timeInMillis

        hashMap["date_of_birth"] = date.toString()
    }

    private fun setNewDataOnEditDataClass(){
        viewModel.setNewUserData(
            DataClassForEdit(
                User(
                    "Testing_id",
                    hashMap["nameForEdit"].toString(),
                    AddressForEdit(
                        hashMap["countryForEdit"].toString(),
                        hashMap["cityForEdit"].toString()
                    ),
                    hashMap["weightForEdit"].toString(),
                    hashMap["heightForEdit"].toString(),
                    hashMap["genderForEdit"].toString(),
                    "55",
                    hashMap["phoneForEdit"].toString(),
                    FoodPreferencesForEdit(
                        listOf(hashMap["allergiesForEdit"].toString()),
                        hashMap["foodPreferencesForEdit"].toString()
                    ),
                    "newUrl",
                    hashMap["date_of_birth"]?.toLong()
                )
            )
        )
    }
}