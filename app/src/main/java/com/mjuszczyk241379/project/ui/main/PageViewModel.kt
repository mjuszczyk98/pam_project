package com.mjuszczyk241379.project.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.mjuszczyk241379.project.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PageViewModel(application: Application) : AndroidViewModel(application) {

    val readAllPasswordBasic: LiveData<List<Password>>
    val lastSyncDate: LiveData<SyncDate>
    private val passwordRepository: PasswordRepository
    private val syncDateRepository: SyncDateRepository
    private val _syncPasswordManager: SyncPasswordsManager

    init {
        val database = AppDatabase.getDatabase(application)
        val passwordDao = database.passwordDao()
        val syncDateDao = database.syncDateDao()
        passwordRepository = PasswordRepository(passwordDao)
        syncDateRepository = SyncDateRepository(syncDateDao)
        readAllPasswordBasic = passwordRepository.readAllPasswordBasic
        lastSyncDate = syncDateRepository.getLatestUpdate()
        _syncPasswordManager = SyncPasswordsManager(passwordRepository, syncDateRepository)
    }

    fun readPasswordById(id: Int): LiveData<Password> {
        if (passwordRepository == null) {
            return MutableLiveData()
        }
        return passwordRepository.readPasswordById(id)
    }

    fun insert(password: Password) {
        viewModelScope.launch(Dispatchers.IO) {
            passwordRepository.insert(password)
        }
    }

    fun update(password: Password) {
        viewModelScope.launch(Dispatchers.IO) {
            passwordRepository.update(password)
        }
    }

    fun delete(password: Password) {
        viewModelScope.launch(Dispatchers.IO) {
            passwordRepository.delete(password)
        }
    }

    fun syncPasswords(lastSyncDate: SyncDate){
        viewModelScope.launch(Dispatchers.IO) {
            _syncPasswordManager.syncPasswords(lastSyncDate.syncDate!!)
        }
    }
}