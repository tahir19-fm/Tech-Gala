package com.team.hackathon.home.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.team.hackathon.databinding.FragmentEventListBinding
import com.team.hackathon.home.data.EventDataModel
import com.team.hackathon.home.util.HomeViewModel
import com.team.hackathon.home.util.ParentItemAdapter
import kotlin.math.log


class FragmentEventList : Fragment() {
private val binding by lazy{FragmentEventListBinding.inflate(layoutInflater)}
    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var data:MutableList<EventDataModel>
    val db = Firebase.firestore
    companion object{
        fun getInstance() = FragmentEventList()
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

    private fun setupObserver() {
        data=ArrayList()
        viewModel.userData.observe(this.requireActivity()){
            val events=it as EventDataModel
            data.add(EventDataModel(events.image,events.heading,events.totalRegister,events.lastDate,events.teamType,events.entryfee))
            setupRecycler(data)
        }
    }

    private fun setupViews() {

    }

    private fun setupRecycler(data: MutableList<EventDataModel>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        val parentItemAdapter = ParentItemAdapter(data)
        binding.rvEventList.addItemDecoration(
            DividerItemDecoration(context, layoutManager.orientation)
        )
        binding.rvEventList.adapter = parentItemAdapter
        binding.rvEventList.layoutManager = layoutManager
    }
    private fun readFromFirebaseData(){
        val docRef = db.collection("events").document("WVxThG3PFKmVa9LT4U3b")
        docRef.get()
            .addOnSuccessListener { document ->
                if(document!=null){
                    //val gson= Gson()
                    //var daata:UserDataForProfile = gson.fromJson(data,UserDataForProfile::class.java)
                    Log.d("Data", "DocumentSnapshot data: ${document.data}")
                    val name = document.getString("image").toString()
                    viewModel.fetchUserData(

                            EventDataModel(
                                document.getString("image").toString(),
                                document.getString("heading").toString(),
                                document.getString("totalRegister").toString(),
                                document.getString("lastDate").toString(),
                                document.getString("teamType").toString(),
                                document.getString("entryfee").toString(),

                            )

                    )
                    Toast.makeText(requireActivity(),name,Toast.LENGTH_SHORT).show()
                }else{
                    Log.d("Data", "No such document")
                }
            }.addOnFailureListener{ exception ->
                Log.d("Data", "get failed with ", exception)
            }
    }


}