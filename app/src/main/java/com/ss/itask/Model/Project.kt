package com.ss.itask.Model

import android.os.Parcel
import android.os.Parcelable

data class Project(var id:Long, var name: String, var color:String, var userId: Long): Parcelable{
    lateinit var tasksList: MutableList<Task>

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong()
    ) {

    }

    constructor(name: String,color: String,userId: Long):
            this(-1,name,color,userId)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(color)
        parcel.writeLong(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Project> {
        override fun createFromParcel(parcel: Parcel): Project {
            return Project(parcel)
        }

        override fun newArray(size: Int): Array<Project?> {
            return arrayOfNulls(size)
        }
    }
}
