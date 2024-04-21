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
            val dialog = AlertDialog.Builder(context)
            val stringRequest = object: StringRequest(
                Request.Method.POST, url, {
                    Log.d("apiresult", it)
                    if (binding.txtNewPassword.text.toString() == binding.txtRetypePassword.text.toString()) {
                        Log.d("apiresult", it)
                        dialog.setMessage("Success to create a new account")
                        dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                            val action = RegistFragmentDirections.actionLoginFragment()
                            Navigation.findNavController(binding.root).navigate(action)
                        })
                        dialog.create().show()
                    }
                    else {
                        dialog.setMessage("Password doesn't match")
                        dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        })
                        dialog.create().show()
                    }
                }, {
                    Log.e("apiresult", it.printStackTrace().toString())
                    dialog.setMessage("Failed to register, please try again")
                    dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                    dialog.create().show()
                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["first_name"]= binding.txtFirstName.text.toString()
                    params["last_name"]= binding.txtLastName.text.toString()
                    params["username"]= binding.txtNewUsername.text.toString()
                    params["email"]= binding.txtEmail.text.toString()
                    params["password"]= binding.txtNewPassword.text.toString()

                    return params
                }
            }
            q.add(stringRequest)
        }
    }
}