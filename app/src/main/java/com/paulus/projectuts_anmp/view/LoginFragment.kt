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
import com.paulus.projectuts_anmp.model.User
import com.paulus.projectuts_anmp.viewmodel.ProfileViewModel
import org.json.JSONObject

class LoginFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
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

        ObserveViewModel()

    }

    fun ObserveViewModel() {
        viewModel.userLD.observe(viewLifecycleOwner, Observer { user ->
            binding.btnSignin.setOnClickListener {
                val q = Volley.newRequestQueue(it.context)
                val url = "http://10.0.2.2/anmp/news/login.php"
                //val dialog = AlertDialog.Builder(context)
                val stringRequest = object: StringRequest(
                    Request.Method.POST, url, {}, {}) {
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        params["username"]= binding.txtUsername.text.toString()
                        params["password"]= binding.txtPassword.text.toString()
                        //dialog.setMessage("Login success")
                        val action = LoginFragmentDirections.actionLoginNewsListFragment(user.id)
                        Navigation.findNavController(binding.root).navigate(action)
                        return params
                    }
                }
                q.add(stringRequest)
            }
        })
    }
}