package com.syafiqfajrianemha.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafiqfajrianemha.githubuser.databinding.FragmentFollowBinding
import com.syafiqfajrianemha.githubuser.helper.ViewModelFactory
import com.syafiqfajrianemha.githubuser.ui.adapter.UserAdapter
import com.syafiqfajrianemha.githubuser.ui.viewmodel.FollowViewModel

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private var position: Int? = null
    private var username: String? = null

    private val followViewModel by viewModels<FollowViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager

        if (position == 1) {
            followViewModel.setFollowers(username.toString())
            followViewModel.userFollowers.observe(viewLifecycleOwner) {
                binding.rvFollow.adapter = UserAdapter(it)
            }
        } else {
            followViewModel.setFollowing(username.toString())
            followViewModel.userFollowing.observe(viewLifecycleOwner) {
                binding.rvFollow.adapter = UserAdapter(it)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar3.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }
}