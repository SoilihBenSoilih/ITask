package com.ss.itask.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.ss.itask.R

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_pomodoro.setOnClickListener { view ->
            Snackbar.make(view, "Nouveau pomodoro", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        icon_settings.setOnClickListener {
            Toast.makeText(this,"Activité paramètres à créer", Toast.LENGTH_SHORT).show()
        }

        ranking_button.setOnClickListener {
            val intent = Intent(this, RankingListActivity::class.java)
            startActivity(intent)
        }

        statistics_button.setOnClickListener {
            Toast.makeText(this,"Statistique activity à créer", Toast.LENGTH_SHORT).show()
        }

        project_list_button.setOnClickListener {
            Toast.makeText(this,"Activity liste des projets à créer", Toast.LENGTH_SHORT).show()
        }

        add_project_button.setOnClickListener {
            val intent = Intent(this, PomodoroActivity::class.java)
            startActivity(intent)
        }
    }

}
