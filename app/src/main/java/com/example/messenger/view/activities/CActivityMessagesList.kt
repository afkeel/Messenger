package com.example.messenger.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.messenger.databinding.ActivityMessagesListBinding

class CActivityMessagesList : AppCompatActivity() {

    private lateinit var  binding : ActivityMessagesListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}