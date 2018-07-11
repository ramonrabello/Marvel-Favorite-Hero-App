package com.github.ramonrabello.favoritehero.data.repository.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero

/**
 * Database definition for using Room.
 */
@Database(entities = [FavoriteHero::class], version = 1, exportSchema = false)
abstract class HeroDatabase : RoomDatabase() {

    /**
     * Retrieves [FavoriteHeroDao] in order to allow
     * SQL operations with the dao.
     */
    abstract fun heroDao(): FavoriteHeroDao

    /**
     * Singleton to retrieve a single object of [HeroDatabase].
     */
    companion object {
        fun getDatabase(context: Context) =
                synchronized(HeroDatabase::class) {
                    return@synchronized Room.databaseBuilder(context.applicationContext,
                            HeroDatabase::class.java, "hero_database")
                            .build()
                }
    }
}