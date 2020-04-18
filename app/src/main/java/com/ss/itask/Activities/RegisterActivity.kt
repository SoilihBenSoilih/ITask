package com.ss.itask.Activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.ss.itask.R
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.IOException

class RegisterActivity : AppCompatActivity() {
    private  val PICK_IMAGE = 1
    lateinit var iconConnexion: ImageView
    var imageUri : Uri? = null
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
                        var bitmap: Bitmap =
                            MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                        iconConnexion.setImageBitmap(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
           }
//            }
//
//        }

    }
}
