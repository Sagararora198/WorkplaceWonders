package com.example.workplacewonders.data.repository


import com.example.workplacewonders.data.firebase.FirebaseSerivce
import com.example.workplacewonders.data.model.Asset
import com.example.workplacewonders.data.model.Review

class AssetNReviewRepository (){
    private val firebaseService:FirebaseSerivce = FirebaseSerivce()
    fun addAsset(asset:Asset){
        firebaseService.addAsset(asset)
    }
    fun getAssets(callback:(List<Asset>)->Unit ){
        firebaseService.getAsset(callback)
    }
    fun addReview(assetId:String,review:Review){
        firebaseService.addReview(assetId,review)
    }
    fun getReviews(assetId:String,callback: (List<Review>) -> Unit){
        firebaseService.getReviews(assetId,callback)
    }
    fun getAssetById(assetId:String,callback: (Asset) -> Unit){
        firebaseService.getAssetById(assetId,callback)
    }

}