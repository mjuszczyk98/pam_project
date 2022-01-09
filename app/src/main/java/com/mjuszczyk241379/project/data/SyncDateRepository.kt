package com.mjuszczyk241379.project.data

class SyncDateRepository(private val syncDateDao: SyncDateDao) {
    fun getLatestUpdate() = syncDateDao.getLatestUpdate()

    suspend fun syncDateInsert(syncDate: SyncDate) = syncDateDao.insert(syncDate)

}