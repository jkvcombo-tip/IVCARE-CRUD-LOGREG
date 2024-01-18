package com.example.ivcare.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.ivcare.userdatabase.UserEntity
import com.example.ivcare.userdatabase.UserRepository

class ViewModelUser : ViewModel() {

    var liveDataLogin: LiveData<UserEntity>? = null

    fun insertData(context: Context, username: String, password: String) {
        UserRepository.insertData(context, username, password)
    }

    fun getLoginDetails(context: Context, username: String, password: String): LiveData<UserEntity>? {
        liveDataLogin = UserRepository.getLoginDetails(context, username, password)
        return liveDataLogin
    }


}
