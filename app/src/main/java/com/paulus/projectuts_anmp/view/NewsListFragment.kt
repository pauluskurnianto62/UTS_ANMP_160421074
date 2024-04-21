package com.paulus.projectuts_anmp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulus.projectuts_anmp.databinding.FragmentNewsListBinding
import com.paulus.projectuts_anmp.viewmodel.NewsViewModel
import com.paulus.projectuts_anmp.viewmodel.ProfileViewModel

class NewsListFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var viewModelProfile: ProfileViewModel
    private val newsListAdapter = NewsListAdapter(arrayListOf())
    private lateinit var binding: FragmentNewsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsListBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.refresh()

        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = newsListAdapter

        observeViewModel()

        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
            binding.recView.visibility = View.GONE
            binding.txtError.visibility = View.GONE
            binding.progressLoad.visibility = View.VISIBLE
            binding.refreshLayout.isRefreshing = false
        }

        binding.fobProfile.setOnClickListener {
            val action = NewsListFragmentDirections.actionProfileFragment(0)
            Navigation.findNavController(it).navigate(action)
        }


    }

    fun observeViewModel() {
        viewModel.newsLD.observe(viewLifecycleOwner, Observer {
            newsListAdapter.updateStudentList(it)
        })

        viewModel.newsLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.txtError?.visibility = View.VISIBLE
            } else {
                binding.txtError?.visibility = View.GONE
            }
        })

        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.recView.visibility = View.GONE
                binding.progressLoad.visibility = View.VISIBLE
            } else {
                binding.recView.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })
        viewModelProfile.userLD.observe(viewLifecycleOwner, Observer { user ->
            binding.fobProfile.setOnClickListener {
                val action = NewsListFragmentDirections.actionProfileFragment(user.id)
                Navigation.findNavController(it).navigate(action)
            }
        })
    }


}