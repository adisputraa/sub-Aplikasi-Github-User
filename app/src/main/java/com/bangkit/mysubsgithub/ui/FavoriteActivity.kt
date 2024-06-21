package com.bangkit.mysubsgithub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.mysubsgithub.UserAdapter
import com.bangkit.mysubsgithub.data.database.FavoriteUser
import com.bangkit.mysubsgithub.data.response.user
import com.bangkit.mysubsgithub.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: user) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvFavorit.setHasFixedSize(true)
            rvFavorit.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorit.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this){
            if (it!=null){
                val list = mapList(it)
                adapter.setUserList(list)
            }
        }
    }

    private fun mapList(usersFav: List<FavoriteUser>): ArrayList<user> {
        val listUser = ArrayList<user>()
        for (user in usersFav){
            val userMapped = user(user.login, user.id, user.avatar_url)
            listUser.add(userMapped)
        }
        return listUser
    }
}