package com.github.ramonrabello.favoritehero.core.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Base class for [RecyclerView.ViewHolder]s.
 */
abstract class BaseViewHolder<T>(itemView:View) : RecyclerView.ViewHolder(itemView) {
    protected val context: Context = itemView.context
    protected val resources = context.resources
    abstract fun bind(item:T)
}