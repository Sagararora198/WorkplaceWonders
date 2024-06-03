package com.example.workplacewonders.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.workplacewonders.data.model.Asset
import com.example.workplacewonders.databinding.ItemassetBinding


class AssetAdapter(private val assets: List<Asset>, private val onAssetClick: (Asset) -> Unit) : RecyclerView.Adapter<AssetAdapter.AssetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val binding = ItemassetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AssetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        holder.bind(assets[position])
    }

    override fun getItemCount(): Int = assets.size

    inner class AssetViewHolder(private val binding: ItemassetBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(asset: Asset) {
            binding.assetName.text = asset.name
            binding.assetDescription.text = asset.description
            Glide.with(binding.assetImage.context).load(asset.image).into(binding.assetImage)
            binding.root.setOnClickListener { onAssetClick(asset) }
        }
    }
}