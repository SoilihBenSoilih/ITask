package com.ss.itask.Activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.ss.itask.App
import com.ss.itask.R
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener

class AddProjectDialogFragment: DialogFragment() {
    var defaultColor =0
    var finalColor = 0
    interface AddProjectDialogListener{
        fun onDialogPositiveClick(projectName:String,projectColor:String)
        fun onDialogNegativeClick()
    }

    var listener: AddProjectDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        val input = EditText(context)
        val view = LayoutInflater.from(this.context).inflate(R.layout.add_project,null)
        val projectName = view.findViewById<EditText>(R.id.project_name_edit_text)
        val chooseColorButton = view.findViewById<Button>(R.id.choose_color_button)
        val colorShower = view.findViewById<Button>(R.id.color_shower)
        defaultColor = ContextCompat.getColor(App.instance,R.color.colorPrimary)
        finalColor = defaultColor
        chooseColorButton.setOnClickListener {
            showColorPicker(colorShower)
        }
        builder.setTitle("Nouveau projet")
            .setView(view)
            .setPositiveButton("Creer projet",
            DialogInterface.OnClickListener{_,_->
                listener?.onDialogPositiveClick(projectName.text.toString(),finalColor.toString())
            })
            .setNegativeButton("Annuler",
            DialogInterface.OnClickListener{dialog, _ ->dialog.cancel()
                listener?.onDialogNegativeClick()
            })
        return builder.create()
    }

    private fun showColorPicker(colorShower:Button) {
        val colorPicker =
            AmbilWarnaDialog(this.context, defaultColor, object : OnAmbilWarnaListener {
                override fun onCancel(dialog: AmbilWarnaDialog) {}
                override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                    finalColor = color
                    defaultColor = color
                    //colorShower.highlightColor = defaultColor
                   colorShower.setBackgroundColor(defaultColor)
                    Log.e("=============","dans la methode "+color)
                }
            })
        colorPicker.show()
    }

}