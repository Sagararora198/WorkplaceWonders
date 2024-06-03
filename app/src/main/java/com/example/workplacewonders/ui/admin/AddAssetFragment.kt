package com.example.workplacewonders.ui.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.workplacewonders.R
import com.example.workplacewonders.data.model.Asset
import com.example.workplacewonders.databinding.FragmentAddAssetBinding
import com.example.workplacewonders.ui.viewmodel.AdminViewModel
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID


class AddAssetFragment : Fragment() {
    private lateinit var binding:FragmentAddAssetBinding
    private val viewModel:AdminViewModel by viewModels()
    private var selectedImageUri:Uri? = null
    private var storageReference = FirebaseStorage.getInstance().reference



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddAssetBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {

        binding.btnSelectImage.setOnClickListener {
            selectImage()
        }
        binding.btnAddAsset.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val name = binding.etAssetName.text.toString()
            val description = binding.etAssetDescription.text.toString()
            if(selectedImageUri!=null){
                uploadImageToFirebase(name,description)
            }
            else{
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(),"Please upload image",Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            selectedImageUri = data?.data
            binding.ivAssetImage.setImageURI(selectedImageUri)
        }
    }
    private fun uploadImageToFirebase(name:String,description:String){
        selectedImageUri?.let {uri->
            val fileName = UUID.randomUUID().toString()
            val ref = storageReference.child("assets/$fileName")
            ref.putFile(uri)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {downloadedUri->
                        val asset = Asset(name=name, description = description, image = downloadedUri.toString())
                        viewModel.addAsset(asset)
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(),"Asset added successfully",Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    companion object{
        private const val IMAGE_PICK_CODE = 1000
    }
}