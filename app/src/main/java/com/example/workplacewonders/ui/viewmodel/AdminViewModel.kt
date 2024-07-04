package com.example.workplacewonders.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workplacewonders.data.model.Asset
import com.example.workplacewonders.data.model.Review
import com.example.workplacewonders.data.repository.AssetNReviewRepository
import com.example.workplacewonders.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: AssetNReviewRepository,
    private val repo:UserRepository
):ViewModel() {


    private val _assets = MutableLiveData<List<Asset>>()
    val assets:LiveData<List<Asset>> = _assets

    private val _asset = MutableLiveData<Asset>()
    val asset:LiveData<Asset> = _asset

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews:LiveData<List<Review>> = _reviews


    fun addAsset(asset: Asset){
        repository.addAsset(asset)
    }

    fun fetchAssets(){
        repository.getAssets {
            _assets.value = it
        }
    }

    fun fetchReviews(assetId:String){
        repository.getReviews(assetId){
            _reviews.value = it
        }

    }
    fun fetchAssetById(assetId:String){
        repository.getAssetById(assetId){
            _asset.value = it
        }
    }
    fun addUser(email:String,password:String,role:String,callback:(Boolean)->Unit){
        repo.addUser(email,password,role,callback)
    }
}