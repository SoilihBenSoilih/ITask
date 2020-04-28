package com.ss.itask.Model

import android.os.Parcel
import android.os.Parcelable

data class User(var id:Long, var avatar: String, var pseudo: String, var email:String, var password:String): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    constructor(avatar: String, pseudo: String, email: String, password: String):
            this(-1,avatar,pseudo,email,password)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(avatar)
        parcel.writeString(pseudo)
        parcel.writeString(email)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}