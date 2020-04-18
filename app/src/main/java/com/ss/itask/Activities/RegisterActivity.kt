package com.ss.itask.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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


        button_register.setOnClickListener(){
            if(editText_email.length()==0 || editText_password.length()==0 || editText_pseudo.length()==0 || editText_confirm_password.length()==0){
                if(editText_pseudo.length()==0){
                    editText_pseudo.setError("champ requis !")
                }
                else if(editText_email.length()==0){
                    editText_email.setError("champ requis !")
                }
                else if(editText_password.length()==0){
                    editText_password.setError("champ requis !")
                }
                else {
                    editText_confirm_password.setError("champ requis !")
                }
            }
            else{
                Toast.makeText(this, "à implémenter", Toast.LENGTH_SHORT).show()
            }

        }

        textView_skip.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
