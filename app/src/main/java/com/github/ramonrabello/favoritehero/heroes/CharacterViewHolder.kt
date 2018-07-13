package com.github.ramonrabello.favoritehero.heroes

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.view.View
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.R.id.favorite_icon
import com.github.ramonrabello.favoritehero.core.ktx.load
import com.github.ramonrabello.favoritehero.core.ktx.toTypeface
import com.github.ramonrabello.favoritehero.core.view.BaseViewHolder
import com.github.ramonrabello.favoritehero.data.model.Character
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import com.github.ramonrabello.favoritehero.detail.CharacterDetailActivity
import kotlinx.android.synthetic.main.detail_data_item.view.detail_data_description
import kotlinx.android.synthetic.main.hero_item.view.favorite_icon
import kotlinx.android.synthetic.main.hero_item.view.hero_description
import kotlinx.android.synthetic.main.hero_item.view.hero_image
import kotlinx.android.synthetic.main.hero_item.view.hero_name
import android.support.v4.util.Pair as UtilPairCompat
import android.util.Pair as UtilPair


/**
 * View holder for a character item.
 */
class CharacterViewHolder(itemView: View,
                          private val onFavoriteItemClickLister:OnFavoriteItemListener,
                          private val favoriteHeroViewModel:FavoriteHeroViewModel) : BaseViewHolder<Character>(itemView) {

    override fun bind(item: Character) {
        val favoriteHero = FavoriteHero(item.id, item.name, item.description, item.thumbnail.toString())
        favoriteHeroViewModel.observeFavoriteHeroState(favoriteHero)
        favoriteHeroViewModel.favoriteIconStateLiveData.observe(context as FragmentActivity, Observer { isFavorite ->
            isFavorite?.let {
                if (isFavorite){
                    itemView.favorite_icon.setBackgroundResource(R.drawable.ic_heart_filled)
                } else {
                    itemView.favorite_icon.setBackgroundResource(R.drawable.ic_heart_outline)
                }
            }

        })
        itemView.apply {
            hero_name.text = item.name
            hero_description.text = if (item.description == null || item.description.isEmpty()) {
                context.getString(R.string.detail_data_description_empty)
            } else {
                item.description
            }
            hero_image.load(context as Activity, item.thumbnail.toString())
            favorite_icon.tag = favoriteHero

            favorite_icon.setOnCheckedChangeListener { _, isChecked ->
                favoriteHero.isFavorite = isChecked
                onFavoriteItemClickLister.onItemFavorited(favoriteHero, adapterPosition) }

            itemView.setOnClickListener {
                val intent = Intent(context, CharacterDetailActivity::class.java)
                intent.putExtra(CharacterDetailActivity.EXTRA_FAVORITE_HERO, favoriteHero)

                val activity = context as Activity

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

    /**
     * Listens to favorite icon clicks.
     */
    interface OnFavoriteItemListener {

        /**
         * Called when the favorite icon is clicked.
         * @param favoriteHero The [FavoriteHero] item clicked.
         */
        fun onItemFavorited(favoriteHero:FavoriteHero, itemPosition:Int)
    }
}