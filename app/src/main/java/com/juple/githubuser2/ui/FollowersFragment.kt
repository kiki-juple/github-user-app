package com.juple.githubuser2.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.juple.githubuser2.databinding.FragmentFollowerBinding

class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowerBinding.bind(view)
    }
}