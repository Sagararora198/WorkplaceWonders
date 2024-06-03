// AssetDetailFragment.kt
package com.example.workplacewonders.ui.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.workplacewonders.databinding.FragmentAssetDetailBinding
import com.example.workplacewonders.ui.adapter.ReviewAdapter
import com.example.workplacewonders.ui.viewmodel.AdminViewModel
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

class AssetDetailFragment : Fragment() {

    private lateinit var binding: FragmentAssetDetailBinding
    private val viewModel: AdminViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAssetDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.assetProgreessbar.visibility = View.VISIBLE

        val assetId = arguments?.getString("assetId") ?: return
        Log.d("asset", "onViewCreated: $assetId")
        val linearLayoutManager = ZoomRecyclerLayout(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        binding.recyclerView.layoutManager =linearLayoutManager
        viewModel.fetchAssetById(assetId)
        viewModel.fetchReviews(assetId)

        viewModel.asset.observe(viewLifecycleOwner) { asset ->

            Log.d("asset", "onViewCreated: $asset")
            binding.assetName.text = asset.name
            binding.assetDescription.text = asset.description
            Glide.with(binding.assetImage.context).load(asset.image).into(binding.assetImage)

        }

        viewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            binding.assetProgreessbar.visibility = View.GONE
            binding.recyclerView.adapter = ReviewAdapter(reviews)
        }


    }
}