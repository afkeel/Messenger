package com.example.messenger.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.databinding.MessagesListItemBinding
import com.example.messenger.model.CMessage
import org.joda.time.format.DateTimeFormat
import com.example.messenger.R

class CRecyclerViewMessagesListAdapter (
    private var msgList: List<CMessage>,
    //private val listener : IClickListener?
    ) : RecyclerView.Adapter<CRecyclerViewMessagesListAdapter.ViewHolder>() {
    private val formatter = DateTimeFormat.forPattern("YYYY-MM-DD HH:mm")
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(val binding: MessagesListItemBinding) : RecyclerView
    .ViewHolder(binding.root) {
        var msg : CMessage? = null
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = MessagesListItemBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }
    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //viewHolder.binding.ivUserIcon = msgList[position].
        viewHolder.binding.tvMsgUserName.text = msgList[position].userName
        viewHolder.binding.tvMsgContent.text = msgList[position].msg
        viewHolder.binding.tvMsgDateTime.text = msgList[position].dateTime.toString(formatter)

//        viewHolder.msg = msgList[position]
//        viewHolder.binding.llLesson.setOnClickListener {
//            viewHolder.msg?.let {
//                listener?.onItemClick(it, msgList.indexOf(viewHolder.msg))
//            }
//        }
//
//        viewHolder.binding. {
//            viewHolder.msg?.let {
//                listener?.onItemDeleteClick(it, msgList.indexOf(viewHolder.msg))
//            }
//        }
    }
//    interface IClickListener{
//        fun onItemClick(lesson : CMessage, index : Int)
//        fun onItemDeleteClick(lesson : CMessage, index : Int)
//    }
    fun updateData(msgList : List<CMessage>)
    {
        this.msgList = msgList
        notifyDataSetChanged()
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = msgList.size
}