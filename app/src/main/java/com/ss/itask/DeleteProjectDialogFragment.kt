package com.ss.itask

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DeleteProjectDialogFragment : DialogFragment() {

    interface DeleteProjectDialogListener {
        fun onDialogPositiveClick ()
        fun onDialogNegativeClick()
    }

    companion object{

        val EXTRA_PROJECT_NAME = "android.training.weather.EXTRA_PROJECT_NAME"

        fun newInstance(projectName : String) : DeleteProjectDialogFragment{
            val fragment = DeleteProjectDialogFragment()
            fragment.arguments = Bundle().apply {
                putString(EXTRA_PROJECT_NAME, projectName)
            }
            return fragment
        }
    }

    var listener: DeleteProjectDialogListener? = null
    private lateinit var projectName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        projectName = arguments!!.getString(EXTRA_PROJECT_NAME).toString()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(context)

        builder.setTitle("Voulez vous vraiment supprimer ${projectName}?")
            .setPositiveButton(
                "Supprimer",
                { _, _-> listener?.onDialogPositiveClick() })
            .setNegativeButton(
                "Annuler",
                { _, _-> })
        return builder.create()
    }
}