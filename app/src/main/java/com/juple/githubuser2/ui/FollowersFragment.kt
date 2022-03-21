package com.juple.githubuser2.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.juple.githubuser2.R
import com.juple.githubuser2.adapter.SearchAdapter
import com.juple.githubuser2.databinding.FragmentFollowerBinding
import com.juple.githubuser2.viewmodel.FollowersViewModel

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FollowersViewModel>()

    private lateinit var adapter: SearchAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailActivity.USER).toString()
        _binding = FragmentFollowerBinding.bind(view)

        viewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }
        viewModel.listFollower.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.addList(it)
            }
        }
        viewModel.getListFollower(username)

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()
        binding.apply {
            rvFollower.layoutManager = LinearLayoutManager(activity)
            rvFollower.setHasFixedSize(true)
            rvFollower.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarFollower.visibility = if (state) View.VISIBLE else View.GONE
    }
}