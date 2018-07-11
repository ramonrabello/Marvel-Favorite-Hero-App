package com.github.ramonrabello.favoritehero.core.di.module

import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.github.ramonrabello.favoritehero.data.repository.local.FavoriteHeroDao
import com.github.ramonrabello.favoritehero.data.repository.local.FavoriteHeroLocalRepository
import com.github.ramonrabello.favoritehero.data.repository.local.HeroDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context) : RoomDatabase {
        return HeroDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideHeroDatabase(context: Context) : HeroDatabase {
        return HeroDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideFavoriteHeroDao(heroDatabase: HeroDatabase) : FavoriteHeroDao {
        return heroDatabase.heroDao()
    }

    @Provides
    @Singleton
    internal fun provideLocalHeroRepository(heroDao: FavoriteHeroDao): FavoriteHeroLocalRepository {
        return FavoriteHeroLocalRepository(heroDao)
    }
}