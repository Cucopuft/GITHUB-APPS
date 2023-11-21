package com.example.may_githubapp.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.may_githubapp.Api.ApiConfig
import com.example.may_githubapp.Data.DbUser
import com.example.may_githubapp.Data.FavoriteUser
import com.example.may_githubapp.Data.FavoriteUserDAO
import com.example.may_githubapp.Response.DataUser
import com.example.may_githubapp.Response.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val getUser = MutableLiveData<DetailUserResponse>()

    private var user_DAO : FavoriteUserDAO?
    private var user_DB: DbUser?

    init {
        user_DB = DbUser.getDB(application)
        user_DAO = user_DB?.favoriteUserDao()
    }

    /*get detail user*/
    fun setDataUserDetail(username: String) {
        ApiConfig.apiInstance
            .detailuser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        getUser.postValue((response.body()))
                    }
                }
                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Data Failure", t.message.toString())
                }
            })
    }
    fun getDataUserDetail(): LiveData<DetailUserResponse> {
        return getUser
    }

    fun addFavorite(username: String, id : Int, avatarUrl : String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            user_DAO?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id : Int) = user_DAO?.cekUser(id)

    fun deleteDataUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            user_DAO?.removeFavorite(id)
        }
    }

}