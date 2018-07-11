package com.github.ramonrabello.favoritehero.view

import android.arch.lifecycle.Observer
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.util.AttributeSet
import android.widget.ToggleButton
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import com.github.ramonrabello.favoritehero.heroes.FavoriteHeroViewModel
import kotlinx.coroutines.experimental.runBlocking

/**
 * Favorite view that handles favorite/unfavorite hero actions.
 */
class FavoriteView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ToggleButton(context, attrs, defStyle) {

    lateinit var viewModel: FavoriteHeroViewModel
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        textOn = ""
        textOff = ""
        setBackgroundResource(R.drawable.favorite_icon_background)
        val favoriteIconStateObserver = Observer<Boolean> { favorited -> favorited?.let { isChecked = it } }
        viewModel.favoriteIconStateLiveData.observe(context as FragmentActivity, favoriteIconStateObserver)
        val viewTag = tag as FavoriteHero
        viewModel.observeFavoriteHeroState(viewTag.id)
        setOnCheckedChangeListener { _, _ -> runBlocking { viewModel.addOrRemoveFavoriteHero(viewTag) } }
    }
}