package com.nima.tmdb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nima.tmdb.R
import com.nima.tmdb.models.trend.TrendModel
import com.nima.tmdb.utils.Constants
import kotlinx.android.synthetic.main.item_movie_category.view.*

class TrendMoviesAdapter(private val interaction: Interaction? = null, private val glide: RequestManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TrendModel>() {

        override fun areItemsTheSame(
            oldItem: TrendModel,
            newItem: TrendModel
        ): Boolean  = oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: TrendModel,
            newItem: TrendModel
        ): Boolean = oldItem.equals(newItem)

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TrendMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie_category,
                parent,
                false
            ),
            interaction,
            glide
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TrendMovieViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun submitList(list: List<TrendModel?>) {
        differ.submitList(list)
    }
    class TrendMovieViewHolder(
        itemView: View,
        private val interaction: Interaction?,
        private val glide: RequestManager
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: TrendModel) = with(itemView) {
            itemView.setOnClickListener { interaction?.onItemSelected(adapterPosition, item) }
            itemView.apply {
                txt_movieCategoryI_title.text = item.title
                txt_movieCategoryI_date.text = item.releaseDate
                glide.load(Constants.IMAGE_BASE_URL + item.posterPath)
                    .into(img_mainPageF_image)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: TrendModel)
    }

}