package com.juple.githubuser2.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.juple.githubuser2.R
import com.juple.githubuser2.adapter.TabAdapter
import com.juple.githubuser2.databinding.ActivityDetailBinding
import com.juple.githubuser2.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USER)

        viewModel.isLoading.observe(this) { }
        viewModel.detailUser.observe(this) {
            if (it != null) {
                binding.apply {
                    detailName.text = it.name
                    detailUsername.text = it.login
                    detailCompany.text = it.company
                    detailLocation.text = it.location
                    detailRepo.text = it.public_repos.toString()
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .circleCrop()
                        .into(detailAvatar)
                }
            }
        }
        username?.let { viewModel.getDetailUser(it) }

        viewModel.detailUser.observe(this) {
            val totalFollowers = it.followers
            val totalFollowing = it.following

            val tabAdapter = TabAdapter(this)
            val viewPager: ViewPager2 = binding.viewPager
            viewPager.adapter = tabAdapter
            val tabs: TabLayout = binding.tabs
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position], totalFollowers, totalFollowing)
            }.attach()
        }
    }

    companion object {
        const val USER = "user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }
}