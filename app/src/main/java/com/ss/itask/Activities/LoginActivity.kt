package com.ss.itask.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.ss.itask.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editText_email = findViewById<EditText>(R.id.editText_email)
        val editText_password = findViewById<EditText>(R.id.editText_password)
        val button_login = findViewById<Button>(R.id.button_login)
        val textView_register = findViewById<TextView>(R.id.textView_register)
        val textView_skip = findViewById<TextView>(R.id.textView_skip)


        textView_register.setOnClickListener(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        textView_skip.setOnClickListener(){
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }

    }


}
