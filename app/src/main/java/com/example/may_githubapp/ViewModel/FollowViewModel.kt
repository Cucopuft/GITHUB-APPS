package com.example.may_githubapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.may_githubapp.Api.ApiConfig
import com.example.may_githubapp.Response.DataUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    val listFollower = MutableLiveData<ArrayList<DataUser>>()
    val listFollowing = MutableLiveData<ArrayList<DataUser>>()

    fun setDataListFollower(username: String) {
        ApiConfig.apiInstance
            .getfollowers(username)
            .enqueue(getCallback(listFollower))
    }

    fun getDataListFollowers(): LiveData<ArrayList<DataUser>> = listFollower

    fun setDataListFollowing(username: String) {
        ApiConfig.apiInstance
            .getfollowing(username)
            .enqueue(getCallback(listFollowing))
    }

    fun getDataListFollowing(): LiveData<ArrayList<DataUser>> = listFollowing

    private fun getCallback(data: MutableLiveData<ArrayList<DataUser>>): Callback<ArrayList<DataUser>> {
        return object : Callback<ArrayList<DataUser>> {
            override fun onResponse(
                call: Call<ArrayList<DataUser>>,
                response: Response<ArrayList<DataUser>>
            ) {
                if (response.isSuccessful) {
                    data.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<DataUser>>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        }
    }
}
