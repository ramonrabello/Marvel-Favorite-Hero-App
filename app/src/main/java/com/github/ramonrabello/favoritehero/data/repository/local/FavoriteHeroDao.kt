package com.github.ramonrabello.favoritehero.data.repository.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero

/**
 * DAO for operations related to [CharacterItem]s.
 */
@Dao
interface FavoriteHeroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(hero: FavoriteHero): Long

    @Delete
    fun delete(favoriteHero: FavoriteHero): Int

    @Query("SELECT * FROM heroes ORDER BY name ASC")
    fun allHeroes(): LiveData<List<FavoriteHero>>

    @Query("SELECT * FROM heroes WHERE name LIKE '%' || :name || '%' ORDER BY name ASC")
    fun searchHeroByName(name: String): LiveData<List<FavoriteHero>>

    @Query("SELECT * FROM heroes WHERE id = :heroId")
    fun findHeroById(heroId: Long): FavoriteHero?
}