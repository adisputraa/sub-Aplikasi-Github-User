package com.bangkit.mysubsgithub.data.retrofit


import com.bangkit.mysubsgithub.data.response.DetailUserResponse
import com.bangkit.mysubsgithub.data.response.UserResponse
import com.bangkit.mysubsgithub.data.response.user
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getSearchUsers(@Query("q") q :String): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUsers(@Path("username") username :String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username :String): Call<ArrayList<user>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username :String): Call<ArrayList<user>>
}