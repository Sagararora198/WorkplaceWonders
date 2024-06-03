package com.example.workplacewonders.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AuthService {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().reference

    fun getCurrentUserRole(callback:(String?)->Unit){
        val user = auth.currentUser
        user?.let {
            db.child("users").child(it.uid).get().addOnSuccessListener {snapshot->
                if(snapshot.exists()){
                    val role = snapshot.child("role").getValue(String::class.java)
                    callback(role)
                }
                else{
                    callback(null)
                }

            }
        }?:callback(null)
    }
}