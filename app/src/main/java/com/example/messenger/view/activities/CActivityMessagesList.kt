package com.example.messenger.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger.R
import com.example.messenger.dao.IDAOLessons
import com.example.messenger.databinding.ActivityMessagesListBinding
import com.example.messenger.model.CMessage
import com.example.messenger.util.CDatabase
import com.example.messenger.view.adapters.CRecyclerViewMessagesListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import java.util.*

class CActivityMessagesList : AppCompatActivity() {

    private lateinit var  binding : ActivityMessagesListBinding
    private lateinit var adapter : CRecyclerViewMessagesListAdapter
    private val messagesList = ArrayList<CMessage>()
    private val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")
    private lateinit var daoLessons: IDAOLessons
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

//        val listener = object : CRecyclerViewMessagesListAdapter
//        .IClickListener {
//            override fun onItemClick(lesson: CMessage, index: Int) {
//                //Toast.makeText(this@CActivityLessonList, lesson.subject, Toast.LENGTH_SHORT).show()
//                val intent = Intent(this@CActivityLessonList, CActivityLesson::class.java)
//                intent.putExtra(getString(R.string.param_lesson_id), lesson.id.toString())
//                resultLauncher.launch(intent)
//            }
//            override fun onItemDeleteClick(lesson: CMessage, index: Int) {
//                lifecycleScope.launch {
//                    daoLessons.delete(lesson)
//                }
//            }
//        }

        adapter = CRecyclerViewMessagesListAdapter(messagesList)
        binding.rvMessagesList.adapter = adapter
        binding.rvMessagesList.layoutManager = LinearLayoutManager(this)
        binding.bmNvgMessagesList.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.bmiSendMessage -> {
                    val msg = CMessage(
                        UUID.randomUUID(),
                        "UserName",
                        binding.etEnterMessage.text.toString(),
                        LocalDateTime.now()
                    )
                    lifecycleScope.launch {
//                        val intent = Intent(this@CActivityMessagesList, CActivityLesson::class.java)
//                        intent.putExtra(getString(R.string.param_lesson_id), lesson.id.toString())
//                        resultLauncher.launch(intent)
                        daoLessons.insert(msg)
                    }
                    true
                }
                R.id.bmiExit -> {
                    val sharedPref = applicationContext
                        .getSharedPreferences(getString(R.string.file_name_preferences), Context.MODE_PRIVATE)
                    with (sharedPref.edit()) {
                        putString(getString(R.string.param_login_key), "")
                        apply()
                    }
                    finishAffinity()
                    true
                }
                else -> false
            }
        }
        val db = CDatabase.getDatabase(this)
        daoLessons = db.daoLessons()
        lifecycleScope.launch {
            createInitialData(daoLessons)
        }
        lifecycleScope.launch{
            daoLessons.getAllFlow().collect {
                adapter.updateData(it)
            }
        }
    }
    private suspend fun createInitialData(daoLessons: IDAOLessons) = withContext(Dispatchers.IO)
    {
        if (daoLessons.getAll().isNotEmpty())
            return@withContext
        messagesList.add(
            CMessage(
                UUID.fromString("bcfe5813-df87-4b59-9a1c-a2e18cea8079"),
                userName = "UserName1",
                msg = "TextTextTextTextTextTextTextTextTextTextTextText",
                LocalDateTime.parse("2021-09-30 08:00", formatter))
        )
        messagesList.add(
            CMessage(
                UUID.fromString("a9045654-2a42-4f6b-ad51-983adb43e422"),
                userName = "UserName2",msg = "TextTextTextTextTextTextTextTextTextTextTextText",
                LocalDateTime.parse("2021-09-30 08:00", formatter))
        )
        messagesList.add(
            CMessage(
                UUID.fromString("1fc1e764-fb52-40dc-83b5-fede31e67dae"),
                userName = "UserName3",msg = "TextTextTextTextTextTextTextTextTextTextTextText",
                LocalDateTime.parse("2021-09-30 08:00", formatter))
        )
        messagesList.forEach {
            daoLessons.insert(it)
        }
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