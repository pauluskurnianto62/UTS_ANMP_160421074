package com.paulus.projectuts_anmp.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paulus.projectuts_anmp.databinding.FragmentRegistBinding
import org.json.JSONObject

class RegistFragment : Fragment() {

    private lateinit var binding: FragmentRegistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            val action = RegistFragmentDirections.actionLoginFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnCreateAccount.setOnClickListener {
            val q = Volley.newRequestQueue(it.context)
            val url = "http://10.0.2.2/anmp/news/regist.php"
            val stringRequest = object: StringRequest(
                Request.Method.POST, url, {}, {}) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["first_name"]= binding.txtFirstName.toString()
                    params["last_name"]= binding.txtLastName.toString()
                    params["username"]= binding.txtNewUsername.toString()
                    params["email"]= binding.txtEmail.toString()
                    params["password"]= binding.txtNewPassword.toString()

                    return params
                }
            }
            q.add(stringRequest)
        }
    }
}