package com.team.hackathon.UserProfile.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.team.hackathon.UserProfile.util.UserProfileViewModel
import com.team.hackathon.databinding.FragmentUserProfileBinding

class FragmentUserProfile : Fragment() {
    private val binding by lazy { FragmentUserProfileBinding.inflate(layoutInflater)}
    private val viewModel : UserProfileViewModel by activityViewModels()
    private val pickImage  = 100
    private var imageUri : Uri? = null
    private var imageUrlFromFirebase :String?= null
    private var userNameFromFirebase:String?=null
    private var allergies : String = ""
    private val db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        readFromFirebaseData()
        super.onViewCreated(view, savedInstanceState)
        binding.editButton.setOnClickListener{
            viewModel.setUserProfileState(UserProfileActivity.USER_PROFILE_EDIT)
        }
        binding.backButton.setOnClickListener{
            viewModel.setUserProfileState(UserProfileActivity.DIET_ACTIVITY)
        }
        binding.cameraButton.setOnClickListener{
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery,pickImage)

        }

        binding.signOutButton.setOnClickListener{
            viewModel.setUserProfileState(UserProfileActivity.SPLASH_SCREEN)
            FirebaseAuth.getInstance().signOut()

        }

        //viewModel.fetachUserData("hfSkfOT7YbcxrEqDdbTa")
        //apiViewModal.fetchUserData(FirebaseAuth.getInstance().currentUser!!.uid)
        //setUpObserver()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK && requestCode==pickImage){
            imageUri = data?.data
            binding.tvUserProfile.visibility = View.GONE
            binding.ivUserProfile.setImageURI(imageUri)

        }
    }

    private fun readFromFirebaseData(){
        val docRef = db.collection("Users").document("FmUhRcWqDqA6ifxHIm1U")
        docRef.get()
            .addOnSuccessListener { document ->
                if(document!=null){
                    Log.d("Data", "DocumentSnapshot data: ${document.data}")
                }else{
                    Log.d("Data", "No such document")
                }
            }.addOnFailureListener{ exception ->
                Log.d("Data", "get failed with ", exception)
            }
    }

    /*private fun setUpObserver(){
        viewModel.userData.observe(requireActivity()){
            when(it){
                ApiResult.Loading -> {
                    hideAllContentShowProgressBar()

                }
                is ApiResult.Success -> {
                    showALlContentHideProgresssBar()
                    val data = it.data as UserDataForProfile
                    binding.tvUserName.text = data.name
                    userNameFromFirebase = data.name
                    binding.tvPhoneNumber.text = data.phoneNumber
                    val city = data.address.city
                    val country = data.address.country
                    val address = "$city , $country"
                    binding.tvAddress.text = address
                    binding.tvWeight.text = data.weight+" Kg"
                    binding.tvHeight.text = data.Height+" cm"
                    binding.tvGender.text = data.gender
                    binding.tvAge.text = data.age+" yrs"
                    binding.tvType.text = data.foodPreferences.type
                    val calender = Calendar.getInstance()
                    calender.timeInMillis = data.dob!!
                    val monthName = UtilsForMonthName.returnMonthName(calender.get(Calendar.MONTH))
                    val date : String = calender.get(Calendar.DAY_OF_MONTH).toString() + " " + monthName + " " + calender.get(
                        Calendar.YEAR)
                    binding.tvDob.text = date

                    imageUrlFromFirebase = data.profilePicture
                    if (data.foodPreferences!=null){
                        data.foodPreferences.allergies?.forEach{ item ->
                            //Toast.makeText(requireActivity(),item,Toast.LENGTH_SHORT).show()
                            allergies = "$allergies $item "
                        }
                    }
                    binding.tvAllergie.text = allergies
                    Log.d("array",allergies)
                    DownloadImageFromInternet(binding.ivUserProfile).execute(imageUrlFromFirebase)
                    viewModel.setUserDataForEdit(
                        UserDataForProfile(
                            "Testing_Id",
                            data.name,
                            Address(
                                data.address.country,
                                data.address.city
                            ),
                            data.weight,
                            data.Height,
                            data.gender,
                            data.age,
                            data.phoneNumber,
                            FoodPreferences(
                                listOf(allergies),
                                data.foodPreferences.type
                            ),
                            "null",
                            data.dob
                        )
                    )
                }
                is ApiResult.Error ->{
                    Toast.makeText(requireActivity(), "Error : ${it.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }*/


    @SuppressLint("StaticFieldLeak")
    @Suppress("DEPRECATION")
    private inner class DownloadImageFromInternet(var imageView: ImageView) : AsyncTask<String, Void, Bitmap?>(){
        init {
            //Toast.makeText(requireActivity(),"image setting pls wait",Toast.LENGTH_SHORT).show()
        }
        override fun doInBackground(vararg urls: String?): Bitmap? {
            val imageURL = urls[0]
            var image : Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
            }
            catch (e:Exception){
                Log.e("Error Message", e.message.toString())
                e.printStackTrace()
            }
            return image
        }

        override fun onPostExecute(result: Bitmap?) {
            if(imageUrlFromFirebase!=null){
                binding.tvUserProfile.visibility = View.GONE
                imageView.setImageBitmap(result)
            }else if(imageUrlFromFirebase==null){
                binding.tvUserProfile.visibility = View.VISIBLE
                binding.tvUserProfile.text = userNameFromFirebase?.get(0).toString()
            }
        }

    }

    private fun hideAllContentShowProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
        binding.ivUserProfile.visibility = View.INVISIBLE
        binding.cameraButton.visibility = View.INVISIBLE
        binding.lluserInformation.visibility = View.INVISIBLE
        binding.llBasicInformationHeading.visibility = View.INVISIBLE
        binding.glBasicInformation.visibility = View.INVISIBLE
        binding.llFoodPreferencesHeading.visibility = View.INVISIBLE
        binding.glFoodPreferencesInformation.visibility = View.INVISIBLE
        binding.signOutButton.visibility = View.INVISIBLE
        binding.llFirstLine.visibility = View.INVISIBLE
        binding.llSecondLine.visibility = View.INVISIBLE
        binding.llThirdLine.visibility = View.INVISIBLE
    }

    private fun showALlContentHideProgresssBar(){
        binding.progressBar.visibility = View.GONE
        binding.ivUserProfile.visibility = View.VISIBLE
        binding.cameraButton.visibility = View.VISIBLE
        binding.lluserInformation.visibility = View.VISIBLE
        binding.llBasicInformationHeading.visibility = View.VISIBLE
        binding.glBasicInformation.visibility = View.VISIBLE
        binding.llFoodPreferencesHeading.visibility = View.VISIBLE
        binding.glFoodPreferencesInformation.visibility = View.VISIBLE
        binding.signOutButton.visibility = View.VISIBLE
        binding.llFirstLine.visibility = View.VISIBLE
        binding.llSecondLine.visibility = View.VISIBLE
        binding.llThirdLine.visibility = View.VISIBLE
    }

}