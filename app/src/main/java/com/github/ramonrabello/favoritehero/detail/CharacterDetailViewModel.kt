package com.github.ramonrabello.favoritehero.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.core.ktx.commonSubscribe
import com.github.ramonrabello.favoritehero.data.model.DetailData
import com.github.ramonrabello.favoritehero.data.repository.remote.MarvelRemoteRepository
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * View model that handles character details.
 */
class CharacterDetailViewModel @Inject internal constructor(val repository: MarvelRemoteRepository) : ViewModel() {

    val characterComicsLiveData = MutableLiveData<List<DetailData>>()
    val characterEventsLiveData = MutableLiveData<List<DetailData>>()
    val characterStoriesLiveData = MutableLiveData<List<DetailData>>()
    val characterSeriesLiveData = MutableLiveData<List<DetailData>>()
    val errorLiveData = MutableLiveData<Int>()
    private val compositeDisposable = CompositeDisposable()

    companion object {
        const val TAG = "CharDetailViewModel"
        const val RESULTS_SIZE = 3
    }

    /**
     * Load all character details asynchronously in order to provide to the user as soon
     * as the data are available (comics, events, stories and series).
     */
    fun loadCharacterDetails(characterId: Long) {
        loadCharacterComics(characterId)
        loadCharacterEvents(characterId)
        loadCharacterStories(characterId)
        loadCharacterSeries(characterId)
    }

    private fun loadCharacterSeries(characterId: Long) {
        val disposable = repository.getCharacterSeries(characterId)
                .flatMap { item -> Maybe.just(item.data.results) }
                .commonSubscribe(
                        { series -> characterSeriesLiveData.postValue(series.take(RESULTS_SIZE)) },
                        { errorLiveData.postValue(R.string.character_detail_loading_error) }
                )
        compositeDisposable.add(disposable)
    }

    private fun loadCharacterStories(characterId: Long) {
        val disposable = repository.getCharacterStories(characterId)
                .flatMap { item -> Maybe.just(item.data.results) }
                .commonSubscribe(
                        { stories -> characterStoriesLiveData.postValue(stories.take(RESULTS_SIZE)) },
                        { errorLiveData.postValue(R.string.character_detail_loading_error) }
                )
        compositeDisposable.add(disposable)
    }

    private fun loadCharacterEvents(characterId: Long) {
        val disposable = repository.getCharacterEvents(characterId)
                .flatMap { item -> Maybe.just(item.data.results) }
                .commonSubscribe(
                    { events -> characterEventsLiveData.postValue(events.take(RESULTS_SIZE)) },
                    { errorLiveData.postValue(R.string.character_detail_loading_error) }
                )
        compositeDisposable.add(disposable)
    }

    private fun loadCharacterComics(characterId: Long) {
        val disposable = repository.getCharacterComics(characterId)
                .flatMap { item -> Maybe.just(item.data.results) }
                .commonSubscribe(
                        { comics -> characterComicsLiveData.postValue(comics.take(RESULTS_SIZE)) },
                        { errorLiveData.postValue(R.string.character_detail_loading_error) }
                )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared()")
        // clear disposables to avoid memory leaks
        compositeDisposable.clear()
    }
}