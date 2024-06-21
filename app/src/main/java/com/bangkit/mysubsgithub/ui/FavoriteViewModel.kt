package com.bangkit.mysubsgithub.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.bangkit.mysubsgithub.data.database.FavoriteDAO
import com.bangkit.mysubsgithub.data.database.FavoriteRoomDatabase
import com.bangkit.mysubsgithub.data.database.FavoriteUser

class FavoriteViewModel (application: Application) : AndroidViewModel(application) {

    private var userDao: FavoriteDAO?
    private var userDb: FavoriteRoomDatabase?

    init {
        userDb = FavoriteRoomDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUser()
    }
}