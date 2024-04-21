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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.paulus.projectuts_anmp.R
import com.paulus.projectuts_anmp.databinding.FragmentProfileBinding
import com.paulus.projectuts_anmp.databinding.FragmentRegistBinding
import com.paulus.projectuts_anmp.model.User
import com.paulus.projectuts_anmp.viewmodel.NewsDetailViewModel
import com.paulus.projectuts_anmp.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso
import org.json.JSONObject

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getInt("userId")
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.fetch(userId)

        observeViewModel()

        binding.btnUpdate.setOnClickListener {
            val q = Volley.newRequestQueue(it.context)
            val url = "http://10.0.2.2/anmp/news/update_profile.php"
            val stringRequest = object: StringRequest(
                Request.Method.POST, url, {}, {}) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["first_name"]= binding.txtEditFirstName.toString()
                    params["last_name"]= binding.txtEditLastName.toString()
                    params["password"]= binding.txtEditPassword.toString()
                    params["username"]= userId.toString()

                    return params
                }
            }
            q.add(stringRequest)
        }

        binding.btnLogout.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileLoginFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }

    fun observeViewModel() {
        viewModel.userLD.observe(viewLifecycleOwner, Observer {user ->
            binding.txtEditFirstName.setText(user.first_name)
            binding.txtEditLastName.setText(user.last_name)
            binding.txtEditPassword.setText(user.password)

            binding.btnBackProfile.setOnClickListener {
                val action = ProfileFragmentDirections.actionProfileFragmentToNewsListFragment(user.id)
                Navigation.findNavController(it).navigate(action)
            }
        })
    }
}