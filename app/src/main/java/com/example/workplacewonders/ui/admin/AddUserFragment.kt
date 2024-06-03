package com.example.workplacewonders.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.workplacewonders.R

import com.example.workplacewonders.databinding.FragmentAddUserBinding
import com.example.workplacewonders.ui.viewmodel.AdminViewModel


class AddUserFragment : Fragment() {

    private lateinit var binding: FragmentAddUserBinding
    private val viewModel:AdminViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddUserBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val roles = resources.getStringArray(R.array.roles_array)
        val adapter = ArrayAdapter(requireContext(),R.layout.spinner_item,roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerRole.adapter = adapter


        binding.btnAddUser.setOnClickListener{
            binding.progressBar.visibility = View.VISIBLE
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val role = binding.spinnerRole.selectedItem.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && role.isNotEmpty()){
                viewModel.addUser(email,password,role){success->
                    binding.progressBar.visibility = View.GONE
                    if(success){
                        Toast.makeText(requireContext(),"User added successfully",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(requireContext(),"Cannot add user",Toast.LENGTH_SHORT).show()
                    }

                }
            }
            else{
                binding.progressBar.visibility = View.GONE
                binding.etEmail.error = "Required field"
                binding.etPassword.error = "Required field"

            }
        }

    }
}