package com.example.mygithubapp.repository

import com.example.mygithubapp.network.AuthService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val authService: AuthService) {

    suspend fun getAccessToken(code: String) = authService.getAccessToken(code = code)
    suspend fun getRepositories(token: String) = authService.getRepositories(token = token)
}