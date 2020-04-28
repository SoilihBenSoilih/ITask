package com.ss.itask


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.itask.Activities.ProjectAdapter
import com.ss.itask.Activities.TaskListActivity
import com.ss.itask.Model.Project
import com.ss.itask.dao.Database
import kotlinx.android.synthetic.main.content_task_list.*

class ProjectListFramgent : Fragment(),
    ProjectAdapter.ProjectItemListener {



    private lateinit var projects : MutableList<Project>
    private  lateinit var  database: Database
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : ProjectAdapter
    var projectsList = mutableListOf<Project>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = App.database
        projects = mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_first, container, false)
        recyclerView = view.findViewById(R.id.projects_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var list = database.getAllProjects()
        projectsList = list
        projects = database.getAllProjects()
        adapter = ProjectAdapter(projects, this)
        recyclerView.adapter = adapter
    }

    private fun showDeleteprojectDialog(project: Project) {
        val deleteProjectFragment =
            DeleteProjectDialogFragment.newInstance(
                project.name
            )

        deleteProjectFragment.listener = object:
            DeleteProjectDialogFragment.DeleteProjectDialogListener {
            override fun onDialogPositiveClick() {
                deleteProject(project)
            }

            override fun onDialogNegativeClick() {
                TODO("Not yet implemented")
            }


        }
        deleteProjectFragment.show(parentFragmentManager, "DeleteProjectDialog")
    }



    override fun onProjectSelected(project: Project) {
        val intent = Intent(this.context, TaskListActivity::class.java)
        intent.putExtra("ProjectId",project)
        startActivity(intent)
    }



    override fun onProjectDeleted(project: Project) {
        showDeleteprojectDialog(project)
    }

    fun deleteProject(project : Project) {
        if(database.deleteProjectSelected(project)){
            projects.remove(project)
            adapter.notifyDataSetChanged()
            App.database.deleteTaksByProject(project)
            Toast.makeText(context,"Le projet ${project.name} supprimée", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,"erreur lors de la suppression", Toast.LENGTH_SHORT).show()
        }
    }
}