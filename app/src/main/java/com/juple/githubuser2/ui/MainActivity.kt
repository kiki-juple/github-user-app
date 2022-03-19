package com.juple.githubuser2.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.juple.githubuser2.adapter.SearchAdapter
import com.juple.githubuser2.data.User
import com.juple.githubuser2.databinding.ActivityMainBinding
import com.juple.githubuser2.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchAdapter
    private val viewModel by viewModels<MainViewModel>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this) { showLoading(it) }

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()
        binding.apply {
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rvUsers.layoutManager = GridLayoutManager(this@MainActivity, 2)
            } else {
                rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            }
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter

            btnSearch.setOnClickListener {
                searchUser()
            }

            editQuery.setOnKeyListener { _, keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.listUser.observe(this) {
            if (it != null) {
                adapter.addList(it)
            }
        }

        adapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.USER, data.login)
                    startActivity(it)
                }
            }
        })
    }

    private fun searchUser() {
        binding.apply {
            val query = editQuery.text.toString()
            if (query.isEmpty()) return
            viewModel.getListUser(query)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}