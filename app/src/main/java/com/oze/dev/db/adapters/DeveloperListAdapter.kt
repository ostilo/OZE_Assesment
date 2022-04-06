package com.oze.dev.db.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oze.dev.R
import com.oze.dev.core.iinterface.IDeveloper
import com.oze.dev.databinding.DeveloperViewBinding
import com.oze.dev.db.model.DeveloperInfo
import com.oze.dev.utils.BindingAdapters.loadGroupImage
import com.oze.dev.utils.BindingAdapters.loadShortString
import javax.inject.Inject

class DeveloperListAdapter @Inject constructor(private var callback:IDeveloper) : PagingDataAdapter<DeveloperInfo , RecyclerView.ViewHolder>(
    DataDifferentiator
) {

    object DataDifferentiator : DiffUtil.ItemCallback<DeveloperInfo>() {
        override fun areItemsTheSame(oldItem: DeveloperInfo, newItem: DeveloperInfo): Boolean { return oldItem.id == newItem.id
        }


        override fun areContentsTheSame(oldItem: DeveloperInfo, newItem: DeveloperInfo): Boolean {
            return oldItem.equals(newItem)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? DevelopersViewHolder)?.bind(item = getItem(position),callback = callback)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        DevelopersViewHolder(
            DeveloperViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )

  inner  class DevelopersViewHolder(view: DeveloperViewBinding) : RecyclerView.ViewHolder(view.root) {
                var bi = view
         fun bind(item: DeveloperInfo?,callback: IDeveloper) {
            loadShortString(bi.name,item?.login)
            loadGroupImage(bi.productImg,item?.avatarUrl)
            bi.productImg.setOnClickListener { item?.let { callback.openDeveloperDetails(item) } }
        }
    }
}