package com.example.workplacewonders.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().reference

    fun addUser(email:String,password:String,role:String,callback:(Boolean)->Unit){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                val user = auth.currentUser
                user?.let {
                    val userData = mapOf("email" to email,"role" to role)
                    db.child("users").child(it.uid).setValue(userData).addOnCompleteListener {task->
                        callback(task.isSuccessful)
                    }
                }
                        ?:callback(false)
            }
            else{
                callback(false)
            }
        }
    }
}