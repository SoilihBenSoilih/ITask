package com.ss.itask.Model

import java.time.LocalDateTime

data class Task(var id:Long, var name: String, var duration:Long,val date: String,var status:Int, val projectId: Long) {
    constructor(name: String,duration: Long, date: String,status: Int,projectId: Long):
            this(-1,name,duration,date,status,projectId)
}