package com.ss.itask.Activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.BitmapCompat
import com.ss.itask.App
import com.ss.itask.Model.User
import com.ss.itask.R
import com.ss.itask.dao.Database
import com.ss.itask.dao.UserDAO
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import java.io.IOException
import java.lang.Exception

class RegisterActivity : AppCompatActivity() {
    private  val PICK_IMAGE = 1
    lateinit var iconConnexion: ImageView
    var imageUri : Uri? = null
    var bitmap: Bitmap?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editText_pseudo = findViewById<EditText>(R.id.editText_pseudo)
        val editText_email = findViewById<EditText>(R.id.editText_email)
        val editText_password = findViewById<EditText>(R.id.editText_password)
        val editText_confirm_password = findViewById<EditText>(R.id.editText_confirm_password)
        val button_register = findViewById<Button>(R.id.button_register)
        val textView_skip = findViewById<TextView>(R.id.textView_skip)

        iconConnexion = findViewById<ImageView>(R.id.icon_connexion)



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

                if(editText_confirm_password.text.toString() != editText_password.text.toString()){
                    Toast.makeText(this, "Le mot de passe et la confirmation doivent etre identique",
                        Toast.LENGTH_SHORT).show()
                }else{
                    try {
                        //File("./", "icon.png").writeBitmap(bitmap!!, Bitmap.CompressFormat.PNG, 85)
                        var user = User(imageUri.toString(),editText_pseudo.text.toString(),
                            editText_email.text.toString(),editText_password.text.toString())
                        var id = App.database.saveUser(UserDAO().createUser(user))
                        if (id!=null){
                            Toast.makeText(this, "Votre comte a ete cree avec succes", Toast.LENGTH_SHORT).show()
                            user.id = id
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("user",user)
                            startActivity(intent)
                        }else{

                        }
                        println("=========================SUcces==================")
                    }catch (e:Exception){
                        e.printStackTrace()
                        println("===================Erreur====================")
                    }
                }
            }

        }

        textView_skip.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        iconConnexion.setOnClickListener{
            val galleryIntent =Intent()
            galleryIntent.setType("image/*")
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(galleryIntent,"Choisissez une photo"),PICK_IMAGE)
            CropImage.startPickImageActivity(this)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data!=null) {
            imageUri = data!!.data
//            CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .setAspectRatio(1, 1)
//                .start(this)
//            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//                println("==============11111=============")
//                var result = CropImage.getActivityResult(data)
//                if (resultCode == RESULT_OK) {
//                    var resultUri: Uri = result.uri
                    try {
                        bitmap =  MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                        iconConnexion.setImageBitmap(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
           }
//            }
//
//        }

    }
    fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }
}
