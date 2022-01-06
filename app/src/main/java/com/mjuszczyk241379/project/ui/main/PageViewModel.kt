package com.mjuszczyk241379.project.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.mjuszczyk241379.project.data.AppDatabase
import com.mjuszczyk241379.project.data.AppRepository
import com.mjuszczyk241379.project.data.Password
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PageViewModel(application: Application) : AndroidViewModel(application) {

    val readAllPasswordBasic: LiveData<List<Password>>
    private val _repository: AppRepository

    init {
        val passwordDao = AppDatabase.getDatabase(application).passwordDao()
        _repository = AppRepository(passwordDao)
        readAllPasswordBasic = _repository.readAllPasswordBasic
    }

    fun readPasswordById(id: Int): LiveData<Password> {
        if (_repository == null) {
            return MutableLiveData()
        }
        return _repository.readPasswordById(id)
    }

    fun insert(password: Password) {
        viewModelScope.launch(Dispatchers.IO) {
            _repository.insert(password)
        }
    }

    fun update(password: Password) {
        viewModelScope.launch(Dispatchers.IO) {
            _repository.update(password)
        }
    }

    fun delete(password: Password) {
        viewModelScope.launch(Dispatchers.IO) {
            _repository.delete(password)
        }
    }
}