package com.ss.itask.Activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ss.itask.Model.Project
import com.ss.itask.R

class ProjectAdapter(private val projects: List<Project>,
                     private val ProjectListener : ProjectAdapter.ProjectItemListener)
        : RecyclerView.Adapter<ProjectAdapter.ViewHolder>(), View.OnClickListener {

    interface ProjectItemListener {
        fun onProjectSelected(project: Project)
        fun onProjectDeleted(project: Project)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.project_list_card_view)!!
        val project_title_textView = itemView.findViewById<TextView>(R.id.project_title_textView)!!
        val number_of_hours_textView = itemView.findViewById<TextView>(R.id.number_of_hours_textView)!!
        val delete_project = itemView.findViewById<View>(R.id.delete_project)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent?.context)
            .inflate(R.layout.project_list_item, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects[position]
        with(holder){
            cardView.tag = project
            cardView.setOnClickListener(this@ProjectAdapter)
            project_title_textView.text = project.name
            number_of_hours_textView.text = "0h"
            delete_project.tag = project
            delete_project.setOnClickListener(this@ProjectAdapter)
        }
    }

    override fun getItemCount(): Int = projects.size

    override fun onClick(view: View) {
        when(view.id) {
            R.id.project_list_card_view -> ProjectListener.onProjectSelected(view.tag as Project)
            R.id.delete_project -> ProjectListener.onProjectDeleted(view.tag as Project)
        }
    }
}