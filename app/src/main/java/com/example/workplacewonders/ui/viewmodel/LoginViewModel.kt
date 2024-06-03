package com.example.workplacewonders.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.workplacewonders.data.repository.AuthRepository

class LoginViewModel():ViewModel() {
    private val authRepo:AuthRepository = AuthRepository()

    fun login(email:String,password:String,callback:(Boolean,String?)->Unit){
        authRepo.login(email,password){success,role ->
            callback(success,role)
        }
    }
    fun logout(){
        authRepo.logout()
    }

}