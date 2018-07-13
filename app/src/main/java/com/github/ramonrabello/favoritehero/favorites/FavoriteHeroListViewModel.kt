package com.github.ramonrabello.favoritehero.favorites

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.core.ktx.commonSubscribe
import com.github.ramonrabello.favoritehero.data.repository.local.FavoriteHeroLocalRepository
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * View model for [FavoriteHeroListFragment] that call repository and notifies the View about
 * updates using [MutableLiveData].
 */
class FavoriteHeroListViewModel @Inject internal constructor(val repository: FavoriteHeroLocalRepository) : ViewModel() {

    val favoriteHeroesLiveData = MutableLiveData<List<FavoriteHero>>()
    val errorLiveData = MutableLiveData<Int>()
    private val compositeDisposable = CompositeDisposable()

    companion object {
        const val TAG = "FavoriteViewModel"
    }

    fun loadAllHeroes() {
        repository.loadAllHeroes().commonSubscribe(
                        { allHeroes -> favoriteHeroesLiveData.postValue(allHeroes) },
                        { errorLiveData.postValue(R.string.favorite_hero_list_error) })
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared()")
        compositeDisposable.clear()
    }
}