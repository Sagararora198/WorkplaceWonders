package com.example.workplacewonders.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workplacewonders.data.model.Asset
import com.example.workplacewonders.data.model.Review
import com.example.workplacewonders.data.repository.AssetNReviewRepository

class EmployeeViewModel:ViewModel() {
    private val repository: AssetNReviewRepository = AssetNReviewRepository()


    private val _assets = MutableLiveData<List<Asset>>()
    val assets:LiveData<List<Asset>> = _assets

    private val _asset = MutableLiveData<Asset>()
    val asset:LiveData<Asset> = _asset

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews:LiveData<List<Review>> = _reviews


    fun fetchAssets(){
        repository.getAssets {
            _assets.value = it
        }
    }

    fun addReview(assetId:String,review: Review){
        repository.addReview(assetId,review)

        val updatedReviews = _reviews.value?.toMutableList() ?: mutableListOf()
        updatedReviews.add(review)
        _reviews.value = updatedReviews

    }

    fun fetchReviews(assetId: String){
        repository.getReviews(assetId){
            _reviews.value = it
        }
    }
    fun fetchAssetById(assetId:String){
        repository.getAssetById(assetId){
            _asset.value = it
        }
    }

}