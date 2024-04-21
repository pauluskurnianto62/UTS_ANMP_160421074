package com.paulus.projectuts_anmp.viewmodel

import android.app.Application
import android.provider.ContactsContract
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
import com.paulus.projectuts_anmp.model.User
import org.json.JSONObject

class ProfileViewModel (application: Application) : AndroidViewModel(application){
    val userLD = MutableLiveData<User>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun fetch(userId: Int?) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/anmp/news/profile.php"

        val stringRequest = object: StringRequest(
            Request.Method.POST, url, {}, {}) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"]= userId.toString()

                return params
            }
        }

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}