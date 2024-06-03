package com.example.workplacewonders.ui.employee

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workplacewonders.R
import com.example.workplacewonders.databinding.FragmentEmployeeViewAsset2Binding
import com.example.workplacewonders.ui.adapter.AssetAdapter
import com.example.workplacewonders.ui.viewmodel.EmployeeViewModel
import com.example.workplacewonders.ui.viewmodel.SharedViewModel
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout


class EmployeeViewAssetFragment : Fragment() {

    private lateinit var binding:FragmentEmployeeViewAsset2Binding
    private var userId:String? = null
    private val viewModel:EmployeeViewModel by viewModels()
    private val sharedViewModel:SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEmployeeViewAsset2Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.empProgressbar.visibility = View.VISIBLE
        viewModel.fetchAssets()
        sharedViewModel.userId.observe(viewLifecycleOwner) { userId ->
            Log.d("EmployeeViewAssetFragment", "Received userId: $userId")
            val linearLayoutManager = ZoomRecyclerLayout(requireContext())
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            linearLayoutManager.reverseLayout = true
            linearLayoutManager.stackFromEnd = true
            binding.recyclerView.layoutManager = linearLayoutManager
            viewModel.assets.observe(viewLifecycleOwner) { assets ->
                binding.empProgressbar.visibility = View.GONE
                binding.recyclerView.adapter = AssetAdapter(assets) { asset ->
                    val bundle = Bundle().apply {
                        putString("assetId", asset.id)
                    }
                    findNavController().navigate(R.id.action_employeeViewAssetFragment_to_employeeAssetDetailFragment, bundle)
                }
            }

        }

    }
}