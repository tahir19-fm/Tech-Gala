package com.team.hackathon.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.team.hackathon.databinding.FragmentEventListBinding
import com.team.hackathon.home.data.EventDataModel
import com.team.hackathon.home.util.ParentItemAdapter


class FragmentEventList : Fragment() {
private val binding by lazy{FragmentEventListBinding.inflate(layoutInflater)}
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
        setupViews()

    }

    private fun setupViews() {
        val data:MutableList<EventDataModel>
        data=ArrayList()
        data.add(EventDataModel("https://idendron.hku.hk/wp-content/uploads/2020/12/Banner_rev.jpg","Join EY Techathon 3.0: Enter the Metaverse","16 registered","Last date:12/12/2002","Team","10 rupia"))
        data.add(EventDataModel("https://idendron.hku.hk/wp-content/uploads/2020/12/Banner_rev.jpg","Join EY Techathon 3.0: Enter the Metaverse","16 registered","Last date:12/12/2002","Team","10 rupia"))
        data.add(EventDataModel("https://idendron.hku.hk/wp-content/uploads/2020/12/Banner_rev.jpg","Join EY Techathon 3.0: Enter the Metaverse","16 registered","Last date:12/12/2002","Team","10 rupia"))
        data.add(EventDataModel("https://idendron.hku.hk/wp-content/uploads/2020/12/Banner_rev.jpg","Join EY Techathon 3.0: Enter the Metaverse","16 registered","Last date:12/12/2002","Team","10 rupia"))
       setupRecycler(data)
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
}