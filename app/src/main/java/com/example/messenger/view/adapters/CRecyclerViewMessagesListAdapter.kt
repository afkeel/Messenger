package com.example.messenger.view.adapters

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.databinding.MessagesListItemBinding
import com.example.messenger.model.CMessage
import org.joda.time.format.DateTimeFormat

class CRecyclerViewMessagesListAdapter(
    private var postList: List<CMessage>,
    private val listener : IClickListener?
    ) : RecyclerView.Adapter<CRecyclerViewMessagesListAdapter.ViewHolder>() {

    private val formatter = DateTimeFormat.forPattern("YYYY-MM-DD HH:mm")
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(val binding: MessagesListItemBinding) : RecyclerView
    .ViewHolder(binding.root) {
        //var post : CMessage? = null
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
        viewHolder.binding.tvMsgUserName.text = postList[position].userName
        viewHolder.binding.tvMsgContent.text = postList[position].msg
        viewHolder.binding.tvMsgDateTime.text = postList[position].dateTime.toString(formatter)

        //viewHolder.msg = msgList[position]
        viewHolder.binding.rlMessage.setOnLongClickListener {
            listener?.onLongItemClick(it, postList[position])
            true
        }
    }
    interface IClickListener{
        fun onLongItemClick(v : View, post : CMessage)
    }
    fun updateData(postList : List<CMessage>)
    {
        this.postList = postList
        notifyDataSetChanged()
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = postList.size
}