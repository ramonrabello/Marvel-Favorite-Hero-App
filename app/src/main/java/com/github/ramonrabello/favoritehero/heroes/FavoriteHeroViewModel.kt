package com.github.ramonrabello.favoritehero.heroes

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.persistence.room.EmptyResultSetException
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.core.ktx.commonSubscribe
import com.github.ramonrabello.favoritehero.data.repository.local.FavoriteHeroLocalRepository
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

/**
 * View model to handle actions to favorite/unfavorite hero.
 */
class FavoriteHeroViewModel @Inject internal constructor(val repository: FavoriteHeroLocalRepository) : ViewModel() {

    val favoriteIconStateLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<Int>()

    fun addOrRemoveFavoriteHero(favoriteHero: FavoriteHero) {
        launch {
            repository.findById(favoriteHero.id)
                    .commonSubscribe(
                            {
                                if (it.id == favoriteHero.id) {
                                    repository.delete(favoriteHero)
                                    favoriteIconStateLiveData.postValue(false)
                                }
                            },
                            { error ->
                                when (error) {
                                    is EmptyResultSetException -> {
                                        repository.insert(favoriteHero)
                                        favoriteIconStateLiveData.postValue(true)
                                    }
                                    else -> errorLiveData.postValue(R.string.favorite_hero_error)
                                }
                            }
                    )
        }
    }

    fun observeFavoriteHeroState(favoriteHero: FavoriteHero) {
        launch {
            repository.findById(favoriteHero.id)
                    .commonSubscribe(
                            { foundFavoriteHero -> favoriteIconStateLiveData.postValue(foundFavoriteHero.id == favoriteHero.id) },
                            { error ->
                                when (error) {
                                    is EmptyResultSetException -> favoriteIconStateLiveData.postValue(false)
                                    else -> errorLiveData.postValue(R.string.favorite_hero_error)
                                }
                            }
                    )
        }
    }
}