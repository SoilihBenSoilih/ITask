package com.ss.itask.Model

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDateTime

data class Task(var id:Long, var name: String, var duration:Long,var date: String,var status:Int, val projectId: Long):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readLong()
    ) {
    }

    constructor(name: String, duration: Long, date: String, status: Int, projectId: Long):
            this(-1,name,duration,date,status,projectId)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeLong(duration)
        parcel.writeString(date)
        parcel.writeInt(status)
        parcel.writeLong(projectId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}