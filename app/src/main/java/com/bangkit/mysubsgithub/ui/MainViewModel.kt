package com.bangkit.mysubsgithub.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.mysubsgithub.SettingPreferences
import com.bangkit.mysubsgithub.data.response.UserResponse
import com.bangkit.mysubsgithub.data.response.user
import com.bangkit.mysubsgithub.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<user>>()

    fun setSearch(query: String) {
        ApiConfig.getApiService().getSearchUsers(query).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    listUsers.postValue((response.body()?.items))
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("Failure", t.message ?: "Unknown error occurred")
            }
        })
    }

    fun getSearch(): LiveData<ArrayList<user>>{
        return listUsers
    }
    fun getThemeSetting(): LiveData<Boolean> {return pref.getThemeSetting().asLiveData()}
}

