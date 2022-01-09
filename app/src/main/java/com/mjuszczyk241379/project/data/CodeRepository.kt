package com.mjuszczyk241379.project.data

class CodeRepository(private val codeDao: CodeDao) {
    val getCode = codeDao.getCode()

    suspend fun insert(code: Code) = codeDao.insert(code)

    suspend fun update(code: Code) = codeDao.update(code)

}