package com.github.ramonrabello.favoritehero.favorites

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import com.github.ramonrabello.favoritehero.core.ktx.inflate
import com.github.ramonrabello.favoritehero.core.view.BaseAdapter
import com.github.ramonrabello.favoritehero.heroes.FavoriteHeroViewModel

/**
 * Adapter that holds a list of [CharacterItem].
 */
class FavoriteHeroAdapter : BaseAdapter<FavoriteHero, FavoriteHeroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FavoriteHeroViewHolder {
        val itemView = parent.inflate(R.layout.favorite_hero_item)
        return FavoriteHeroViewHolder(itemView)
    }
}