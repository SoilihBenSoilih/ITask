package com.ss.itask.Model

import java.time.LocalDateTime

data class Task(var id:Long, var name: String, var duration:Long,val date: String, val projectId: Long) {
    constructor(name: String,duration: Long, date: String,projectId: Long):
            this(-1,name,duration,date,projectId)
}