package com.juple.githubuser2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juple.githubuser2.api.ApiConfig
import com.juple.githubuser2.data.User
import com.juple.githubuser2.data.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUser = MutableLiveData<ArrayList<User>>()
    val listUser: LiveData<ArrayList<User>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getListUser(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUsers(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.postValue(response.body()?.items)
                } else {
                    Log.e(TAG, "onFailure ${response.body()?.items}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failure, ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}