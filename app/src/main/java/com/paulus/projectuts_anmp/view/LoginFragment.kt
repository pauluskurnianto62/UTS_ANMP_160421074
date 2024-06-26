package com.paulus.projectuts_anmp.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.paulus.projectuts_anmp.databinding.FragmentLoginBinding
import com.paulus.projectuts_anmp.model.News
import com.paulus.projectuts_anmp.model.User
import com.paulus.projectuts_anmp.viewmodel.ProfileViewModel
import org.json.JSONObject

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCreate.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginRegistFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnSignin.setOnClickListener {
            val q = Volley.newRequestQueue(context)
            val url = "http://10.0.2.2/anmp/news/login.php"
            val dialog = AlertDialog.Builder(context)
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                {
                    Log.d("login", it)
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "success") {
                        val data = obj.getJSONArray("data")
                        if (data.length() > 0) {
                            val dataUser = data.getJSONObject(0)
                            val sType = object : TypeToken<User>() { }.type
                            val user = Gson().fromJson(dataUser.toString(), sType) as User
                            Log.d("login_result", user.toString())
                            dialog.setMessage("Welcome to 'My Hobby' apps, ${user.username}")
                            dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                                val action = LoginFragmentDirections.actionLoginNewsListFragment(user.userid)
                                Navigation.findNavController(binding.root).navigate(action)
                            })
                            dialog.create().show()
                        }
                    } else{
                        dialog.setMessage("Username or password is incorrect, please try again")
                        dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        })
                        dialog.create().show()
                    }
                },
                {
                    Log.e("apiresult", it.printStackTrace().toString())
                    dialog.setMessage("There's error in login process, please try again")
                    dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                    dialog.create().show()
                }
            )
            {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["username"] = binding.txtUsername.text.toString()
                    params["password"] = binding.txtPassword.text.toString()
                    return params
                }
            }
            q?.add(stringRequest)
        }
    }
}