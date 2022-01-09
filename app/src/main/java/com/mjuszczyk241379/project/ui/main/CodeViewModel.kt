package com.mjuszczyk241379.project.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.mjuszczyk241379.project.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CodeViewModel(application: Application) : AndroidViewModel(application) {

    private val codeRepository: CodeRepository =
        CodeRepository(AppDatabase.getDatabase(application).codeDao())
    val code: LiveData<Code> = codeRepository.getCode

    fun insert(code: Code) {
        viewModelScope.launch(Dispatchers.IO) {
            codeRepository.insert(code)
        }
    }

    fun update(code: Code) {
        viewModelScope.launch(Dispatchers.IO) {
            codeRepository.update(code)
        }
    }
}