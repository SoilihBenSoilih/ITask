package com.ss.itask.Activities

import android.content.Intent
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
import kotlinx.android.synthetic.main.content_task_list.*
import java.util.*

class TaskListActivity :  AppCompatActivity(), View.OnClickListener {

    lateinit var project: Project
    companion object{
        lateinit  var taskList: MutableList<Task>
    }

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
        taskList = App.database.getTasksByProject(project.id)
        taskList.reverse()
        adapter = TaskListAdapter(taskList,this)
        recyclerView = findViewById(R.id.task_list_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        project_number_of_hours.text = (ProjectAdapter.getNumberOfHours(taskList)/60).toString()
        var taskDone =0
        var taskToDo = 0
        taskList.forEach{
                e->if (e.status==0){
            taskToDo++
        }else{
            taskDone++
        }
        }
        tasks_to_be_accomplished.text = taskToDo.toString()
        completed_tasks.text = taskDone.toString()
        findViewById<ImageButton>(R.id.add_task_button).setOnClickListener {
            var taskNameEditText = findViewById<EditText>(R.id.add_taks_name_edit_text)
            val taskName = taskNameEditText.text
            if (taskName.trim().isEmpty()){
                Toast.makeText(this, "Veuillez renseigner le nom de la tache", Toast.LENGTH_SHORT).show()
            }else{
                var task = Task(taskName.toString(),0,"Ajourd'hui",0,project.id)
                taskNameEditText.text.clear()
                task.id = App.database.saveTask((TaskDAO()).createTask(task))
                taskList.add(task)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onClick(v: View) {
        if (v.tag!=null){
            val index = v.tag as Int
            val task =  taskList[index]
            if (task.status==0){
                val intent = Intent(this,TimerActivity::class.java)
                intent.putExtra("tache",task)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Tâche déja effectuée", Toast.LENGTH_SHORT).show()
            }

        }
    }

}
