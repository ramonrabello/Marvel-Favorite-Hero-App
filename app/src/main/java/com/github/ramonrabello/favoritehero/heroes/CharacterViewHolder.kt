package com.github.ramonrabello.favoritehero.heroes

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.github.ramonrabello.favoritehero.core.ktx.load
import com.github.ramonrabello.favoritehero.core.ktx.toTypeface
import com.github.ramonrabello.favoritehero.core.view.BaseViewHolder
import com.github.ramonrabello.favoritehero.data.model.Character
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import com.github.ramonrabello.favoritehero.detail.CharacterDetailActivity
import kotlinx.android.synthetic.main.hero_item.view.favorite_icon
import kotlinx.android.synthetic.main.hero_item.view.hero_description
import kotlinx.android.synthetic.main.hero_item.view.hero_image
import kotlinx.android.synthetic.main.hero_item.view.hero_name
import kotlinx.coroutines.experimental.launch
import android.support.v4.util.Pair as UtilPairCompat
import android.util.Pair as UtilPair


/**
 * View holder for a character item.
 */
class CharacterViewHolder(itemView: View, private val viewModel: FavoriteHeroViewModel) : BaseViewHolder<Character>(itemView) {

    override fun bind(item: Character) {
        val favoriteHero = FavoriteHero(item.id, item.name, item.description, item.thumbnail.toString())
        itemView.apply {
            hero_name.text = item.name
            hero_description.text = item.description
            hero_image.load(item.thumbnail.toString())
            favorite_icon.tag = favoriteHero
            favorite_icon.setOnCheckedChangeListener { _, _ ->
                launch { viewModel.addOrRemoveFavoriteHero(favoriteHero) }
            }

            itemView.setOnClickListener {
                val intent = Intent(context, CharacterDetailActivity::class.java)
                intent.putExtra(CharacterDetailActivity.EXTRA_FAVORITE_HERO, favoriteHero)

                val activity = itemView.context as Activity

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        UtilPairCompat.create(itemView.hero_name as View, favoriteHero.name),
                        UtilPairCompat.create(itemView.hero_description as View, favoriteHero.description),
                        UtilPairCompat.create(itemView.hero_image as View, favoriteHero.thumbnail))
                ContextCompat.startActivity(activity, intent, options.toBundle())
            }
            hero_name.toTypeface("OpenSans-SemiBold")
            hero_description.toTypeface("OpenSans-Regular")
        }
    }
}