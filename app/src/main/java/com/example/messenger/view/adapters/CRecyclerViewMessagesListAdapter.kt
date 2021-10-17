package com.example.messenger.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.databinding.MessagesListItemBinding
import com.example.messenger.model.CMessage
import org.joda.time.format.DateTimeFormat

class CRecyclerViewMessagesListAdapter (
    private val msgList: ArrayList<CMessage>,
    //private val listener : IClickListener?
) : RecyclerView.Adapter<CRecyclerViewMessagesListAdapter.ViewHolder>() {
    private val formatter = DateTimeFormat.forPattern("YYYY-MM-DD HH:mm")
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(val binding: MessagesListItemBinding) : RecyclerView
    .ViewHolder(binding.root) {
//        var msg : CMessage? = null
//        var index : Int = -1
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
        viewHolder.binding.tvMsgUserName.text = msgList[position].username
        viewHolder.binding.tvMsgContent.text = msgList[position].msg
        viewHolder.binding.tvMsgDateTime.text = msgList[position].dateTime.toString(formatter)
        //Toast
//        viewHolder.lesson = lessons[position]
//        viewHolder.index = position
//        viewHolder.binding.llLesson.setOnClickListener {
//            viewHolder.lesson?.let {
//                listener?.onItemClick(it, viewHolder.index)
//            }
//        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = msgList.size
    //Toast
//    interface IClickListener{
//        fun onItemClick(msg : CMessage, index : Int)
//    }
}