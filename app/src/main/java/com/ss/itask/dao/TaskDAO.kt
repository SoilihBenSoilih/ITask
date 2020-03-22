package com.ss.itask.dao

import android.content.ContentValues
import com.ss.itask.Model.Task

class TaskDAO {
    companion object{
        const val TASK_TABLE_NAME ="tasks"
        const val TASK_KEY_ID = "id"
        const val TASK_KEY_NAME = "name"
        const val TASK_KEY_DURATION = "duration"
        const val TASK_KEY_DATE = "date"
        const val TASK_KEY_PROJECT_ID = "projectid"
        const val USER_CREATE_TABLE = "CREATE TABLE $TASK_TABLE_NAME($TASK_KEY_ID " +
                "INTEGER PRIMARY KEY, $TASK_KEY_NAME TEXT, $TASK_KEY_DURATION INTEGER," +
                " $TASK_KEY_DATE DATE,, $TASK_KEY_PROJECT_ID INTEGER)"
        const val TASK_QUERY_SELECT_ALL = "SELECT * FROM $TASK_TABLE_NAME"
        const val TASK_QUERY_SELECT_BY_PROJECT = "SELECT * FROM $TASK_TABLE_NAME WHERE $TASK_KEY_PROJECT_ID = "
    }

    fun createTask(task: Task):ContentValues{
        val values = ContentValues()
        values.put(TASK_KEY_NAME, task.name)
        values.put(TASK_KEY_DURATION,task.duration)
        values.put(TASK_KEY_DATE,task.date)
        values.put(TASK_KEY_PROJECT_ID,task.projectId)
        return values
    }
}