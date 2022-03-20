package com.mobile.foodreceipe.repository

import com.mobile.foodreceipe.datasource.LocalDataSource
import com.mobile.foodreceipe.datasource.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class FoodReceipeRepository @Inject constructor( private val remoteDataSource: RemoteDataSource,
 private val localDataSource: LocalDataSource) {
    val remote=remoteDataSource
    val local=localDataSource

}