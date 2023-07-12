package com.upjers.zoo2animalpa.ui.chat_bot.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.upjers.zoo2animalpa.databinding.ChatItemBinding
import com.upjers.zoo2animalpa.model.chat.ChatMessage

class ChatGPTAdapter(private val onCardClick: OnChatSuggestionItemClickListener) :
    RecyclerView.Adapter<ChatGPTAdapter.ChatsViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffUtil)

    var chatDataSet: List<ChatMessage>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    interface OnChatSuggestionItemClickListener {
        fun onChatSuggestionClicked(cardView: View, refillId: String, message: String)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ChatsViewHolder {
        val itemBinding = ChatItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )

        return ChatsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        chatViewHolder: ChatsViewHolder,
        position: Int
    ) {
        val chatMessage = chatDataSet[position]
        chatViewHolder.bind(chatMessage, onCardClick)
    }

    override fun getItemCount() = chatDataSet.size

    inner class ChatsViewHolder(private val itemBinding: ChatItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(chatMessage: ChatMessage, clickListener: OnChatSuggestionItemClickListener) {
            itemBinding.run {
                if (chatMessage.sentBy == ChatMessage.SENT_BY_USER) {
                    itemBinding.chatBotItem.visibility = View.GONE
                    itemBinding.chatUserItem.visibility = View.VISIBLE
                    itemBinding.userChatText.text = chatMessage.message
                } else {
                    itemBinding.chatUserItem.visibility = View.GONE
                    itemBinding.chatBotItem.visibility = View.VISIBLE
                    itemBinding.botChatText.text = chatMessage.message
                }
            }

            itemView.setOnLongClickListener {
                clickListener.onChatSuggestionClicked(itemView, chatMessage.id, chatMessage.message)
                return@setOnLongClickListener true
            }
        }
    }
}