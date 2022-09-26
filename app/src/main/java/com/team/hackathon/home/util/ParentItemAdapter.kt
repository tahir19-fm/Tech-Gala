package com.team.hackathon.home.util

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso.get
import com.team.hackathon.R
import com.team.hackathon.eventregisteration.ui.EventRegistrationActivity
import com.team.hackathon.home.data.EventDataModel


class ParentItemAdapter internal constructor(private val itemList: List<EventDataModel>) : RecyclerView.Adapter<ParentItemAdapter.ParentViewHolder>() {
    var context: Context? = null
    companion object{
         var id=""
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ParentViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.event_list_card_view, viewGroup, false)
        return ParentViewHolder(view)
    }

    override fun onBindViewHolder(parentViewHolder: ParentViewHolder, position: Int) {


        val parentItem = itemList[position]
        if (parentItem.image.isNotEmpty()) {
            get().load(parentItem.image).into(parentViewHolder.imageUr)
        }
        parentViewHolder.headingPost.text=parentItem.heading
        parentViewHolder.total.text=parentItem.totalRegister
        parentViewHolder.lstDate.text=parentItem.lastDate
        parentViewHolder.teamTipe.text=parentItem.teamType
        parentViewHolder.regFee.text=parentItem.entryfees
        parentViewHolder.itemView.setOnClickListener{v->
            id=parentItem.id
            val intent = Intent(v.context, EventRegistrationActivity::class.java)
            Log.d("naaa", ": ${id}")
            intent.putExtra("id",id)
            v.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headingPost: TextView
        val imageUr : ImageView
        val total:TextView
        val  lstDate:TextView
        val teamTipe:TextView
        val regFee:TextView


        init { headingPost=itemView.findViewById(R.id.headingText)
            imageUr=itemView.findViewById(R.id.img)
            total=itemView.findViewById(R.id.totalRegister)
            lstDate=itemView.findViewById(R.id.lastDate)
            teamTipe=itemView.findViewById(R.id.tvTeamIndividual)
            regFee=itemView.findViewById(R.id.registrationFee)
        }

    }
}