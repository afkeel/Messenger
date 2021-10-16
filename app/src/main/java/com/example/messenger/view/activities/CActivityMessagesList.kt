package com.example.messenger.view.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.messenger.R
import com.example.messenger.databinding.ActivityMessagesListBinding

class CActivityMessagesList : AppCompatActivity() {

    private lateinit var  binding : ActivityMessagesListBinding
//    private var resultLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK){
//                val data: Intent? = result.data
//                val res = data?.getIntExtra("PARAM_123", 0)
//            }
//        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.activity_messages_list_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.miAccountEdit -> {
                startActivity(Intent(this, CActivityUserAccount::class.java))
                //resultLauncher.launch(Intent(this, CActivityUserAccount::class.java))
                true
            }
            R.id.miExit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}