package com.example.workplacewonders.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.workplacewonders.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo:AuthRepository
):ViewModel() {


    fun login(email:String,password:String,callback:(Boolean,String?)->Unit){
        authRepo.login(email,password){success,role ->
            callback(success,role)
        }
    }
    fun logout(){
        authRepo.logout()
    }

}