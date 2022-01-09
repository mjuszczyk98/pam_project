package com.mjuszczyk241379.project.data

class PasswordRepository(private val passwordDao: PasswordDao) {

    val readAllPasswordBasic = passwordDao.readAllPasswordBasic()

    suspend fun readAll() = passwordDao.readAll()

    fun readPasswordById(id: Int) = passwordDao.readPasswordById(id)

    suspend fun insert(password: Password) = passwordDao.insert(password)

    suspend fun update(password: Password) = passwordDao.update(password)

    suspend fun delete(password: Password) = passwordDao.delete(password)
}