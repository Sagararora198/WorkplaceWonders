package com.example.workplacewonders.ui.employee

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.workplacewonders.R
import com.example.workplacewonders.data.model.Review
import com.example.workplacewonders.databinding.FragmentEmployeeAssetDetail2Binding
import com.example.workplacewonders.ui.adapter.ReviewAdapter
import com.example.workplacewonders.ui.viewmodel.EmployeeViewModel
import com.example.workplacewonders.ui.viewmodel.SharedViewModel
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout


class EmployeeAssetDetailFragment : Fragment() {

    private val sharedViewModel:SharedViewModel by activityViewModels()
    private val viewModel:EmployeeViewModel by viewModels()
    private lateinit var reviewAdapter:ReviewAdapter

    private lateinit var binding:FragmentEmployeeAssetDetail2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEmployeeAssetDetail2Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.empProgressBar.visibility = View.VISIBLE
        val assetId = arguments?.getString("assetId")
        Log.d("assetid", "onViewCreated: $assetId")
        viewModel.fetchAssetById(assetId!!)
        viewModel.fetchReviews(assetId)
        sharedViewModel.userId.observe(viewLifecycleOwner){userId->
            val linearLayoutManager = ZoomRecyclerLayout(requireContext())
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            linearLayoutManager.reverseLayout = true
            linearLayoutManager.stackFromEnd = true
            binding.recyclerView.layoutManager = linearLayoutManager


            viewModel.asset.observe(viewLifecycleOwner) { asset ->
                Log.d("asset", "onViewCreated: $asset")

                binding.assetName.text = asset.name
                binding.assetDescription.text = asset.description
                Glide.with(binding.empimageview.context).load(asset.image).into(binding.empimageview)

            }

            viewModel.reviews.observe(viewLifecycleOwner) { reviews ->
                binding.empProgressBar.visibility = View.GONE
                reviewAdapter = ReviewAdapter(reviews)
                binding.recyclerView.adapter = reviewAdapter
            }
            binding.submitReviewButton.setOnClickListener {
                binding.empProgressBar.visibility = View.VISIBLE
                val rating = binding.ratingBar.rating
                val comment = binding.commentEditText.text.toString()
                val review = Review(userId, rating, comment)
                viewModel.addReview(assetId, review)
                binding.empProgressBar.visibility = View.GONE
                Toast.makeText(context, "Review added", Toast.LENGTH_SHORT).show()

            }
        }
    }
}