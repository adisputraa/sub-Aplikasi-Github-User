package com.bangkit.mysubsgithub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bangkit.mysubsgithub.R
import com.bangkit.mysubsgithub.SectionPagerAdapter
import com.bangkit.mysubsgithub.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)?: ""
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)?: ""
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        showLoading(true)
        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this){
            if (it != null){
                binding.apply {
                    tvName.text = getString(R.string.namePH, it.name)
                    tvUsername.text = getString(R.string.usernamePH, it.login)
                    tvFollowers.text = getString(R.string.followersPH, it.followers)
                    tvFollowing.text = getString(R.string.followingPH, it.following)
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(ivAvatar)
                }
                showLoading(false)
            }
        }

        viewModel.errorMessageLiveData.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0) {
                        binding.fab.isChecked = true
                        _isChecked = true
                    } else{
                        binding.fab.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.fab.setOnClickListener{
            _isChecked = !_isChecked
            if (_isChecked){
                viewModel.addTofavorite(username, id, avatarUrl)
            } else{
                viewModel.removeFromFavorite(id)
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabMode.setupWithViewPager(viewPager)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
    }
}