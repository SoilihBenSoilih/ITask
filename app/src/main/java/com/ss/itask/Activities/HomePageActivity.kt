package com.ss.itask.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ss.itask.App
import com.ss.itask.Model.Project
import com.ss.itask.R
import com.ss.itask.dao.ProjectDAO

class HomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        findViewById<Button>(R.id.add_project_button).setOnClickListener {
            val addProjectDialogFragment = AddProjectDialogFragment()
            addProjectDialogFragment.listener = object: AddProjectDialogFragment.AddProjectDialogListener{
                override fun onDialogPositiveClick(projectName: String,projectColor: String) {
                   var projet = Project(projectName,projectColor,1)
                    App.database.saveProject(ProjectDAO().createProject(projet))
                    var list = App.database.getAllProjects()
                    for (p in list){
                        Log.e("===== HomePage =====",p.name+"=="+p.color)
                    }
                }

                override fun onDialogNegativeClick() {
                }

            }
            addProjectDialogFragment.show(supportFragmentManager,"Add project dialog")
        }
    }
}
