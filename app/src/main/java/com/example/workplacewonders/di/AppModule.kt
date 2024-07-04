package com.example.workplacewonders.di

import com.example.workplacewonders.data.firebase.FirebaseSerivce
import com.example.workplacewonders.data.repository.AssetNReviewRepository
import com.example.workplacewonders.data.repository.AuthRepository
import com.example.workplacewonders.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideFireBaseService():FirebaseSerivce{
        return FirebaseSerivce()
    }

    @Provides
    @Singleton
    fun provideRepository(firebaseSerivce: FirebaseSerivce):AssetNReviewRepository{
        return AssetNReviewRepository(firebaseSerivce)
    }

    @Provides
    @Singleton
    fun provideUserRepository():UserRepository{
        return UserRepository()
    }

    @Provides
    @Singleton
    fun provideAuthRepo():AuthRepository{
        return AuthRepository()
    }
}