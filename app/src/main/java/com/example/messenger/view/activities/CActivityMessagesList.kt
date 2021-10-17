package com.example.messenger.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.messenger.R
import com.example.messenger.databinding.ActivityMessagesListBinding
import com.example.messenger.model.CMessage
import com.example.messenger.view.adapters.CRecyclerViewMessagesListAdapter
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

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

        val formatter = DateTimeFormat.forPattern("YYYY-MM-DD HH:mm")
        val messagesList = ArrayList<CMessage>()
        messagesList.add(
            CMessage(username = "UserName",msg = "TextTextTextTextTextTextTextTextTextTextTextText",
                LocalDateTime.parse("2021-09-30 08:00", formatter)))
        messagesList.add(
            CMessage(username = "UserName",msg = "TextTextTextTextTextTextTextTextTextTextTextText",
                LocalDateTime.parse("2021-09-30 08:00", formatter)))
        messagesList.add(
            CMessage(username = "UserName",msg = "TextTextTextTextTextTextTextTextTextTextTextText",
                LocalDateTime.parse("2021-09-30 08:00", formatter)))
        val adapter = CRecyclerViewMessagesListAdapter(messagesList)
        binding.rvMessagesList.adapter = adapter
        binding.rvMessagesList.layoutManager = LinearLayoutManager(this)
        //Toast
//        val listener = object : CRecyclerViewMessagesListAdapter.IClickListener{
//            override fun onItemClick(msg: CMessage, index: Int) {
//                //Toast.makeText(this@CActivityLessonList, lesson.subject, Toast.LENGTH_SHORT).show()
//                val intent = Intent(this@CActivityLessonList, CActivityLesson::class.java)
//                intent.putExtra("PARAM_LESSON_SUBJECT",lesson.subject)
//                intent.putExtra("PARAM_LESSON_DATE",lesson.dateTime.toString())
//                intent.putExtra("PARAM_LESSON_INDEX", index)
//                resultLauncher.launch(intent)
//            }
//        }
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