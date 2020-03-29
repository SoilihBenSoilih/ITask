package com.ss.itask.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.ss.itask.App
import com.ss.itask.Model.Project
import com.ss.itask.R
import com.ss.itask.dao.ProjectDAO

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_pomodoro.setOnClickListener { view ->
            val intent = Intent(this,TimerActivity::class.java)
            startActivity(intent)
        }

        icon_settings.setOnClickListener {
            val intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
        }

        ranking_button.setOnClickListener {
            val intent = Intent(this, RankingListActivity::class.java)
            startActivity(intent)
        }

        statistics_button.setOnClickListener {

        }

        project_list_button.setOnClickListener {
            Toast.makeText(this,"Activity liste des projets à créer", Toast.LENGTH_SHORT).show()
        }

        add_project_button.setOnClickListener {
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
