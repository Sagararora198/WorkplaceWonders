package com.example.workplacewonders.data.firebase

import android.util.Log
import com.example.workplacewonders.data.model.Asset
import com.example.workplacewonders.data.model.Review
import com.google.firebase.database.FirebaseDatabase

class FirebaseSerivce {
    private val db = FirebaseDatabase.getInstance().reference

    fun addAsset(asset:Asset){
        val assetId = db.child("assets").push().key
        Log.d("assetId", "addAsset: $assetId")

        assetId?.let{
            asset.id = assetId
            db.child("assets").child(it).setValue(asset)
        }
    }

    fun getAsset(callback :(List<Asset>)->Unit){
        db.child("assets").get().addOnSuccessListener { result->
            val assets = result.children.map{
                it.getValue(Asset::class.java)!!
            }
            callback(assets)
        }
    }
    fun addReview(assetId: String, review: Review) {
        val reviewId = db.child("assets").child(assetId).child("reviews").push().key
        reviewId?.let {
            db.child("assets").child(assetId).child("reviews").child(it).setValue(review)
        }
    }



    fun getAssetById(assetId: String, callback: (Asset) -> Unit) {
        db.child("assets").child(assetId).get().addOnSuccessListener { result ->
            val asset = result.getValue(Asset::class.java)
            asset?.let { callback(it) }
        }
    }

    fun getReviews(assetId:String,callback: (List<Review>) -> Unit){
        db.child("assets").child(assetId).child("reviews").get().addOnSuccessListener { result ->
            val reviews = result.children.map {
                it.getValue(Review::class.java)!!
            }
            callback(reviews)

        }

    }


}
