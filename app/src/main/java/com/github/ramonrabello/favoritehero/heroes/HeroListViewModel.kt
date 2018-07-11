package com.github.ramonrabello.favoritehero.heroes

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.github.ramonrabello.favoritehero.data.model.CharacterDataWrapper
import com.github.ramonrabello.favoritehero.data.model.Character
import com.github.ramonrabello.favoritehero.data.repository.remote.MarvelRemoteRepository
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * View model for [HeroListFragment].
 */
class HeroListViewModel @Inject constructor(private val repository: MarvelRemoteRepository) : ViewModel() {

    val allHeroesLiveData = MutableLiveData<List<Character>>()
    val errorLiveData = MutableLiveData<Throwable>()
    private val compositeDisposable = CompositeDisposable()

    companion object {
        const val TAG = "HeroListViewModel"
    }

    fun loadHeroes(offset: Int = 0) {
        repository.getAllCharacters(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : MaybeObserver<CharacterDataWrapper> {
                    override fun onSuccess(item: CharacterDataWrapper?) {
                        allHeroesLiveData.postValue(item?.data?.results)
                    }

                    override fun onSubscribe(d: Disposable?) {
                        compositeDisposable.add(d)
                    }

                    override fun onComplete() {}
                    override fun onError(e: Throwable) {
                        errorLiveData.postValue(e)
                    }
                })
    }

    fun searchHeroes(query:String) {
        repository.getCharactersByName(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : MaybeObserver<CharacterDataWrapper> {
                    override fun onSuccess(item: CharacterDataWrapper?) {
                        allHeroesLiveData.postValue(item?.data?.results)
                    }

                    override fun onSubscribe(d: Disposable?) {
                        compositeDisposable.add(d)
                    }

                    override fun onComplete() {}
                    override fun onError(e: Throwable) {
                        errorLiveData.postValue(e)
                    }
                })
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared()")
        compositeDisposable.dispose()
        super.onCleared()
    }
}