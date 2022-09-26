package com.team.hackathon.eventregisteration.ui

import android.R
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dietTracker.login.ui.FragmentLoginOtp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.team.hackathon.databinding.FragmentEventDetailsBinding
import com.team.hackathon.eventregisteration.data.EventDetailsModalClass
import com.team.hackathon.eventregisteration.util.EventRegistrationViewModel
import com.team.hackathon.home.ui.FragmentEventRegistered


class EventDetailsFragment : Fragment() {
    private val binding by lazy { FragmentEventDetailsBinding.inflate(layoutInflater) }
    private val viewModel: EventRegistrationViewModel by activityViewModels()
    private val db = Firebase.firestore

    companion object {
        const val REGISTER=1
        const val REGISTERED=2
        const val COMPLETED=3
        const val DOWNLOAD_C=4
        const val RS="Rs "
        fun getInstance() = FragmentLoginOtp()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readFromFirebaseData()
        setupObserver()
        setupViews()

    }

    private fun setupViews() {

    binding.btnRegister.setOnClickListener{
        if (viewModel.registerDone.value== DOWNLOAD_C){
            //download c
           openChromeToDownloadCertificate()
        }else if (viewModel.registerDone.value==2){
            return@setOnClickListener
        }else if (viewModel.registerDone.value==3){
            return@setOnClickListener
        }else
        {
            viewModel.setUserState(EventRegistrationActivity.EVENT_REGISTER_FRAGMENT)
        }
    }


    }

    private fun openChromeToDownloadCertificate() {
        val url = ""
         val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireActivity(),Uri.parse(url))
    }


    private fun setupObserver() {
    viewModel.userDataEventsDetail.observe(this.requireActivity()){
        val data =it as EventDetailsModalClass
        if (data.image.isNotEmpty()) {
            Picasso.get().load(data.image).into(binding.EventImage)
        }
            binding.headingOf.text = data.heading
            binding.subHeadingOf.text = data.subHeading
            binding.adress.text = data.address
            binding.lastdateDeadline.text = data.lastDate
            if (data.entryfees != "0") {
                binding.entryfee.text = RS + data.entryfees
            } else {
                binding.entryfee.text = "free"
            }
            binding.teamLength.text = data.teamType
            binding.location.text = data.location
        binding.startEnddate.text=data.startEndDate
        viewModel.setPayment(data.entryfees.toString())
    }

        viewModel.registerDone.observe(this.requireActivity()){
            when(it){
                REGISTER->{
                    binding.btnRegister.text="register"
                }
               REGISTERED->{
                   binding.btnRegister.text="registered"
               }
                COMPLETED->{
                    binding.btnRegister.text="completed"
                }
                DOWNLOAD_C->{
                    binding.btnRegister.text="download certificate"
                }
            }
        }
    }



    private fun readFromFirebaseData() {
        Log.d("ray", "readFromFirebaseData:${viewModel.userId.value.toString()} ")
        val docRef = db.collection("events").document(viewModel.userId.value.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                Log.d("gf", "readFromFirebaseData: ${document}")
                if(document.exists()){


                       val res= EventDetailsModalClass(
                            document.getString("address").toString(),
                            document.getString("entryfees").toString(),
                            document.getString("heading").toString(),
                            document.getString("image").toString(),
                            document.getString("lastDate").toString(),
                            document.getString("location").toString(),
                            document.getString("subHeading").toString(),
                            document.getString("teamType").toString(),
                            document.getString("totalRegister").toString(),
                           document.getString("startEndDate").toString()
                        )
                    viewModel.fetchUserDataDetail(res)

                    Log.d("press", "dsata${res.address}")
                }else{
                    Log.d("Data", "No such document")
                }
            }.addOnFailureListener{ exception ->
                Log.d("Data", "get failed with ", exception)
            }

        val id=Firebase.auth.currentUser!!.phoneNumber.toString()
        val roc = db.collection(FragmentEventRegistered.EVENTS_REGISTERED).document(id).collection("events").document(viewModel.userId.value.toString())
        roc.get()
            .addOnSuccessListener {document->


                if (document.getString("completed").toString()=="1"){
                    viewModel.setUserExists(DOWNLOAD_C)
                }else
                if(document.exists()){
                    Log.d("Data", " exist document")
                   viewModel.setUserExists(REGISTERED)
                }else{
                    viewModel.setUserExists(REGISTER)
                    Log.d("Data", "No such document")
                }
            }.addOnFailureListener{ exception ->
                Log.d("Data", "get failed with ", exception)
            }

    }
}