package com.ss.itask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ProjectListAdapter(val projects: MutableList<String>, val itemClickListener: View.OnClickListener)
    : RecyclerView.Adapter<ProjectListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardView = itemView.findViewById(R.id.project_list_cardview) as CardView
        val project_imageView = itemView.findViewById(R.id.project_imageView) as ImageView
        val project_title_textView = itemView.findViewById(R.id.project_title_textView) as TextView
        val number_of_hours_textView = itemView.findViewById(R.id.number_of_hours_textView) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewItem = inflater.inflate(R.layout.project_list_item, parent, false)
        return ViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return projects.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tableau = projects[position]
        holder.project_imageView.setImageResource(R.drawable.icon_dossier)
        holder.project_title_textView.text =  tableau
        holder.number_of_hours_textView.text ="0h"

        holder.cardView.tag = position
        holder.cardView.setOnClickListener(itemClickListener)
    }
}