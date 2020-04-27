package com.ss.itask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ss.itask.Model.Task

class TaskListAdapter (val tasks: MutableList<Task>, val itemClickListener: View.OnClickListener)
    : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardView = itemView.findViewById(R.id.task_list_cardview) as CardView
        val statusRadioButton = itemView.findViewById(R.id.task_status) as RadioButton
        val taskName = itemView.findViewById(R.id.task_name) as TextView
        val taskDate = itemView.findViewById(R.id.task_date) as TextView
        val launchButton = itemView.findViewById(R.id.launch_button) as ImageButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewItem = inflater.inflate(R.layout.task_list_item, parent, false)
        println("1111111111111111")
        return ViewHolder(viewItem)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.name
        holder.taskDate.text = task.date
        println("888888888888888888888888888888")
        if (task.status.toInt()==1){
            holder.statusRadioButton.isChecked = true
        }
        holder.cardView.tag = position
        holder.cardView.setOnClickListener(itemClickListener)
        holder.launchButton.setOnClickListener{

        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }


}