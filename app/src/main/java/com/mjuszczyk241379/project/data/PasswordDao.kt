package com.mjuszczyk241379.project.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PasswordDao {

    @Query("SELECT id, name FROM password")
    fun readAllPasswordBasic(): LiveData<List<Password>>

    @Query("SELECT * FROM password WHERE id=:passwordId")
    fun readPasswordById(passwordId: Int): LiveData<Password>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg password: Password)

    @Update
    suspend fun update(password: Password)

    @Delete
    suspend fun delete(password: Password)
}