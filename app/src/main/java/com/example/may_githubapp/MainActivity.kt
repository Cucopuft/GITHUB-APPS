package com.example.may_githubapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.may_githubapp.Adapter.LisUserAdapter
import com.example.may_githubapp.DarkMode.DarkModeActivity
import com.example.may_githubapp.DarkMode.DarkModeViewModel
import com.example.may_githubapp.DarkMode.SettingPreferences
import com.example.may_githubapp.DarkMode.createFactory
import com.example.may_githubapp.DarkMode.dataStore
import com.example.may_githubapp.Response.DataUser
import com.example.may_githubapp.ViewModel.UserSearchViewModel
import com.example.may_githubapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchViewModel: UserSearchViewModel
    private lateinit var listAdapter: LisUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Github User"

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModelFactory = DarkModeViewModel(pref).createFactory()
        val settingViewModel = ViewModelProvider(this, settingViewModelFactory)[DarkModeViewModel::class.java]
        settingViewModel.themeSetting().observe(this) {isDarkMode ->
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        initializeComponents()
        setupListeners()
        observeUserData()
    }

    private fun initializeComponents() {
        listAdapter = LisUserAdapter()
        listAdapter.notifyDataSetChanged()
        listAdapter.setOnItemClickCallback(object : LisUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataUser) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.DETAIL_USER, data.login)
                    it.putExtra(DetailActivity.DETAIL_ID, data.id)
                    it.putExtra(DetailActivity.DETAIL_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        searchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserSearchViewModel::class.java)

        binding.rvGithub.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = listAdapter
        }
    }

    private fun setupListeners() {
        binding.apply {
            searchButton.setOnClickListener {
                searchUser()
            }
            searchQuery.setOnKeyListener { v, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    private fun observeUserData() {
        searchViewModel.getDataUser().observe(this) { userData ->
            if (userData != null) {
                listAdapter.setListUser(userData)
                showLoading(false)
            }
        }
    }

    private fun searchUser() {
        binding.apply {
            val query = searchQuery.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            searchViewModel.setDataUser(query)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                return true
            }
            R.id.menu2 -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.menu3 -> {
                val intent = Intent(this, DarkModeActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return true
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progresBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
