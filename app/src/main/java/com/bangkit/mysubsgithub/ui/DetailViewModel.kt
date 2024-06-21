package com.bangkit.mysubsgithub.ui

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.mysubsgithub.data.database.FavoriteDAO
import com.bangkit.mysubsgithub.data.database.FavoriteRoomDatabase
import com.bangkit.mysubsgithub.data.database.FavoriteUser
import com.bangkit.mysubsgithub.data.response.DetailUserResponse
import com.bangkit.mysubsgithub.data.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application: Application) : AndroidViewModel(application) {

    val user = MutableLiveData<DetailUserResponse>()

    val errorMessageLiveData = MutableLiveData<String>()

    private var userDao: FavoriteDAO?
    private var userDb: FavoriteRoomDatabase?

    init {
        userDb = FavoriteRoomDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        ApiConfig.getApiService()
            .getDetailUsers(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    val errorMessage = t.message ?: "Unknown error occurred"
                    errorMessageLiveData.postValue(errorMessage)
                }

            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user
    }

    fun addTofavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(id, username, avatarUrl)
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }

}