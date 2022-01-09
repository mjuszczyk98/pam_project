package com.mjuszczyk241379.project.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "password")
data class Password (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "login") val login: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "lastEditDate") val lastEditDate: Long?,
    @ColumnInfo(name = "mongoId") val _id: String?
)