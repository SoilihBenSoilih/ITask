package com.ss.itask.Model

data class User(var id:Long, var avatar: String, var pseudo: String, var email:String, var password:String){
    constructor(avatar: String,pseudo: String,email: String,password: String):
            this(-1,avatar,pseudo,email,password)
}