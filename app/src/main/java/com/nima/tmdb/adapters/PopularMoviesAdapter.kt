package com.nima.tmdb.adapters

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.nima.tmdb.R
import com.nima.tmdb.databinding.ItemMovieCategoryBinding
import com.nima.tmdb.models.movie.popular.PopularModel
import com.nima.tmdb.utils.Constants


class PopularMoviesAdapter(
    private val interaction: Interaction? = null,
    private val glide: RequestManager,
    private val ctx: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PopularModel>() {

        override fun areItemsTheSame(
            oldItem: PopularModel,
            newItem: PopularModel
        ): Boolean = oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: PopularModel,
            newItem: PopularModel
        ): Boolean = oldItem.equals(newItem)

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return PopularMoviesViewHolder(
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
            is PopularMoviesViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<PopularModel?>) {
        differ.submitList(list)
    }

    class PopularMoviesViewHolder(
        itemView: View,
        private val interaction: Interaction?,
        private val glide: RequestManager,
        private val ctx: Context
    ) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemMovieCategoryBinding.bind(itemView)
        private lateinit var myPopupWindow: PopupWindow
        private val TAG = "PopularMoviesViewHolder"
        fun bind(item: PopularModel) = with(itemView) {
            setPopUpWindow()
            binding.btnMainPageFMenu.setOnClickListener {
                myPopupWindow.showAsDropDown(it,-80,0);
                //showAsDropDown(below which view you want to show as dropdown,horizontal position, vertical position)
//                val menu = PopupMenu(ctx, binding.btnMainPageFMenu, Gravity.RIGHT)
//                menu.inflate(R.menu.main_page_item_menu)
//                menu.setOnMenuItemClickListener {
//                    when(it.itemId){
//                        R.id.itemMenu_addToList -> {
//                            interaction?.addToList(adapterPosition,item)
//                            true
//                        }
//                        R.id.itemMenu_addToFavorite->{
//                            interaction?.addToFavorite(adapterPosition,item)
//                            true
//                        }
//                        R.id.itemMenu_addToWatchList ->{
//                            interaction?.addToWatchList(adapterPosition,item)
//                            true
//                        }
//                        else -> {
//                            false
//                        }
//                    }
//                }
//                menu.show()
            }
            itemView.setOnClickListener {
                interaction?.onPopularItemSelected(
                    adapterPosition,
                    item
                )
            }
            binding.apply {
                imgMainPageFImage.setOnClickListener {
                    interaction?.onPopularItemSelected(adapterPosition, item)
                }
                txtMovieCategoryITitle.text = item.title
                txtMovieCategoryIDate.text = item.releaseDate
                glide.load(Constants.IMAGE_BASE_URL + item.posterPath)
                    .into(imgMainPageFImage)
            }
        }

        private fun setPopUpWindow() {
            val inflater:LayoutInflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.popupmenu, null)
            myPopupWindow = PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        }
    }

    interface Interaction {
        fun onPopularItemSelected(position: Int, item: PopularModel)
        fun addToList(position: Int, item: PopularModel)
        fun addToFavorite(position: Int, item: PopularModel)
        fun addToWatchList(position: Int, item: PopularModel)
    }
}