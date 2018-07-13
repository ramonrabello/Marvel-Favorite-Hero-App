package com.github.ramonrabello.favoritehero.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.support.v4.app.FragmentActivity
import android.util.AttributeSet
import android.widget.ToggleButton
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.core.ktx.obtainViewModel
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import com.github.ramonrabello.favoritehero.heroes.FavoriteHeroViewModel
import dagger.android.AndroidInjection
import kotlinx.coroutines.experimental.runBlocking
import javax.inject.Inject

/**
 * Favorite view that handles favorite/unfavorite hero actions.
 */
class FavoriteView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ToggleButton(context, attrs, defStyle) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: FavoriteHeroViewModel
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        textOn = ""
        textOff = ""
        setBackgroundResource(R.drawable.favorite_icon_background)
        viewModel = (context as FragmentActivity).obtainViewModel(viewModelFactory, FavoriteHeroViewModel::class.java)
        val favoriteIconStateObserver = Observer<Boolean> { favorited -> favorited?.let { toggle() } }
        viewModel.favoriteIconStateLiveData.observe(context as FragmentActivity, favoriteIconStateObserver)
        val viewTag = tag as FavoriteHero
        viewModel.observeFavoriteHeroState(viewTag)
        setOnCheckedChangeListener { _, _ -> viewModel.addOrRemoveFavoriteHero(viewTag) }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewModel.favoriteIconStateLiveData.removeObservers(context as FragmentActivity)
    }
}