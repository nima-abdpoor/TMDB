package com.nima.tmdb.framewrok.presentation.createdlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nima.tmdb.R
import com.nima.tmdb.databinding.ItemCreatedListBinding
import com.nima.tmdb.business.domain.model.account.lists.CreatedListsResult
import com.nima.tmdb.utils.Constants


class CreatedListAdapter(
    private val interaction: Interaction? = null,
    private val glide: RequestManager
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val diffCallBack = object : DiffUtil.ItemCallback<CreatedListsResult>() {

        override fun areItemsTheSame(
            oldItem: CreatedListsResult,
            newItem: CreatedListsResult
        ): Boolean = oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: CreatedListsResult,
            newItem: CreatedListsResult
        ): Boolean = oldItem.equals(newItem)

    }
    private val differ = AsyncListDiffer(this, diffCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CreatedListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_created_list,
                parent,
                false
            ),
            interaction,
            glide
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CreatedListViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<CreatedListsResult>) {
        differ.submitList(list)
    }

    class CreatedListViewHolder(
        itemView: View,
        private val interaction: Interaction?,
        private val glide: RequestManager
    ) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCreatedListBinding.bind(itemView)
        fun bind(item: CreatedListsResult) = with(itemView) {
            binding.apply {
                cardCreatedListIItem.setOnClickListener {
                    interaction?.onItemSelected(bindingAdapterPosition, item)
                }
                txtCreatedListIName.text = item.name
                txtCreatedListIItems.text = item.itemCount.toString()
                txtCreatedListIDescription.text = item.description
                glide.load(Constants.IMAGE_BASE_URL + item.posterPath)
                    .into(imgCreatedListIImage)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: CreatedListsResult)
    }
}