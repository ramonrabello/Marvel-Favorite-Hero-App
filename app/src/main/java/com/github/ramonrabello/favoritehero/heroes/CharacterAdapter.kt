package com.github.ramonrabello.favoritehero.heroes

import android.arch.lifecycle.Observer
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.view.ViewGroup
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.core.ktx.inflate
import com.github.ramonrabello.favoritehero.core.view.BaseAdapter
import com.github.ramonrabello.favoritehero.core.view.BaseViewHolder
import com.github.ramonrabello.favoritehero.data.model.Character
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import kotlinx.android.synthetic.main.hero_item.view.favorite_icon

/**
 * Adapter that holds a list of [Character].
 */
class CharacterAdapter(private val onFavoriteItemListener: CharacterViewHolder.OnFavoriteItemListener,
                       private val favoriteHeroViewModel: FavoriteHeroViewModel) :
        BaseAdapter<Character, BaseViewHolder<Character>>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CharacterViewHolder {
        val itemView = parent.inflate(R.layout.hero_item)
        return CharacterViewHolder(itemView, onFavoriteItemListener, favoriteHeroViewModel)
    }
}