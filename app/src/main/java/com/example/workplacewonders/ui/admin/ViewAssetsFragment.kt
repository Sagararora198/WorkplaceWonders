package com.example.workplacewonders.ui.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workplacewonders.R
import com.example.workplacewonders.databinding.FragmentViewAssetsBinding
import com.example.workplacewonders.ui.adapter.AssetAdapter
import com.example.workplacewonders.ui.viewmodel.AdminViewModel
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout


class ViewAssetsFragment : Fragment() {


    private lateinit var binding:FragmentViewAssetsBinding
    private val viewModel:AdminViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentViewAssetsBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progress.visibility = View.VISIBLE
        val linearLayoutManager = ZoomRecyclerLayout(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        binding.recyclerView.layoutManager = linearLayoutManager
        viewModel.assets.observe(viewLifecycleOwner){ asserts->
            binding.progress.visibility = View.GONE
            binding.recyclerView.adapter = AssetAdapter(asserts){
                asset->
                val bundle = Bundle().apply {
                    Log.d("Sending asset", "onViewCreated: $asset")
                    putString("assetId", asset.id)
                }
                findNavController().navigate(R.id.action_viewAssetsFragment_to_assetDetailFragment, bundle)

            }
        }
        viewModel.fetchAssets()

    }
}