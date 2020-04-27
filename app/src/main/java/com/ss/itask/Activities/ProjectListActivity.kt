package com.ss.itask.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.itask.Model.Project
import com.ss.itask.ProjectListAdapter
import com.ss.itask.R
import com.ss.itask.dao.Database

class ProjectListActivity : AppCompatActivity(), View.OnClickListener {

    val database = Database(this)

    var projects = mutableListOf<String>()
    var projectsList = mutableListOf<Project>()

    var tab = mutableListOf<String>()

   lateinit var  recyclerView : RecyclerView

    val adapter = ProjectListAdapter(projects,this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)

        val toolbar = findViewById(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById<RecyclerView>(R.id.project_list_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var list = database.getAllProjects()
        projectsList = list

        tab.addAll(projects)
        recyclerView.adapter = adapter
        for((i,j) in list.withIndex()){
            projects.add(j.name)
          //  recyclerView.get(i).setBackgroundColor((projectsList[i].color).toInt())
        }

    }



    override fun onClick(view: View?) {
        if(view?.tag != null){
          val index = view.tag as Int
          val project = projectsList[index]
            view.setBackgroundColor((projectsList[index].color).toInt())
            val intent = Intent(this,TaskListActivity::class.java)
            intent.putExtra("ProjectId",project)
            startActivity(intent)
       }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

            when (item.itemId) {
                R.id.action_search -> {

                    }
                R.id.action_delete -> {
                    Toast.makeText(this, "action à implémenter.",Toast.LENGTH_SHORT).show()
                }
                else -> return super.onOptionsItemSelected(item)
            }
        return super.onOptionsItemSelected(item)
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.project_list_toolbar, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        if(searchItem != null){
            val searchView = searchItem.actionView as SearchView
            val edittext = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            edittext?.hint = "Recherche..."
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                   if(newText!!.isNotEmpty()) {
                        tab.clear()
                        val search = newText.toLowerCase()

                        projects.forEach(){
                            if(it.toLowerCase().contains(search)){
                                tab.add(it)
                            }
                        }
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                    else {
                        tab.clear()
                        tab.addAll(projects)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                    return true
                }

            })
        }
        return true
    }


}
