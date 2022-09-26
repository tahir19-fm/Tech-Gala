package com.team.hackathon.UserProfile.ui
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.team.hackathon.UserProfile.data.UserDataForProfile
import com.team.hackathon.UserProfile.data.UserForProfile
import com.team.hackathon.UserProfile.util.UserProfileViewModel
import com.team.hackathon.databinding.FragmentUserProfileBinding


class FragmentUserProfile : Fragment()  {
    private val binding by lazy { FragmentUserProfileBinding.inflate(layoutInflater)}
    private val viewModel : UserProfileViewModel by activityViewModels()
    private val pickImage  = 100
    private var imageUri : Uri? = null
    private val db = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {

        readFromFirebaseData()
        setData()
        super.onViewCreated(view, savedInstanceState)



        binding.editButton.setOnClickListener{
            viewModel.setUserProfileState(UserProfileActivity.USER_PROFILE_EDIT)
        }
        binding.backButton.setOnClickListener{
            viewModel.setUserProfileState(UserProfileActivity.DIET_ACTIVITY)
        }
        binding.signOutButton.setOnClickListener{
            viewModel.setUserProfileState(UserProfileActivity.SPLASH_SCREEN)
            FirebaseAuth.getInstance().signOut()

        }

    }


    private fun readFromFirebaseData(){
        hideAllContentShowProgressBar()
        val id =FirebaseAuth.getInstance().currentUser!!.phoneNumber.toString()
        val docRef = db.collection("users").document(id)
        docRef.get()
            .addOnSuccessListener { document ->
                if(document!=null){
                    Log.d("Data", "DocumentSnapshot data: ${document.data}")
                    val name = document.getString("gender").toString()
                    viewModel.setUserData(
                        UserDataForProfile(
                            UserForProfile(
                                document.getString("college_id").toString(),
                                document.getString("name").toString(),
                                document.getString("year").toString(),
                                document.getString("branch").toString(),
                                document.getString("gender").toString(),
                                document.getString("age").toString(),
                                document.getString("phoneNumber").toString(),
                                document.getString("interest").toString(),
                                document.getString("city").toString(),
                                document.getString("collage_name").toString()
                            )
                        )
                    )
                    setData()
                    showALlContentHideProgresssBar()
                }else{
                    Log.d("Data", "No such document")
                }
            }.addOnFailureListener{ exception ->
                Log.d("Data", "get failed with ", exception)
            }

    }

    private fun setData(){
        viewModel.userData.observe(requireActivity()){
            val modal = it as UserDataForProfile
            binding.tvUserName.text = modal.user.name
            binding.tvPhoneNumber.text = modal.user.phoneNumber
            binding.tvBranch.text = modal.user.branch
            binding.tvGender.text = modal.user.gender
            binding.tvYear.text = modal.user.year + " Year"
            binding.tvAge.text = modal.user.age + " yrs"
            binding.tvInterest.text = modal.user.interest
            binding.tvAddress.text = modal.user.city
            binding.tvCollageName.text = modal.user.collageName
            binding.tvUserProfile.text = modal.user.name[0].uppercase()
        }
    }


    private fun hideAllContentShowProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
        binding.ivUserProfile.visibility = View.INVISIBLE
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