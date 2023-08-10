package com.ab.clipchat.ui.github

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ab.clipchat.databinding.FragmentGithubUsersBinding
import com.ab.clipchat.utils.AdapterUtils
import com.ab.clipchat.utils.Utils
import kotlinx.coroutines.launch

class GitHubUserFragment : Fragment(),AdapterUtils.GitHubUserDelegate {

    private var _binding: FragmentGithubUsersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var viewModel : GitHubViewModel
    private lateinit var pagingAdapter: GitHubUserPagingAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(GitHubViewModel::class.java)

        _binding = FragmentGithubUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagingAdapter = GitHubUserPagingAdapter(this)
        binding.gitHubUserList.layoutManager = GridLayoutManager(context,2)
        binding.gitHubUserList.adapter = pagingAdapter
        lifecycleScope.launch {
            viewModel.listData.collect{ pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
        Utils.checkDeviceOnline(requireContext())
    }

    override fun selectedGitHubUser(url: String) {
        val intent = Intent(requireContext(), GitHubActivity::class.java)
        intent.putExtra("html_url",url)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}