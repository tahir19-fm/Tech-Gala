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
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.team.hackathon.R
import com.team.hackathon.UserProfile.data.UserDataForProfile
import com.team.hackathon.UserProfile.data.UserForProfile
import com.team.hackathon.UserProfile.util.UserProfileViewModel
import com.team.hackathon.databinding.FragmentUserProfileBinding
import org.json.JSONException
import org.json.JSONObject

class FragmentUserProfile : Fragment() , PaymentResultListener  {
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {

        readFromFirebaseData()
        setData()
        super.onViewCreated(view, savedInstanceState)

        binding.sendmoney.setOnClickListener{
            val amt = binding.edamount.text.toString()
            val amount = Math.round(amt.toFloat() * 100)
            val checkout = Checkout()

            checkout.setKeyID("rzp_test_lDBxxnlPFiPUGx")

            checkout.setImage(R.drawable.banner_rev)
            val obj = JSONObject()
            try {
                obj.put("name", "Geeks for Geeks")

                // put description
                obj.put("description", "Test payment")

                // to set theme color
                obj.put("theme.color", "")

                // put the currency
                obj.put("currency", "INR")

                // put amount
                obj.put("amount", amount)

                // put mobile number
                obj.put("prefill.contact", "9284064503")

                // put email
                obj.put("prefill.email", "chaitanyamunje@gmail.com")

                checkout.open(requireActivity(),obj)
            }catch (e:JSONException){
                e.printStackTrace()
            }

        }



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
        hideAllContentShowProgressBar()
        val docRef = db.collection("Users").document("FmUhRcWqDqA6ifxHIm1U")
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
                                document.getString("phone").toString(),
                                document.getString("profile_picture_url").toString(),
                                document.getString("interest").toString(),
                                document.getString("city").toString(),
                                document.getString("country").toString(),
                                document.getString("collage_name").toString()
                            )
                        )
                    )
                    Toast.makeText(requireActivity(),name,Toast.LENGTH_SHORT).show()
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
            binding.tvYear.text = modal.user.year + " Year"
            binding.tvAge.text = modal.user.age + " yrs"
            binding.tvInterest.text = modal.user.interest
            binding.tvAddress.text = modal.user.city
            binding.tvCollageName.text = modal.user.collageName
        }
    }



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

    override fun onPaymentSuccess(s: String?) {
        Toast.makeText(requireActivity(), "Payment is successful : " + s, Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int, s: String?) {
        Toast.makeText(requireActivity(), "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }

}