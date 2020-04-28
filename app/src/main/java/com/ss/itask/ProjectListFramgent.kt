package com.ss.itask


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ss.itask.Activities.ProjectAdapter
import com.ss.itask.Model.Project
import com.ss.itask.dao.Database

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
///******************************c'est dans cette fonction qu'il doit être appelé***************************************///
        Toast.makeText(context,"click sur ${project.name} ", Toast.LENGTH_SHORT).show()
///******************************c'est dans cette fonction qu'il doit être appelé***************************************///
    }



    override fun onProjectDeleted(project: Project) {
        showDeleteprojectDialog(project)
    }

    fun deleteProject(project : Project) {
        if(database.deleteProjectSelected(project)){
            projects.remove(project)
            adapter.notifyDataSetChanged()
            Toast.makeText(context,"ville ${project.name} supprimée", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context,"erreur lors de la suppression", Toast.LENGTH_SHORT).show()
        }
    }
}