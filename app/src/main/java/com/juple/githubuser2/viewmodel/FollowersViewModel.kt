package com.juple.githubuser2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.juple.githubuser2.api.ApiConfig
import com.juple.githubuser2.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    private val _listFollower = MutableLiveData<ArrayList<User>>()
    val listFollower: LiveData<ArrayList<User>> = _listFollower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getListFollower(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailFollowers(username)
        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollower.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure ${response.body()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "Failure, ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "FollowersViewModel"
    }
}