package com.example.ivcare.display

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ivcare.uzerdatabase.Uzer
import com.example.ivcare.uzerdatabase.UzerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//userviewmodel has reference to user repo as constructor parameter
class UzerViewModel(private val repository: UzerRepository) : ViewModel() {

    val uzers = repository.uzers
    private var isUpdateOrDelete = false
    private lateinit var uzerToUpdateOrDelete : Uzer

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val inputStatus = MutableLiveData<String>()
    val inputRole = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate(){

        if (inputName.value == null) {
            statusMessage.value = Event("Please enter your name")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter your company email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter a correct email address")
        } else if (inputStatus.value == null) {
            statusMessage.value = Event("Please enter your current status")
        } else if (inputRole.value == null) {
            statusMessage.value = Event("Please enter your company role")
        } else {
            if(isUpdateOrDelete){
                uzerToUpdateOrDelete.name = inputName.value!!
                uzerToUpdateOrDelete.email = inputEmail.value!!
                update(uzerToUpdateOrDelete)
            }else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                val status = inputStatus.value!!
                val role = inputRole.value!!
                insert(Uzer(0, name, email, status, role))
                inputName.value = ""
                inputEmail.value = ""
                inputStatus.value = ""
                inputRole.value = ""
            }
        }
    }

    fun clearAllOrDelete(){
        if(isUpdateOrDelete){
            delete(uzerToUpdateOrDelete)
        }else {
            clearAll()
        }

    }
    private fun insert(uzer: Uzer) = viewModelScope.launch(Dispatchers.IO) {
        val newRowId = repository.insert(uzer)
        withContext(Dispatchers.Main){
            if(newRowId > -1) {
                statusMessage.value = Event("User Inserted Successfully! $newRowId")
            }else{
                statusMessage.value = Event("Error Occurred!")
            }
        }
    }


    private fun update(uzer: Uzer) = viewModelScope.launch(Dispatchers.IO) {
        val numberOfRows = repository.update(uzer)
        withContext(Dispatchers.Main){
            if(numberOfRows > 0) {
                inputName.value = ""
                inputEmail.value = ""
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"
                statusMessage.value = Event("$numberOfRows Rows Updated Successfully!")
            }else{
                statusMessage.value = Event("Error Occurred!")
            }
        }
    }

    private fun delete(uzer: Uzer) = viewModelScope.launch(Dispatchers.IO) {
        val numberOfRowsDeleted = repository.delete(uzer)
        withContext(Dispatchers.Main){
            if(numberOfRowsDeleted > 0) {
                inputName.value = ""
                inputEmail.value = ""
                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"
                statusMessage.value = Event("$numberOfRowsDeleted Rows Deleted Successfully!")
            } else {
                statusMessage.value = Event("Error Occurred!")
            }
        }
    }

    private fun clearAll() = viewModelScope.launch(Dispatchers.IO) {
        val numberOfRowsDeleted = repository.deleteAll()
        withContext(Dispatchers.Main){
            if(numberOfRowsDeleted > 0) {
                statusMessage.value = Event("$numberOfRowsDeleted Rows Deleted Successfully!")
            }else{
                statusMessage.value = Event("Error Occurred!")
            }
        }
    }

    fun initUpdateAndDelete(uzer: Uzer){
        inputName.value = uzer.name
        inputEmail.value = uzer.email
        inputStatus.value = uzer.status
        inputRole.value = uzer.role
        isUpdateOrDelete = true
        uzerToUpdateOrDelete = uzer
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

}