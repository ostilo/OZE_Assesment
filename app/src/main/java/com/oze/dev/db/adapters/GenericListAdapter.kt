package com.oze.dev.db.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oze.dev.db.adapters.viewholder.GenericViewHolder

typealias OnItemClick<T> = (item: T, position: Int) -> Unit
class GenericListAdapter<T> internal constructor(
    @IdRes private val layout: Int,
    private val clickListener: OnItemClick<T>
) : RecyclerView.Adapter<GenericViewHolder<T>>() {

    var productItems: ArrayList<T> = ArrayList()
        set(value) {
            field = value
            val diffCallback =
                GenericViewHolder.ChildViewDiffUtils(filteredProductItems, productItems)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
            if (filteredProductItems.size > 0)
                filteredProductItems.clear()
            filteredProductItems.addAll(value)
        }

    private var filteredProductItems: ArrayList<T> = ArrayList()
        set(value) {
            field = value
            val diffCallback =
                GenericViewHolder.ChildViewDiffUtils(filteredProductItems, productItems)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
        }

    fun clearProductItem() {
        productItems.clear()
        val diffCallback =
            GenericViewHolder.ChildViewDiffUtils(filteredProductItems, productItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        filteredProductItems.clear()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GenericViewHolder<T> {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, i, viewGroup, false)
        return GenericViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenericViewHolder<T>, i: Int) {
        val item = filteredProductItems[i]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickListener(item, i)
        }
    }

    override fun getItemCount(): Int = filteredProductItems.size

    override fun getItemViewType(position: Int) = layout
}