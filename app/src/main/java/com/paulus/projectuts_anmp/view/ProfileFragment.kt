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
import com.paulus.projectuts_anmp.model.News
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

        if(arguments != null) {
            val userid = ProfileFragmentArgs.fromBundle(requireArguments()).userid
            val q = Volley.newRequestQueue(context)
            val url = "http://10.0.2.2/anmp/news/profile.php"
            val dialog = AlertDialog.Builder(context)
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                {
                    Log.d("profile", it)
                    val sType = object : TypeToken<User>() { }.type
                    val user = Gson().fromJson<User>(it, sType)
                    Log.d("profile_result", user.toString())
                    binding.txtEditFirstName.setText(user.first_name.toString())
                    binding.txtEditLastName.setText(user.last_name.toString())
                    binding.txtEditPassword.setText(user.password.toString())
                },
                {
                    Log.e("apiresult", it.printStackTrace().toString())
                    dialog.setMessage("Username is not found")
                    dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                    dialog.create().show()
                }
            ) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["id"] = userid.toString()
                    return params
                }
            }
            q.add(stringRequest)
        }

        binding.btnUpdate.setOnClickListener {
            val q = Volley.newRequestQueue(activity)
            val url = "http://10.0.2.2/anmp/news/update_profile.php"
            val dialog = AlertDialog.Builder(requireActivity())

            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                {
                    Log.d("apiresult", it)
                    dialog.setMessage("Successfully changed the profile")
                    dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        binding.txtEditPassword.text?.clear()
                        dialog.dismiss()
                    })
                    dialog.create().show()
                },
                {
                    Log.e("apiresult", it.toString())
                    dialog.setMessage("Cannot change the profile, please check again")
                    dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        binding.txtEditPassword.text?.clear()
                        dialog.dismiss()
                    })
                    dialog.create().show()
                }
            ) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["first_name"] = binding.txtEditFirstName.text.toString()
                    params["last_name"] = binding.txtEditLastName.text.toString()
                    params["password"] = binding.txtEditPassword.text.toString()
                    params["id"] = ProfileFragmentArgs.fromBundle(requireArguments()).userid.toString()
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
}