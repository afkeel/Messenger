package com.example.messenger.view.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.messenger.R
import com.example.messenger.databinding.ActivitySignInBinding

class CActivitySignIn : AppCompatActivity() {

    private lateinit var  binding : ActivitySignInBinding
    private lateinit var sharedPref : SharedPreferences
    private lateinit var login : String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPref = applicationContext
            .getSharedPreferences(getString(R.string.file_name_preferences), Context.MODE_PRIVATE)
        login = sharedPref.getString(getString(R.string.param_login_key), "").toString()
        if (login.isNotEmpty())
        {
            startActivity(Intent(this, CActivityMessagesList::class.java))
            return
        }
        binding.btnSignIn.setOnClickListener {
            login = binding.etLogin.text.toString()
            sharedPref = applicationContext
                .getSharedPreferences(getString(R.string.file_name_preferences), Context.MODE_PRIVATE)
            with (sharedPref.edit()) {
                putString(getString(R.string.param_login_key), login)
                apply()
            }
            startActivity(Intent(this, CActivityMessagesList::class.java))
        }
    }
}