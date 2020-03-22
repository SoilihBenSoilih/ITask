package com.ss.itask.Model

data class Project(var id:Long, var name:String,var color:String, var userId: Long){
    lateinit var tasksList: MutableList<Task>
    constructor(name: String,color: String,userId: Long):
            this(-1,name,color,userId)
}
