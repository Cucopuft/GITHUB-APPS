package com.example.may_githubapp.Api

import com.example.may_githubapp.Response.DataUser
import com.example.may_githubapp.Response.DetailUserResponse
import com.example.may_githubapp.Response.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization:token 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")
    @GET("search/users")
    fun searchuser(
        @Query("q") query: String
    ): Call<ResponseUser>


    @Headers("Authorization:token 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")
    @GET("users/{username}")
    fun detailuser(
        @Path("username") username: String
    ): Call<DetailUserResponse>


    @Headers("Authorization:token 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")
    @GET("users/{username}/followers")
    fun getfollowers(
        @Path("username") username: String
    ): Call<ArrayList<DataUser>>

    @Headers("Authorization:token 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")
    @GET("users/{username}/following")
    fun getfollowing(
        @Path("username") username: String
    ): Call<ArrayList<DataUser>>
}