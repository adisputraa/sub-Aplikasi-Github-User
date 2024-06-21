package com.bangkit.mysubsgithub.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.mysubsgithub.data.response.user
import com.bangkit.mysubsgithub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class followingViewModel: ViewModel() {

    val listFollowing = MutableLiveData<ArrayList<user>>()

    val errorMessageLiveData = MutableLiveData<String>()

    fun setListFollowing(username: String){
        ApiConfig.getApiService()
            .getFollowing(username)
            .enqueue(object :Callback<ArrayList<user>>{
                override fun onResponse(
                    call: Call<ArrayList<user>>,
                    response: Response<ArrayList<user>>
                ) {
                    if (response.isSuccessful){
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<user>>, t: Throwable) {
                    val errorMessage = t.message ?: "Unknown error occurred"
                    errorMessageLiveData.postValue(errorMessage)
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<user>>{
        return listFollowing
    }
}