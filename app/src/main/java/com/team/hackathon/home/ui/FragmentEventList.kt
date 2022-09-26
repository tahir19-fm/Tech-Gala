package com.team.hackathon.home.ui
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.team.hackathon.databinding.FragmentEventListBinding
import com.team.hackathon.home.data.EventDataModel
import com.team.hackathon.home.util.HomeViewModel
import com.team.hackathon.home.util.ParentItemAdapter
import kotlin.collections.ArrayList


class FragmentEventList : Fragment() {
    private val binding by lazy { FragmentEventListBinding.inflate(layoutInflater) }
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var data: MutableList<EventDataModel>
    private val db = Firebase.firestore
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

        viewModel.userDataEvents.observe(this.requireActivity()) {
            val beta = it as ArrayList<EventDataModel>
            data = ArrayList()
            for (events in beta) {
                data.add(
                    EventDataModel(
                        events.image,
                        events.heading,
                        events.totalRegister,
                        events.lastDate,
                        events.teamType,
                        events.entryfees,
                        events.id
                    )
                )
            }
            //reading data from viewModel and setting recyclerView
            binding.progressBar.visibility = View.GONE
            setupRecycler(data)
        }
    }

    private fun setupViews() {
        refreshByPull()

    }

    private fun refreshByPull() {
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = false
            readFromFirebaseData()
        }
    }

    private fun setupRecycler(data: MutableList<EventDataModel>) {
        val layoutManager = LinearLayoutManager(requireActivity())
        val parentItemAdapter = ParentItemAdapter(data)
        binding.rvEventList.adapter = parentItemAdapter
        binding.rvEventList.layoutManager = layoutManager
    }

    private fun readFromFirebaseData() {
        binding.progressBar.visibility = View.VISIBLE
        val docRef = db.collection("events")
        docRef.get()
            .addOnSuccessListener { result ->
                Log.d("TAG", "readFromFirebaseData: ${result.documents}")
                if (result != null) {
                    val res = ArrayList<EventDataModel>()
                    for (document in result.documents) {
                        res.add(
                            EventDataModel(
                                document.getString("image").toString(),
                                document.getString("heading").toString(),
                                document.getString("totalRegister").toString() + " Registered",
                                "last date : " + document.getString("lastDate").toString(),
                                document.getString("teamType").toString(),
                                "Rupees: " + document.getString("entryfees").toString(),
                                document.id
                            )
                        )
                    }
                    res.reverse()
                    viewModel.fetchUserData(res)
                } else {
                    Log.d("Data", "No such document")
                }
            }.addOnFailureListener { exception ->
                Log.d("Data", "get failed with ", exception)
            }
    }


}