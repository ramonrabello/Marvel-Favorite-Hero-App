package com.github.ramonrabello.favoritehero.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.core.ktx.commonSubscribe
import com.github.ramonrabello.favoritehero.data.model.CharacterDetailsModel
import com.github.ramonrabello.favoritehero.data.model.ComicDataWrapper
import com.github.ramonrabello.favoritehero.data.model.DetailData
import com.github.ramonrabello.favoritehero.data.model.EventDataWrapper
import com.github.ramonrabello.favoritehero.data.model.SeriesDataWrapper
import com.github.ramonrabello.favoritehero.data.model.StoryDataWrapper
import com.github.ramonrabello.favoritehero.data.repository.remote.MarvelRemoteRepository
import io.reactivex.Maybe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function4
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
    }

    /**
     * Load all character details asynchronously in order to provide to the user as soon
     * as the data are available (comics, events, stories and series).
     */
    fun loadCharacterDetails(characterId: Long) {
        val disposable = Maybe.zip(repository.getCharacterComics(characterId),
                repository.getCharacterEvents(characterId),
                repository.getCharacterStories(characterId),
                repository.getCharacterSeries(characterId),
                Function4<ComicDataWrapper, EventDataWrapper, StoryDataWrapper, SeriesDataWrapper, CharacterDetailsModel> { comic, event, stories, series ->
                    createCharacterDetailsModel(comic, event, stories, series)
                }).commonSubscribe(
                { details ->
                    characterComicsLiveData.postValue(details.comics)
                    characterEventsLiveData.postValue(details.events)
                    characterStoriesLiveData.postValue(details.stories)
                    characterSeriesLiveData.postValue(details.series)
                },
                { errorLiveData.postValue(R.string.character_detail_loading_error) }
        )
        compositeDisposable.add(disposable)
    }

    private fun createCharacterDetailsModel(comic: ComicDataWrapper, event: EventDataWrapper, stories: StoryDataWrapper, series: SeriesDataWrapper): CharacterDetailsModel {
        return CharacterDetailsModel(comic.data.results, event.data.results, stories.data.results, series.data.results)
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared()")
        // clear disposables to avoid memory leaks
        compositeDisposable.clear()
    }
}