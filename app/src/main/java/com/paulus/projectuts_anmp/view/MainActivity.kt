package com.paulus.projectuts_anmp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.paulus.projectuts_anmp.R
import com.paulus.projectuts_anmp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}