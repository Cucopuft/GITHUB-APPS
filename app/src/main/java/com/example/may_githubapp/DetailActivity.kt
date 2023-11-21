package com.example.may_githubapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.may_githubapp.Adapter.ViewPagerAdapter
import com.example.may_githubapp.DarkMode.DarkModeActivity
import com.example.may_githubapp.ViewModel.DetailViewModel
import com.example.may_githubapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailviewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail Activity"

        val tabLayout = findViewById<TabLayout>(R.id.tabs_layout_detail)
        val viewPager2= findViewById<ViewPager2>(R.id.viewpager_layout_detail)
        val adapter= ViewPagerAdapter(supportFragmentManager,lifecycle, data = Bundle())

        viewPager2.adapter=adapter
        TabLayoutMediator(tabLayout,viewPager2){tab,position->
            when(position){
                0->{
                    tab.text="Follower"
                }
                1->{
                    tab.text="Following"
                }
            }
        }.attach()

        val username = intent.getStringExtra(DETAIL_USER)
        val id = intent.getIntExtra(DETAIL_ID, 0)
        val avatarUrl =intent.getStringExtra(DETAIL_AVATAR)

        val bundle = Bundle()
        bundle.putString(DETAIL_USER, username)

        detailviewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        if (username != null) {
            detailviewModel.setDataUserDetail(username)
        }
        showLoading(true)
        detailviewModel.getDataUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    UserName.text = it.name
                    DetailLogin.text = it.login
                    Id.text = it.id.toString()
                    LocationDetail.text = it.location ?: "Not Found"
                    CompanyDetail.text = it.company ?: "Not Found"
                    Follower.text = it.followers.toString() ?: "Not Found"
                    Following.text = it.following.toString() ?: "Not Found"
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .circleCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageView)
                    showLoading(false)
                }
            }
        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailviewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleButton.isChecked = true
                        _isChecked = true
                    }
                    else {
                        binding.toggleButton.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleButton.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                if (username != null) {
                    if (avatarUrl != null) {
                        detailviewModel.addFavorite(username, id, avatarUrl)
                    }
                }
            }
            else {
                detailviewModel.deleteDataUser(id)
            }
            binding.toggleButton.isChecked= _isChecked
        }
    }
    private fun showLoading(state: Boolean) {
        if (state){
            binding.progressBar.visibility=View.VISIBLE
        }
        else {
            binding.progressBar.visibility= View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu3 -> {
                val i = Intent(this, DarkModeActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }
    companion object {
        const val DETAIL_USER = "detail_user"
        const val DETAIL_ID = "detail_id"
        const val DETAIL_AVATAR = "detail_avatar"
    }
}