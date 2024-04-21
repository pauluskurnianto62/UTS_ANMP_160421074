package com.paulus.projectuts_anmp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.paulus.projectuts_anmp.R
import com.paulus.projectuts_anmp.databinding.FragmentNewsDetailBinding
import com.paulus.projectuts_anmp.viewmodel.NewsDetailViewModel
import com.paulus.projectuts_anmp.viewmodel.ProfileViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class NewsDetailFragment : Fragment() {
    private lateinit var viewModel: NewsDetailViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding: FragmentNewsDetailBinding
    var idPar = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        if(arguments != null) {
            val id =
                NewsDetailFragmentArgs.fromBundle(requireArguments()).id
            viewModel = ViewModelProvider(this).get(NewsDetailViewModel::class.java)
            viewModel.fetch(id)
            observeViewModel()
            binding.txtArticle.setText(viewModel.newsLD.value?.detail?.article?.get(idPar).toString())
        }
        binding.btnPrevious.isEnabled = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            idPar++
            binding.txtArticle.setText(viewModel.newsLD.value?.detail?.article?.get(idPar).toString())
            val currentPage = idPar
            val totalParagraphs = viewModel.newsLD.value?.detail?.article?.size ?: 0
            binding.btnNext.isEnabled = currentPage+1 < totalParagraphs
            binding.btnPrevious.isEnabled = currentPage+1 > 1
        }

        binding.btnPrevious.setOnClickListener {
            idPar--
            binding.txtArticle.setText(viewModel.newsLD.value?.detail?.article?.get(idPar).toString())
            val currentIndex = idPar
            val totalParagraphs = viewModel.newsLD.value?.detail?.article?.size ?: 0
            binding.btnPrevious.isEnabled = currentIndex+1 > 1
            binding.btnNext.isEnabled = currentIndex+1 < totalParagraphs
        }

        binding.btnBackNews.setOnClickListener {
            val action = NewsDetailFragmentDirections.actionNewsDetailFragmentToNewsListFragment(1)
            Navigation.findNavController(it).navigate(action)
        }


    }

    fun observeViewModel() {
        viewModel.newsLD.observe(viewLifecycleOwner, Observer {news ->
            binding.txtDetailTitle.setText(news.title)
            binding.txtDetailAuthor.setText("@" + news.author)
            val picasso = this.context?.let { it1 -> Picasso.Builder(it1) }
            picasso?.listener { picasso, uri, exception ->
                exception.printStackTrace()
            }
            picasso?.build()?.load(viewModel.newsLD.value?.images)?.into(binding.imgDetailNews)
            picasso?.build()?.load(viewModel.newsLD.value?.images)
                ?.into(binding.imgDetailNews,
                    object: Callback {
                        override fun onSuccess() {
                            //binding.progressImgDetail.visibility = View.INVISIBLE
                            binding.imgDetailNews.visibility = View.VISIBLE
                        }
                        override fun onError(e: Exception?) {
                            Log.e("picasso_error", e.toString())
                        }
                    })
        })
    }

    //fun observeParagraph(){
        //viewModel.newsLD.observe(viewLifecycleOwner, Observer {
            //binding.txtArticle.setText(viewModel.newsLD.value?.detail?.article?.get(idPar).toString())
        //})
    //}
}