package com.mobile.foodreceipe.singletons

sealed class NetworkResult<T> (
    val data:T?=null,
    val message:String?=null) {

    class Success<T>(data:T):NetworkResult<T>(data)
    class Error<T>(data: T?=null,message:String?):NetworkResult<T>(data = data,message)
    class Loading<T>:NetworkResult<T>()

}