package com.juple.githubuser2.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.juple.githubuser2.R
import com.juple.githubuser2.adapter.SearchAdapter
import com.juple.githubuser2.data.ItemsItem
import com.juple.githubuser2.databinding.ActivityMainBinding
import com.juple.githubuser2.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<ItemsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.listUser.observe(this) { setListUser(it as ArrayList<ItemsItem>) }
        list.addAll(list)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(this) { showLoading(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return true
    }

    private fun setListUser(listUsers: ArrayList<ItemsItem>) {
        listUsers.map { it.avatarUrl; it.login }
        val searchUserAdapter = SearchAdapter(listUsers)
        binding.rvUsers.adapter = searchUserAdapter
        searchUserAdapter.setOnItemClickCallback(object : SearchAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                showSelectedUser(data)
            }
        })
    }

    private fun showLoading(b: Boolean) {
        binding.progressBar.visibility = if (b) View.INVISIBLE else View.GONE
    }

    private fun showSelectedUser(user: ItemsItem) {
        val intentToDetail = Intent(this, DetailActivity::class.java)
        intentToDetail.putExtra(EXTRA_USER, user.login)
        startActivity(intentToDetail)
    }

    companion object {
        const val EXTRA_USER = "extra_user"
    }
}