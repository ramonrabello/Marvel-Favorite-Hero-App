package com.github.ramonrabello.favoritehero.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.github.ramonrabello.favoritehero.data.model.ComicDataWrapper
import com.github.ramonrabello.favoritehero.data.model.DetailData
import com.github.ramonrabello.favoritehero.data.model.EventDataWrapper
import com.github.ramonrabello.favoritehero.data.model.SeriesDataWrapper
import com.github.ramonrabello.favoritehero.data.model.StoryDataWrapper
import com.github.ramonrabello.favoritehero.data.repository.remote.MarvelRemoteRepository
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CharacterDetailViewModel @Inject constructor(val repository: MarvelRemoteRepository) : ViewModel() {

    val characterComicsLiveData = MutableLiveData<List<DetailData>>()
    val characterEventsLiveData = MutableLiveData<List<DetailData>>()
    val characterStoriesLiveData = MutableLiveData<List<DetailData>>()
    val characterSeriesLiveData = MutableLiveData<List<DetailData>>()
    val compositeDisposable = CompositeDisposable()

    companion object {
        const val TAG = "CharDetailViewModel"
    }

    fun loadCharacterDetails(characterId: Long) {
        loadCharacterComics(characterId)
        loadCharacterEvents(characterId)
        loadCharacterStories(characterId)
        loadCharacterSeries(characterId)
    }

    private fun loadCharacterSeries(characterId: Long) {
        repository.getCharacterSeries(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : MaybeObserver<SeriesDataWrapper> {
                    override fun onSuccess(response: SeriesDataWrapper?) {
                        val results = response?.data?.results
                        results?.let {
                            if (results.size > 3) {
                                characterSeriesLiveData.postValue(results.take(3))
                            } else {
                                characterSeriesLiveData.postValue(results)
                            }
                        }
                    }

                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable?) { compositeDisposable.add(d) }
                    override fun onError(e: Throwable?) {
                        Log.d("ViewModel error: ", e?.message)
                    }
                })
    }

    private fun loadCharacterStories(characterId: Long) {
        repository.getCharacterStories(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : MaybeObserver<StoryDataWrapper> {
                    override fun onSuccess(response: StoryDataWrapper?) {
                        val results = response?.data?.results
                        results?.let {
                            if (results.size > 3) {
                                characterStoriesLiveData.postValue(results.take(3))
                            } else {
                                characterStoriesLiveData.postValue(results)
                            }
                        }
                    }

                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable?) {
                        compositeDisposable.add(d)
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("ViewModel error: ", e?.message)
                    }
                })
    }

    private fun loadCharacterEvents(characterId: Long) {
        repository.getCharacterEvents(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : MaybeObserver<EventDataWrapper> {
                    override fun onSuccess(response: EventDataWrapper?) {
                        val results = response?.data?.results
                        results?.let {
                            if (results.size > 3) {
                                characterEventsLiveData.postValue(results.take(3))
                            } else {
                                characterEventsLiveData.postValue(results)
                            }
                        }
                    }

                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable?) {
                        compositeDisposable.add(d)
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("ViewModel error: ", e?.message)
                    }
                })
    }

    private fun loadCharacterComics(characterId: Long) {
        repository.getCharacterComics(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : MaybeObserver<ComicDataWrapper> {
                    override fun onSuccess(response: ComicDataWrapper?) {
                        val results = response?.data?.results
                        results?.let {
                            if (results.size > 3) {
                                characterComicsLiveData.postValue(results.take(3))
                            } else {
                                characterComicsLiveData.postValue(results)
                            }
                        }
                    }

                    override fun onComplete() {}
                    override fun onSubscribe(d: Disposable?) {
                        compositeDisposable.add(d)
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("ViewModel error: ", e?.message)
                    }
                })
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared()")
        compositeDisposable.dispose()
        super.onCleared()
    }
}