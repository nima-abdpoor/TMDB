package com.nima.tmdb.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nima.tmdb.R
import com.nima.tmdb.models.Result
import com.nima.tmdb.utils.Constants
import kotlinx.android.synthetic.main.layout_movie_list_item.view.*

class MovieListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Result>() {

        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.equals(newItem)
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_movie_list_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieListViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Result>) {
        differ.submitList(list)
    }

    class MovieListViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {
        val TAG = "MovieListViewHolder"

        fun bind(item: Result) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            setupView(itemView,item)
        }

        private fun setupView(itemView: View, item: Result) {
            itemView.card_view.animation =
                AnimationUtils.loadAnimation(itemView.context,R.anim.card_view_anim_one)
            itemView.movie_title.text = item.title
            itemView.movie_description.text = getOverview(item)
            itemView.release_date.text = item.release_date.toString()
            Glide.with(itemView.context)
                .load(Constants.IMAGE_BASE_URL + item.posterPath)
                .into(itemView.movie_image)
        }

        private fun getOverview(item: Result): String {
            try {
                return item.overview?.substring(0, 20) + "..."
            } catch (s: StringIndexOutOfBoundsException) {
                Log.d(TAG, "getOverview: $s")
            }
            return "..."
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Result)
    }
}
