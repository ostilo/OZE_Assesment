package com.oze.dev.db.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oze.dev.R
import com.oze.dev.core.iinterface.IFavourite
import com.oze.dev.databinding.FavouriteViewBinding
import com.oze.dev.db.model.DeveloperInfo
import com.oze.dev.utils.BindingAdapters
import com.oze.dev.utils.BindingAdapters.loadGroupImage
import com.oze.dev.utils.BindingAdapters.loadShortString
import javax.inject.Inject


class MainListSearchAdapter @Inject constructor(private val  callback:IFavourite) : PagingDataAdapter<DeveloperInfo, RecyclerView.ViewHolder>(DataDifferentiator) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? PopularMakesViewHolder)?.bind(item = getItem(position))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        PopularMakesViewHolder(
            FavouriteViewBinding.inflate(LayoutInflater.from(parent.context),parent,false
            )
        )

   inner class PopularMakesViewHolder(view:  FavouriteViewBinding) : RecyclerView.ViewHolder(view.root) {
        var bi = view
        fun bind(item: DeveloperInfo?){
            bi.name.text = item?.login
            loadGroupImage(bi.productImg, item?.avatarUrl)
            bi.delete.setOnClickListener {
                callback.deleteFavourite(item?.id!!,item?.login!!)
            }
            bi.productImg.setOnClickListener {
                item?.let { it1 -> callback.openDevDetails(it1) }
            }
        }
    }
    object DataDifferentiator : DiffUtil.ItemCallback<DeveloperInfo>() {
        override fun areItemsTheSame(oldItem: DeveloperInfo, newItem: DeveloperInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DeveloperInfo, newItem: DeveloperInfo): Boolean {
            return oldItem == newItem
        }
    }

}