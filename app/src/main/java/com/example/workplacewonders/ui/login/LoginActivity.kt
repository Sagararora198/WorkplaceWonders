package com.example.workplacewonders.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.workplacewonders.R
import com.example.workplacewonders.databinding.ActivityLoginBinding
import com.example.workplacewonders.ui.admin.AdminActivity
import com.example.workplacewonders.ui.employee.EmployeeActivity
import com.example.workplacewonders.ui.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private val viewModel:LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initializeView()


    }

    private fun initializeView() {
        binding.btnLogin.setOnClickListener{
            binding.progressbar.visibility = View.VISIBLE
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            Log.d("user data", "initializeView: $email , $password")
            if(email.isNotEmpty() && password.isNotEmpty()){

                viewModel.login(email,password){success,role->
                    binding.progressbar.visibility = View.GONE
                    if(success){

                        when(role){
                            "admin"-> startActivity(Intent(this,AdminActivity::class.java))
                            "employee"->{
                                val intent = Intent(this, EmployeeActivity::class.java)
                                intent.putExtra("userId", email)
                                startActivity(intent)
                                }


                            else->Toast.makeText(this,"Unknown role",Toast.LENGTH_SHORT).show()
                        }
                        finish()
                    }
                    else{
                        Toast.makeText(this,"Login failed",Toast.LENGTH_LONG).show()
                    }
                }
            }
            else{
                binding.etEmail.error = "Please fill the field"
                binding.etPassword.error = "Please fill the field"
                Toast.makeText(this,"Fill all the fields",Toast.LENGTH_SHORT).show()
            }
        }
    }


}