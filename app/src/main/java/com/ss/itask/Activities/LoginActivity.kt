package com.ss.itask.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.ss.itask.App
import com.ss.itask.Model.User
import com.ss.itask.R
import com.ss.itask.dao.Database
import com.ss.itask.dao.UserDAO

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
            val user = User("blalala","blavbla","blabla@gmail.com","blass123")
            user.id = 1
            val id = App.database.updateUSer(user)
            Log.e("INSERTION","Valeur de l'id: $id")
            startActivity(intent)
        }

        textView_skip.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)

          //  val intent = Intent(this, HomePageActivity::class.java)
            val list = Database(App.instance).getAllUsers()
            for (user in list){
                Log.e("====Lecture====",user.toString())
            }
            startActivity(intent)
        }

    }


}
