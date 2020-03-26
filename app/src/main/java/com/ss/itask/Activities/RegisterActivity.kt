package com.ss.itask.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.ss.itask.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editText_pseudo = findViewById<EditText>(R.id.editText_pseudo)
        val editText_email = findViewById<EditText>(R.id.editText_email)
        val editText_password = findViewById<EditText>(R.id.editText_password)
        val editText_confirm_password = findViewById<EditText>(R.id.editText_confirm_password)
        val button_register = findViewById<Button>(R.id.button_register)
        val textView_skip = findViewById<TextView>(R.id.textView_skip)


        textView_skip.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
