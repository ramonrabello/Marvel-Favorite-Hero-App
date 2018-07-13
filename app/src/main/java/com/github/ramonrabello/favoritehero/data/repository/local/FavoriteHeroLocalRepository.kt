package com.github.ramonrabello.favoritehero.data.repository.local

import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import io.reactivex.Maybe
import io.reactivex.Single
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

/**
 * Local repository that calls the [FavoriteHeroDao]
 * class related to persistence in Room.
 */
class FavoriteHeroLocalRepository @Inject constructor(private val heroDao: FavoriteHeroDao) {

    fun insert(item: FavoriteHero) {
        launch { heroDao.insert(item) }
    }

    fun loadAllHeroes(): Maybe<List<FavoriteHero>> {
        return heroDao.loadAllFavoriteHeroes()
    }

    fun delete(item: FavoriteHero) {
        launch { heroDao.delete(item) }
    }

    fun searchByName(name: String): Maybe<List<FavoriteHero>> {
        return heroDao.searchHeroByName(name)
    }

    suspend fun findById(itemId: Long): Single<FavoriteHero> {
        return withContext(DefaultDispatcher) { heroDao.findHeroById(itemId) }
    }
}