package com.example.may_githubapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.may_githubapp.Adapter.LisUserAdapter
import com.example.may_githubapp.Data.FavoriteUser
import com.example.may_githubapp.Response.DataUser
import com.example.may_githubapp.ViewModel.FavoriteViewModel
import com.example.may_githubapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favAdapter: LisUserAdapter
    private lateinit var favViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Favorite"

        setupRecyclerView()
        setupViewModel()
        setupItemCallback()

        favViewModel.getFavoriteDataUser()?.observe(this) { favorites ->
            val userList = mapFavoriteListToDataUserList(favorites)
            favAdapter.setListUser(userList)
        }
    }

    private fun setupRecyclerView() {
        favAdapter = LisUserAdapter()
        favAdapter.notifyDataSetChanged()

        binding.rvGithubFav.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = favAdapter
        }
    }

    private fun setupViewModel() {
        favViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
    }

    private fun setupItemCallback() {
        favAdapter.setOnItemClickCallback(object : LisUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataUser) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.DETAIL_USER, data.login)
                    putExtra(DetailActivity.DETAIL_ID, data.id)
                    putExtra(DetailActivity.DETAIL_AVATAR, data.avatar_url)
                    startActivity(this)
                }
            }
        })
    }

    private fun mapFavoriteListToDataUserList(favorites: List<FavoriteUser>): ArrayList<DataUser> {
        val dataUserList = ArrayList<DataUser>()
        for (favorite in favorites) {
            val dataUser = DataUser(
                favorite.login,
                favorite.id,
                favorite.avatar_url
            )
            dataUserList.add(dataUser)
        }
        return dataUserList
    }
}
