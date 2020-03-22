package com.ss.itask.dao

import android.content.ContentValues
import android.util.Log
import com.ss.itask.App
import com.ss.itask.Model.User

class UserDAO {
    val TAG = UserDAO::class.java.simpleName
    companion object{
        const val USER_TABLE_NAME ="users"
        const val USER_KEY_ID = "id"
        const val USER_KEY_AVATAR = "avatar"
        const val USER_KEY_PSEUDO = "pseudo"
        const val USER_KEY_EMAIL = "email"
        const val USER_KEY_PASSWORD = "password"
        const val USER_CREATE_TABLE = "CREATE TABLE $USER_TABLE_NAME ($USER_KEY_ID " +
                "INTEGER PRIMARY KEY, $USER_KEY_AVATAR TEXT, $USER_KEY_PSEUDO TEXT," +
                " $USER_KEY_EMAIL TEXT, $USER_KEY_PASSWORD TEXT)"
        const val USER_QUERY_SELECT_ALL = "SELECT * FROM $USER_TABLE_NAME"
    }

    fun createUser(user: User):ContentValues{
        val values = ContentValues()
        values.put(USER_KEY_AVATAR,user.avatar)
        values.put(USER_KEY_PSEUDO,user.pseudo)
        values.put(USER_KEY_EMAIL,user.email)
        values.put(USER_KEY_PASSWORD,user.password)
        return  values;
    }

}