package com.github.ramonrabello.favoritehero.data.repository.local

import android.arch.lifecycle.LiveData
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import javax.inject.Inject

/**
 *
 */
class FavoriteHeroLocalRepository @Inject constructor(private val heroDao: FavoriteHeroDao) {

    fun insert(item: FavoriteHero) {
        launch { heroDao.insert(item) }
    }

    fun loadAllHeroes(): LiveData<List<FavoriteHero>> {
        return heroDao.allHeroes()
    }

    fun delete(item: FavoriteHero) {
        launch { heroDao.delete(item) }
    }

    fun searchByName(name: String): LiveData<List<FavoriteHero>> {
        return heroDao.searchHeroByName(name)
    }

    fun findById(itemId: Long): FavoriteHero? {
        return runBlocking { heroDao.findHeroById(itemId) }
    }
}