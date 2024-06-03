package com.example.workplacewonders.ui.admin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.workplacewonders.R
import com.example.workplacewonders.databinding.ActivityAdminBinding
import com.example.workplacewonders.ui.login.LoginActivity
import com.example.workplacewonders.ui.viewmodel.LoginViewModel

class AdminActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAdminBinding
    private lateinit var navController:NavController
    private val viewModel:LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()


    }

    private fun initialize() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


        binding.btnAddUser.setOnClickListener {
            navController.navigate(R.id.addUserFragment)
        }

        binding.btnAddAsset.setOnClickListener {
            navController.navigate(R.id.addAssetFragment)
        }
        binding.btnViewAsset.setOnClickListener {
            navController.navigate(R.id.viewAssetsFragment)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                viewModel.logout()
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}