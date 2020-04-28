package com.ss.itask.Activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.ss.itask.App
import com.ss.itask.Model.Project
import com.ss.itask.Model.Task
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
        val colored_layout  = cardView.findViewById<ConstraintLayout>(R.id.coled_layout)
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
            holder.colored_layout.setBackgroundColor(project.color.toInt())
            cardView.setOnClickListener(this@ProjectAdapter)
            project_title_textView.text = project.name
            number_of_hours_textView.text = "${getNumberOfHours(App.database.getTasksByProject(project.id))/60}h"
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
companion object{
    fun getNumberOfHours(taksList: MutableList<Task>): Float{
        var nbh = 0.0f
        for (task in taksList){
            nbh += task.duration
        }
        return nbh
    }
}

}
