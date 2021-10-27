package com.example.mygithubapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygithubapp.models.Repository
import com.example.mygithubapp.models.AccessToken
import com.example.mygithubapp.repository.AuthRepository
import kotlinx.coroutines.launch
import com.example.mygithubapp.utils.NetworkHelper
import com.example.mygithubapp.utils.Resource
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    fun getAccessToken(code: String): LiveData<Resource<AccessToken>> {
        val liveData = MutableLiveData<Resource<AccessToken>>()
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                liveData.postValue(Resource.loading(null))
                val response = repository.getAccessToken(code)
                liveData.postValue(Resource.success(response))
            }
        } else {
            liveData.postValue(Resource.error("No internet connection", null))
        }
        return liveData
    }

    fun getRepositories(token: String): LiveData<Resource<List<Repository>>> {
        val reposList = MutableLiveData<Resource<List<Repository>>>()
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                reposList.postValue(Resource.loading(null))
                val response = repository.getRepositories("bearer $token")
                reposList.postValue(Resource.success(response))
            }
        } else {
            reposList.postValue(Resource.error("No internet connection", null))
        }
        return reposList
    }
}