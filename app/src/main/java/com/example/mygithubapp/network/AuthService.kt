package com.example.mygithubapp.network

import com.example.mygithubapp.models.Repository
import com.example.mygithubapp.utils.Constants
import com.example.mygithubapp.models.AccessToken
import retrofit2.http.*

interface AuthService {

    companion object{
        const val BASE_URL = Constants.apiURL
    }

    @Headers("Accept: application/json")
    @POST(Constants.domainURL + "login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(
        @Field("client_id") clientId: String = "44dbc6108e828800aef9",
        @Field("client_secret") clientSecret: String = "d2f0c14f869eb1ced0474dca906d3307765375e5",
        @Field("code") code: String
    ): AccessToken

    @Headers("Accept: application/json")
    @GET("user/repos")
    suspend fun getRepositories(
        @Header("authorization") token: String
    ): List<Repository>
}