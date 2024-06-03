package com.example.workplacewonders.data.repository


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.log

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().reference

    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("login", "login: login successfull")
                val user = auth.currentUser
                Log.d("User info", "login: $user")
                user?.let {
                    Log.d("uid of user", "login: ${it.uid}")
                    db.child("users").child(it.uid).get().addOnSuccessListener { snapshot ->
                        if (snapshot.exists()) {
                            val role = snapshot.child("role").getValue(String::class.java)
                            callback(true, role)
                        } else {
                            callback(false, null)
                        }
                    }
                } ?: callback(false, null)
            } else {
                callback(false, null)
                Log.d("login failed", "login: Incorrecrt")
            }
        }
    }
    fun logout(){
        auth.signOut()
    }
}