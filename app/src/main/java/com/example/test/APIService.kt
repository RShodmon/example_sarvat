package com.example.test

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface APIService {
    // GET METHOD
    @GET("/public/v2/posts")
    suspend fun getUsers(): Response<ResponseBody>

    // GET SIMPLE USER METHOD
    @GET("/public/v2/users/4641")
    suspend fun getShowUser(): Response<ResponseBody>

    // POST METHOD
    @Headers("Authorization: Bearer f74dfae196c0543536a7638b5da0e623ce80a351cada7f80ae0611c8b7fe6cca")
    @POST("/public/v2/users")
    suspend fun createUser(@Body requestBody: RequestBody): Response<ResponseBody>

    // PUT METHOD
    @Headers("Authorization: Bearer f74dfae196c0543536a7638b5da0e623ce80a351cada7f80ae0611c8b7fe6cca")
    @PUT("/public/v2/users/29820")
    suspend fun updateUser(@Body requestBody: RequestBody): Response<ResponseBody>

}