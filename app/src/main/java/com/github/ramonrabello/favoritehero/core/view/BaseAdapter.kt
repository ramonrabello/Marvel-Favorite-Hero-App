package com.github.ramonrabello.favoritehero.core.view

import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Base class for [RecyclerView.Adapter]s.
 */
abstract class BaseAdapter<T, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>() {

    companion object {
        const val TAG = "BaseAdapter"
    }

    private val items = mutableListOf<T>()

    protected fun getItem(position: Int) = items[position]

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    protected fun addItem(newItem: T, addInFront: Boolean = true) {
        var addedItemPosition = 0
        if (!items.contains(newItem)) {
            if (addInFront) {
                items.add(addedItemPosition, newItem)
            } else {
                items.add(newItem)
                addedItemPosition = items.lastIndex
            }
            notifyItemInserted(addedItemPosition)
        }
    }

    fun addItems(newItems: List<T>) {
        val positionStart = if (items.isEmpty()) {
            0
        } else {
            newItems.size.plus(1)
        }
        items.addAll(newItems)
        notifyItemRangeInserted(positionStart, newItems.size)
    }

    fun updateItem(item: T) {
        val itemPosition = items.indexOf(item)
        items[itemPosition] = item
        Log.d(TAG, "item $item successfully updated.")
        notifyItemChanged(itemPosition)
    }

    fun removeItem(item: T) {
        val itemPosition = items.indexOf(item)
        if (items.remove(item)) {
            Log.d(TAG, "item $item successfully removed.")
        }
        notifyItemRemoved(itemPosition)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}