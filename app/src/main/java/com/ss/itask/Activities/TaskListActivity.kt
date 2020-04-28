package com.ss.itask.Activities

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.itask.App
import com.ss.itask.Model.Project
import com.ss.itask.Model.Task
import com.ss.itask.R
import com.ss.itask.TaskListAdapter
import com.ss.itask.dao.TaskDAO

import kotlinx.android.synthetic.main.activity_task_list.*
import java.util.*

class TaskListActivity :  AppCompatActivity(), View.OnClickListener {

    lateinit var project: Project
    lateinit  var taskList: MutableList<Task>
    lateinit var  recyclerView : RecyclerView

    lateinit  var adapter :TaskListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val project = intent.getParcelableExtra<Project>("ProjectId")
        var projectName = findViewById<TextView>(R.id.projetct_name_tv)
        projectName.text = project.name
        taskList = mutableListOf()
        adapter = TaskListAdapter(taskList,this)
        recyclerView = findViewById(R.id.task_list_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskList = App.database.getAllTasks()
        recyclerView.adapter = adapter

        findViewById<ImageButton>(R.id.add_task_button).setOnClickListener {
            var taskNameEditText = findViewById<EditText>(R.id.add_taks_name_edit_text)
            val taskName = taskNameEditText.text
            if (taskName.trim().isEmpty()){
                Toast.makeText(this, "Veuillez renseigner le nom de la tache", Toast.LENGTH_SHORT).show()
            }else{
                var task = Task(taskName.toString(),0,"Ajourd'hui",0,project.id)
                taskNameEditText.text.clear()
                println(task.name)
                println(App.database.saveTask((TaskDAO()).createTask(task)))
            }
        }
    }

    override fun onClick(v: View?) {

    }

}
