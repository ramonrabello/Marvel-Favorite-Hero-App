package com.github.ramonrabello.favoritehero.favorites

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.github.ramonrabello.favoritehero.data.repository.local.FavoriteHeroLocalRepository
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * View model for [FavoriteHeroListFragment].
 */
class FavoriteHeroListViewModel @Inject constructor(val repository: FavoriteHeroLocalRepository,
                                                    application: Application) : AndroidViewModel(application) {

    val errorLiveData = MutableLiveData<Throwable>()
    private val compositeDisposable = CompositeDisposable()

    companion object {
        const val TAG = "FavoriteViewModel"
    }

    fun observeFavoriteHeroes(): LiveData<List<FavoriteHero>> {
        return repository.loadAllHeroes()
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared()")
        compositeDisposable.dispose()
        super.onCleared()
    }
}