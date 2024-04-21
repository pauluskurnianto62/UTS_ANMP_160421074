package com.paulus.projectuts_anmp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.paulus.projectuts_anmp.databinding.NewsListItemBinding
import com.paulus.projectuts_anmp.model.News
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class NewsListAdapter(val newsList:ArrayList<News>): RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {
    class NewsViewHolder(var binding: NewsListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.txtTitle.text = newsList[position].title
        holder.binding.txtAuthor.text = "@" + newsList[position].author
        holder.binding.txtShortDesc.text = newsList[position].short_desc
        holder.binding.btnRead.setOnClickListener {
            val action = NewsListFragmentDirections.actionNewsDetailFragment(newsList[position].id)
            Navigation.findNavController(it).navigate(action)
        }
        val picasso = Picasso.Builder(holder.itemView.context)
        picasso.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        picasso.build().load(newsList[position].images).into(holder.binding.imgNews, object:
            Callback {
            override fun onSuccess() {
                holder.binding.progressImage.visibility = View.INVISIBLE
                holder.binding.imgNews.visibility = View.VISIBLE
            }

            override fun onError(e: Exception?) {
                Log.e("picasso_error", e.toString())
            }
        })
    }

    fun updateStudentList(newNewsList: ArrayList<News>) {
        newsList.clear()
        newsList.addAll(newNewsList)
        notifyDataSetChanged()
    }
}