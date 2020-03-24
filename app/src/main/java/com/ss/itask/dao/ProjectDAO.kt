package com.ss.itask.dao

import android.content.ContentValues
import com.ss.itask.Model.Project

class ProjectDAO {
    companion object{
        const val  PROJECT_TABLE_NAME = "projects"
        const val PROJECT_KEY_ID = "id"
        const val PROJECT_KEY_NAME = "name"
        const val PROJECT_KEY_USER_ID = "userid"
        const val PROJECT_KEY_COLOR = "color"
        const val PROJECT_CREATE_TABLE = "CREATE TABLE $PROJECT_TABLE_NAME ($PROJECT_KEY_ID INTEGER PRIMARY KEY," +
                "$PROJECT_KEY_NAME TEXT, $PROJECT_KEY_COLOR TEXT, $PROJECT_KEY_USER_ID INTEGER)"
        const val PROJECT_QUERY_SELECT_ALL = "SELECT * FROM $PROJECT_TABLE_NAME"
    }

    fun createProject(project: Project):ContentValues{
        val values = ContentValues()
        values.put(PROJECT_KEY_NAME,project.name)
        values.put(PROJECT_KEY_COLOR,project.color)
        values.put(PROJECT_KEY_USER_ID,project.userId)
        return values
    }
}