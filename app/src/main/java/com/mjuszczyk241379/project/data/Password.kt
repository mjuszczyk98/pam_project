package com.mjuszczyk241379.project.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password")
data class Password (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "login") val login: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "note") val note: String?
)