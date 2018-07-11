package com.github.ramonrabello.favoritehero.favorites

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.github.ramonrabello.favoritehero.core.ktx.load
import com.github.ramonrabello.favoritehero.core.ktx.toTypeface
import com.github.ramonrabello.favoritehero.core.view.BaseViewHolder
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import com.github.ramonrabello.favoritehero.detail.CharacterDetailActivity
import kotlinx.android.synthetic.main.hero_item.view.hero_description
import kotlinx.android.synthetic.main.hero_item.view.hero_image
import kotlinx.android.synthetic.main.hero_item.view.hero_name
import android.support.v4.util.Pair as PairCompat

/**
 * View holder that display a favorite hero item.
 */
class FavoriteHeroViewHolder(itemView: View) : BaseViewHolder<FavoriteHero>(itemView) {

    override fun bind(item: FavoriteHero) {
        itemView.apply {
            tag = item
            hero_name.text = item.name
            hero_description.text = item.description
            hero_image.load(item.thumbnail)
            hero_name.toTypeface("OpenSans-SemiBold")
            hero_description.toTypeface("OpenSans-Regular")
        }
        itemView.setOnClickListener {
            val intent = Intent(context, CharacterDetailActivity::class.java)
            intent.putExtra(CharacterDetailActivity.EXTRA_FAVORITE_HERO, item)

            val activity = itemView.context as Activity
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    PairCompat.create(itemView.hero_name as View, item.name),
                    PairCompat.create(itemView.hero_description as View, item.description),
                    PairCompat.create(itemView.hero_image as View, item.thumbnail))
            ContextCompat.startActivity(activity, intent, options.toBundle())
        }
    }
}