package com.mjuszczyk241379.project.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CodeDao {

    @Query("SELECT * FROM code WHERE id=1 LIMIT 1")
    fun getCode(): LiveData<Code>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg code: Code)

    @Update
    suspend fun update(vararg code: Code)
}