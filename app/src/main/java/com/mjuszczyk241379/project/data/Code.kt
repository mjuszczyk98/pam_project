package com.mjuszczyk241379.project.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Code(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "code") val code: String
)