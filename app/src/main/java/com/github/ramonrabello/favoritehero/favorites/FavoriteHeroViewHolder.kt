package com.github.ramonrabello.favoritehero.favorites

import android.app.Activity
import android.view.View
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.core.ktx.load
import com.github.ramonrabello.favoritehero.core.ktx.toTypeface
import com.github.ramonrabello.favoritehero.core.view.BaseViewHolder
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import kotlinx.android.synthetic.main.favorite_hero_item.view.hero_description
import kotlinx.android.synthetic.main.favorite_hero_item.view.hero_image
import kotlinx.android.synthetic.main.favorite_hero_item.view.hero_name
import android.support.v4.util.Pair as PairCompat

/**
 * View holder that display a favorite hero item.
 */
class FavoriteHeroViewHolder(itemView: View) : BaseViewHolder<FavoriteHero>(itemView) {

    override fun bind(item: FavoriteHero) {
        itemView.apply {
            tag = item
            hero_name.text = item.name
            hero_description.text = if (item.description == null || item.description.isEmpty()) {
                context.getString(R.string.detail_data_description_empty)
            } else {
                item.description
            }
            hero_image.load(itemView.context as Activity, item.thumbnail)
            hero_name.toTypeface("OpenSans-SemiBold")
            hero_description.toTypeface("OpenSans-Regular")
        }
    }
}