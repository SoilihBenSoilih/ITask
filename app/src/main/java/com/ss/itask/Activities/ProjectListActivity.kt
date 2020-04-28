package com.ss.itask.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.ss.itask.Model.Project
import com.ss.itask.R
import com.ss.itask.dao.Database

class ProjectListActivity : AppCompatActivity(), View.OnClickListener {

    var projects = mutableListOf<String>()
    var projectsList = mutableListOf<Project>()

    var tab = mutableListOf<String>()

   lateinit var  recyclerView : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)

        val toolbar = findViewById(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }


////**************************ce petit code ne doit plus être là****************************/////////////////////////////
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
////**************************ce petit code ne doit plus être là****************************/////////////////////////////



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
