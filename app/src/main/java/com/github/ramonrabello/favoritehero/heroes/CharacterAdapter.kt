package com.github.ramonrabello.favoritehero.heroes

import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.FragmentActivity
import android.view.ViewGroup
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.core.ktx.inflate
import com.github.ramonrabello.favoritehero.core.ktx.obtainViewModel
import com.github.ramonrabello.favoritehero.core.view.BaseAdapter
import com.github.ramonrabello.favoritehero.core.view.BaseViewHolder
import com.github.ramonrabello.favoritehero.data.model.Character

/**
 * Adapter that holds a list of [Character].
 */
class CharacterAdapter(private val viewModelFactory: ViewModelProvider.Factory) :
        BaseAdapter<Character,
                BaseViewHolder<Character>>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CharacterViewHolder {
        val itemView = parent.inflate(R.layout.hero_item)
        val viewModel = (parent.context as FragmentActivity).obtainViewModel(viewModelFactory, FavoriteHeroViewModel::class.java)
        return CharacterViewHolder(itemView, viewModel)
    }
}