package com.github.ramonrabello.favoritehero.data.repository.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * DAO for operations related to [FavoriteHero]s.
 */
@Dao
interface FavoriteHeroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(hero: FavoriteHero): Long

    @Delete
    fun delete(favoriteHero: FavoriteHero): Int

    @Query("SELECT * FROM heroes ORDER BY name ASC")
    fun loadAllFavoriteHeroes(): Maybe<List<FavoriteHero>>

    @Query("SELECT * FROM heroes WHERE name LIKE '%' || :name || '%' ORDER BY name ASC")
    fun searchHeroByName(name: String): Maybe<List<FavoriteHero>>

    @Query("SELECT * FROM heroes WHERE id = :heroId")
    fun findHeroById(heroId: Long): Single<FavoriteHero>
}