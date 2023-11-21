package com.example.may_githubapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.may_githubapp.Api.ApiConfig
import com.example.may_githubapp.Response.DataUser
import com.example.may_githubapp.Response.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class UserSearchViewModel : ViewModel() {

    val listDataUser = MutableLiveData<ArrayList<DataUser>>()

    fun setDataUser(query: String) {
        ApiConfig.apiInstance.searchuser(query).enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful) {
                    listDataUser.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Log.d("Data Failure", t.message.toString())
            }
        })
    }

    fun getDataUser(): LiveData<ArrayList<DataUser>> = listDataUser
}
