package com.example.kotlindemo

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ImageService {
    @GET("https://www.baidu.com")
    fun getContent(): Call<String>

}