package com.paulus.projectuts_anmp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.paulus.projectuts_anmp.model.News

class NewsDetailViewModel(application: Application) : AndroidViewModel(application) {
    val newsLD = MutableLiveData<News>()
    val loadingLD = MutableLiveData<Boolean>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun fetch(newsId: Int?) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/news/articles.php?id=$newsId"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                loadingLD.value = false
                Log.d("show_volley", it)
                val sType = object : TypeToken<News>() { }.type
                val result = Gson().fromJson<News>(it, sType)
                newsLD.value = result
            },
            {
                loadingLD.value = false
                Log.e("show_volley", it.toString())
            })

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}