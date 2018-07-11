package com.github.ramonrabello.favoritehero.heroes

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.ramonrabello.favoritehero.data.repository.local.FavoriteHeroLocalRepository
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

/**
 * View model to handle actions to favorite/unfavorite hero.
 */
class FavoriteHeroViewModel @Inject constructor(val repository: FavoriteHeroLocalRepository) : ViewModel() {

    val favoriteIconStateLiveData = MutableLiveData<Boolean>()

    private fun isFavoriteHero(favoriteHero: FavoriteHero): Boolean {
        val foundFavoriteHero = repository.findById(favoriteHero.id)
        foundFavoriteHero?.let {
            return foundFavoriteHero.id == favoriteHero.id
        }
        return false
    }

    fun addOrRemoveFavoriteHero(favoriteHero: FavoriteHero) {
        val isFavoriteHero = isFavoriteHero(favoriteHero)
        if (!isFavoriteHero) {
            repository.insert(favoriteHero)
        } else {
            repository.delete(favoriteHero)
        }
        favoriteIconStateLiveData.postValue(isFavoriteHero)
    }

    fun observeFavoriteHeroState(id: Long) {
        launch {
            favoriteIconStateLiveData.postValue(repository.findById(id)?.id == id)
        }
    }
}