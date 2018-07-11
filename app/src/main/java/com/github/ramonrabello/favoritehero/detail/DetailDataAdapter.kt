package com.github.ramonrabello.favoritehero.detail

import android.view.View
import android.view.ViewGroup
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.core.ktx.inflate
import com.github.ramonrabello.favoritehero.core.ktx.load
import com.github.ramonrabello.favoritehero.core.ktx.show
import com.github.ramonrabello.favoritehero.core.ktx.toTypeface
import com.github.ramonrabello.favoritehero.core.view.BaseAdapter
import com.github.ramonrabello.favoritehero.core.view.BaseViewHolder
import com.github.ramonrabello.favoritehero.data.model.DetailData
import kotlinx.android.synthetic.main.detail_data_item.view.detail_data_description
import kotlinx.android.synthetic.main.detail_data_item.view.detail_data_image
import kotlinx.android.synthetic.main.detail_data_item.view.detail_data_title

/**
 * Adapter that display a comic item.
 */
class DetailDataAdapter : BaseAdapter<DetailData, DetailDataAdapter.DetailDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailDataViewHolder {
        val itemView = parent.inflate(R.layout.detail_data_item)
        return DetailDataViewHolder(itemView)
    }

    inner class DetailDataViewHolder(itemView: View) : BaseViewHolder<DetailData>(itemView) {
        override fun bind(item: DetailData) {
            itemView.apply {
                detail_data_title.text = item.title
                detail_data_description.text = if (item.description == null || item.description.isEmpty()){
                    context.getString(R.string.detail_data_description_empty)
                } else {
                    item.description
                }

                item.thumbnail?.let {
                    detail_data_image.show()
                    detail_data_image.load(item.thumbnail.toString())
                }
                detail_data_title.toTypeface("OpenSans-Regular")
                detail_data_description.toTypeface("OpenSans-Light")
            }
        }
    }
}