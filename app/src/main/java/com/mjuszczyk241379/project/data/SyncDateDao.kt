package com.mjuszczyk241379.project.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SyncDateDao {

    @Query("SELECT * FROM syncDate ORDER BY id DESC LIMIT 1")
    fun getLatestUpdate(): LiveData<SyncDate>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg syncDate: SyncDate)
}