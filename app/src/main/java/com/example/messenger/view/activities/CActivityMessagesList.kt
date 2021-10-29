package com.example.messenger.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
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
import android.graphics.Color


class CActivityMessagesList : AppCompatActivity() {

    private lateinit var  binding : ActivityMessagesListBinding
    private lateinit var adapter : CRecyclerViewMessagesListAdapter
    private lateinit var daoLessons: IDAOLessons
    private var actionMode : ActionMode? = null
    private val messagesList = ArrayList<CMessage>()
    private val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")
    private lateinit var post : CMessage
    private lateinit var v : View

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMessagesListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = CRecyclerViewMessagesListAdapter(messagesList, listener)
        binding.rvMessagesList.adapter = adapter
        binding.rvMessagesList.layoutManager = LinearLayoutManager(this)


        val db = CDatabase.getDatabase(this)
        daoLessons = db.daoLessons()
        lifecycleScope.launch {
            createInitialData(daoLessons)
        }
        lifecycleScope.launch{
            daoLessons.getAllFlow().collect {
                adapter.updateData(it)
                binding.rvMessagesList.smoothScrollToPosition(adapter.itemCount)
            }
        }
        enterMessageSetOnKeyListener()
    }
    private val listener = object : CRecyclerViewMessagesListAdapter.IClickListener {
        override fun onLongItemClick(v : View, post : CMessage) {
            if(actionMode == null)
            {
                actionMode = this@CActivityMessagesList.startActionMode(actionModeCallback)
                v.isSelected = true
            }
            this@CActivityMessagesList.post = post
            this@CActivityMessagesList.v = v
        }
    }
    val actionModeCallback = object : ActionMode.Callback {
        // Called when the action mode is created; startActionMode() was called
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Inflate a menu resource providing context menu items
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.activity_messages_context_menu, menu)
            return true
        }
        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false // Return false if nothing is done
        }
        // Called when the user selects a contextual menu item
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.miContextEditMessage -> {

                    binding.btnSend.visibility = View.INVISIBLE
                    binding.etEnterMessage.setText(post.msg)
                    binding.etEnterMessage.requestFocus()

                    val imm: InputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(binding.etEnterMessage, InputMethodManager.SHOW_IMPLICIT)

                    binding.etEnterMessage.setOnKeyListener(object : View.OnKeyListener {
                        override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                            if (event.action == KeyEvent.ACTION_DOWN &&
                                keyCode == KeyEvent.KEYCODE_ENTER
                            ) {
                                post.msg = binding.etEnterMessage.text.toString()
                                lifecycleScope.launch {
                                    daoLessons.update(post)
                                }
                                binding.etEnterMessage.text.clear()
                                binding.etEnterMessage.setOnKeyListener(null)
                                binding.btnSend.visibility = View.VISIBLE
                                mode.finish()
                                return true
                            }
                            return false
                        }
                    })
                    true
                }
                R.id.miContextDeleteMessage -> {
                    lifecycleScope.launch {
                        daoLessons.delete(post)
                    }
                    mode.finish()
                    true
                }
                else -> false
            }
        }
        // Called when the user exits the action mode
        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null
            v.setBackgroundColor(Color.WHITE)

            binding.etEnterMessage.text.clear()
            binding.etEnterMessage.setOnKeyListener(null)
            val imm: InputMethodManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.etEnterMessage.windowToken, 0)
            binding.btnSend.visibility = View.VISIBLE
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
                val sharedPref = applicationContext
                    .getSharedPreferences(getString(R.string.file_name_preferences), Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putString(getString(R.string.param_login_key), "")
                    apply()
                }
                startActivity(Intent(this, CActivitySignIn::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun enterMessageSetOnKeyListener()
    {
        binding.btnSend.setOnClickListener {
            val post = CMessage(
                UUID.randomUUID(),
                "UserName",
                binding.etEnterMessage.text.toString(),
                LocalDateTime.now()
            )
            lifecycleScope.launch {
                daoLessons.insert(post)
            }
            binding.etEnterMessage.text.clear()
        }
    }
}