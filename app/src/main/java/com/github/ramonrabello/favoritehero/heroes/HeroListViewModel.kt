package com.github.ramonrabello.favoritehero.heroes

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.core.ktx.commonSubscribe
import com.github.ramonrabello.favoritehero.data.model.Character
import com.github.ramonrabello.favoritehero.data.repository.remote.MarvelRemoteRepository
import com.malinskiy.superrecyclerview.OnMoreListener
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * View model for [HeroListFragment] that call repository and notifies the View about
 * updates using [MutableLiveData].
 */
class HeroListViewModel @Inject internal constructor(private val repository: MarvelRemoteRepository) : ViewModel(), OnMoreListener {

    val allHeroesLiveData = MutableLiveData<List<Character>>()
    val searchResultsLiveData = MutableLiveData<List<Character>>()
    val errorLiveData = MutableLiveData<Int>()
    private val compositeDisposable = CompositeDisposable()
    var currentOffset: Int = 0

    var isSearching: Boolean = false

    companion object {
        const val TAG = "HeroListViewModel"
        const val RESULTS_OFFSET = 20
    }

    fun loadHeroes(offset: Int = 0) {
        val disposable = repository.getAllCharacters(offset)
                .flatMap { item -> Maybe.just(item.data.results) }
                .commonSubscribe(
                        { characters -> allHeroesLiveData.postValue(characters) },
                        { errorLiveData.postValue(R.string.network_request_error) }
                )
        compositeDisposable.add(disposable)
    }

    fun searchHeroes(query: String) {
        currentOffset = 0
        if (query.isNotEmpty()) {
            val disposable = repository.getCharactersByName(query)
                    .toObservable()
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .filter { query.length > 5 }
                    .flatMap { item -> Observable.just(item.data.results) }
                    .commonSubscribe(
                            { characters -> searchResultsLiveData.postValue(characters) },
                            {
                                errorLiveData.postValue(R.string.network_request_error)
                            }
                    )
            compositeDisposable.add(disposable)
        } else {
            isSearching = false
            loadHeroes()
        }
    }

    override fun onMoreAsked(overallItemsCount: Int, itemsBeforeMore: Int, maxLastVisiblePosition: Int) {
        if (itemsBeforeMore + maxLastVisiblePosition >= overallItemsCount) {
            currentOffset = currentOffset.plus(RESULTS_OFFSET)
            loadHeroes(currentOffset)
        }
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared()")
        // clear disposables to avoid memory leaks
        compositeDisposable.clear()
    }
}