package com.nima.tmdb.adapters

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nima.tmdb.R
import com.nima.tmdb.models.trend.TrendModel
import com.nima.tmdb.utils.Constants
import kotlinx.android.synthetic.main.item_movie_category.view.*

class TrendMoviesAdapter(
    private val interaction: Interaction? = null,
    private val glide: RequestManager,
    private val ctx: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TrendModel>() {

        override fun areItemsTheSame(
            oldItem: TrendModel,
            newItem: TrendModel
        ): Boolean = oldItem.id == newItem.id


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
            glide,
            ctx
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
        private val glide: RequestManager,
        private val ctx: Context
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: TrendModel) = with(itemView) {
            itemView.btn_mainPageF_menu.setOnClickListener {
                val menu = PopupMenu(ctx, itemView.btn_mainPageF_menu, Gravity.RIGHT)
                menu.inflate(R.menu.main_page_item_menu)
                menu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.itemMenu_addToList -> {
                            interaction?.addToList(adapterPosition,item)
                            true
                        }
                        R.id.itemMenu_addToFavorite->{
                            interaction?.addToFavorite(adapterPosition,item)
                            true
                        }
                        R.id.itemMenu_addToWatchList ->{
                            interaction?.addToWatchList(adapterPosition,item)
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
                menu.show()
            }
            itemView.setOnClickListener { interaction?.onTrendItemSelected(adapterPosition, item) }
            itemView.apply {
                img_mainPageF_image.setOnClickListener {
                    interaction?.onTrendItemSelected(adapterPosition, item)
                }
                txt_movieCategoryI_title.text = item.title
                txt_movieCategoryI_date.text = item.releaseDate
                glide.load(Constants.IMAGE_BASE_URL + item.posterPath)
                    .into(img_mainPageF_image)
            }
        }
    }

    interface Interaction {
        fun onTrendItemSelected(position: Int, item: TrendModel)
        fun addToList(position: Int, item: TrendModel)
        fun addToFavorite(position: Int, item: TrendModel)
        fun addToWatchList(position: Int, item: TrendModel)
    }

}