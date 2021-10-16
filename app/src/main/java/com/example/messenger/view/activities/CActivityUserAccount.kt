package com.example.messenger.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.messenger.databinding.ActivityUserAccountBinding

class CActivityUserAccount : AppCompatActivity() {

    private lateinit var  binding : ActivityUserAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAccountBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}