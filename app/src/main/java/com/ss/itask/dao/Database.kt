package com.ss.itask.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ss.itask.Model.Project
import com.ss.itask.Model.Task
import com.ss.itask.Model.User

private const val DATABASE_NAME = "itask.db"
private const val DATABASE_VERSION = 1

class Database (context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(UserDAO.USER_CREATE_TABLE)
        db?.execSQL(ProjectDAO.PROJECT_CREATE_TABLE)
        db?.execSQL(TaskDAO.USER_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun saveUser(values: ContentValues):Long{
        return writableDatabase.insert(UserDAO.USER_TABLE_NAME,null,values)
    }
    fun saveProject(values: ContentValues):Long{
            return writableDatabase.insert(ProjectDAO.PROJECT_TABLE_NAME,null,values)
    }
    fun  saveTask(values: ContentValues):Long{
        return writableDatabase.insert(TaskDAO.TASK_TABLE_NAME,null,values)
    }

    fun getAllUsers() : MutableList<User> {
        val users = mutableListOf<User>()
        readableDatabase.rawQuery(UserDAO.USER_QUERY_SELECT_ALL, null).use { cursor ->
            while (cursor.moveToNext()) {
                val user = User(
                    cursor.getLong(cursor.getColumnIndex(UserDAO.USER_KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(UserDAO.USER_KEY_AVATAR)),
                    cursor.getString(cursor.getColumnIndex(UserDAO.USER_KEY_PSEUDO)),
                    cursor.getString(cursor.getColumnIndex(UserDAO.USER_KEY_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(UserDAO.USER_KEY_PASSWORD))
                )
                users.add(user)
            }
        }

        return users
    }

    fun getAllProjects(): MutableList<Project>{
        val projects = mutableListOf<Project>()
        readableDatabase.rawQuery(ProjectDAO.PROJECT_QUERY_SELECT_ALL,null).use { cursor ->
            while (cursor.moveToNext()){
                val project = Project(
                    cursor.getLong(cursor.getColumnIndex(ProjectDAO.PROJECT_KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(ProjectDAO.PROJECT_KEY_NAME)),
                    cursor.getString(cursor.getColumnIndex(ProjectDAO.PROJECT_KEY_COLOR)),
                    cursor.getLong(cursor.getColumnIndex(ProjectDAO.PROJECT_KEY_USER_ID))
                )
                project.tasksList = getTasksByProject(project.id)
                projects.add(project)
            }
        }
        return projects
    }

    fun getAllTasks(): MutableList<Task>{
        val tasks = mutableListOf<Task>()
        readableDatabase.rawQuery(TaskDAO.TASK_QUERY_SELECT_ALL,null).use { cursor ->
            while (cursor.moveToNext()){
                val task =Task(
                    cursor.getLong(cursor.getColumnIndex(TaskDAO.TASK_KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(TaskDAO.TASK_KEY_NAME)),
                    cursor.getLong(cursor.getColumnIndex(TaskDAO.TASK_KEY_DURATION)),
                    cursor.getString(cursor.getColumnIndex(TaskDAO.TASK_KEY_DATE)),
                    cursor.getInt(cursor.getColumnIndex(TaskDAO.TASK_KEY_STATUS)),
                    cursor.getLong(cursor.getColumnIndex(TaskDAO.TASK_KEY_PROJECT_ID))
                )
                tasks.add(task)
            }
        }
        return  tasks
    }

     fun updateUSer(user: User) : Boolean {
        val values = UserDAO().createUser(user)
        val updateCount = writableDatabase.update(UserDAO.USER_TABLE_NAME,
            values,
            "${UserDAO.USER_KEY_ID} = ?",
            arrayOf("${user.id}"))
        return updateCount > 0
    }

    fun updateTask(task: Task) : Boolean {
        val values = TaskDAO().createTask(task)
        val updateCount = writableDatabase.update(TaskDAO.TASK_TABLE_NAME,
            values,
            "${TaskDAO.TASK_KEY_ID} = ?",
            arrayOf("${task.id}"))
        return updateCount > 0
    }

     fun getTasksByProject(projectId: Long):MutableList<Task>{
        val tasks = mutableListOf<Task>()
        readableDatabase.rawQuery(TaskDAO.TASK_QUERY_SELECT_BY_PROJECT+projectId,null).use { cursor ->
            while (cursor.moveToNext()){
                val task =Task(
                    cursor.getLong(cursor.getColumnIndex(TaskDAO.TASK_KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(TaskDAO.TASK_KEY_NAME)),
                    cursor.getLong(cursor.getColumnIndex(TaskDAO.TASK_KEY_DURATION)),
                    cursor.getString(cursor.getColumnIndex(TaskDAO.TASK_KEY_DATE)),
                    cursor.getInt(cursor.getColumnIndex(TaskDAO.TASK_KEY_STATUS)),
                    cursor.getLong(cursor.getColumnIndex(TaskDAO.TASK_KEY_PROJECT_ID))
                )
                tasks.add(task)
            }
        }
        return  tasks
    }
}