package com.example.ivcare.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ivcare.uzerdatabase.UzerRepository
import java.lang.IllegalArgumentException

class UzerViewModelFactory(private val repository: UzerRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UzerViewModel::class.java)) {
            return UzerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}