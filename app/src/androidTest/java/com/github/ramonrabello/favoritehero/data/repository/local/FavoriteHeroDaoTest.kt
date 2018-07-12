package com.github.ramonrabello.favoritehero.data.repository.local

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.EmptyResultSetException
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.emptyCollectionOf
import org.hamcrest.Matchers.hasSize
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID


/**
 * Unit tests related to [FavoriteHeroDao] class.
 */
@RunWith(AndroidJUnit4::class)
class FavoriteHeroDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: HeroDatabase
    private lateinit var heroDao: FavoriteHeroDao

    @Before
    fun beforeTest() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), HeroDatabase::class.java)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build()
        heroDao = database.heroDao()
    }

    @Test
    fun shouldInsertFavoriteHero() {
        val favoriteHero = FavoriteHero(UUID.randomUUID().mostSignificantBits, name = "Iron Man", description = "My favorite super hero", thumbnail = "anyimage.jpg")
        Single.just(heroDao.insert(favoriteHero)).test().assertComplete()
    }

    @Test
    fun shouldInsertAndGetFavoriteHero() {
        val favoriteHero = FavoriteHero(UUID.randomUUID().mostSignificantBits, name = "Iron Man", description = "My favorite super hero", thumbnail = "anyimage.jpg")
        heroDao.insert(favoriteHero)
        heroDao.findHeroById(favoriteHero.id).test().assertValue { valuePredicate -> favoriteHero == valuePredicate }
    }

    @Test
    fun shouldDeleteFavoriteHero() {
        val favoriteHero = FavoriteHero(UUID.randomUUID().mostSignificantBits, name = "Iron Man", description = "My favorite super hero", thumbnail = "anyimage.jpg")
        heroDao.insert(favoriteHero)
        heroDao.delete(favoriteHero)
        heroDao.findHeroById(favoriteHero.id).test().assertNoValues()
    }

    @Test
    fun shouldGetAllHeroes() {
        val allHeroes = mutableListOf(
                FavoriteHero(UUID.randomUUID().mostSignificantBits, name = "Iron Man", description = "My favorite super hero", thumbnail = "http://www.somedomain.com/img/ironman.jpg"),
                FavoriteHero(UUID.randomUUID().mostSignificantBits, name = "Captain America", description = "My second favorite super hero", thumbnail = "http://www.somedomain.com/img/captain_america.jpg"),
                FavoriteHero(UUID.randomUUID().mostSignificantBits, name = "Incredible Hulk", description = "My third favorite super hero", thumbnail = "http://www.somedomain.com/img/incredible_hulk.jpg"),
                FavoriteHero(UUID.randomUUID().mostSignificantBits, name = "Spider-Man", description = "My fourth favorite super hero", thumbnail = "http://www.somedomain.com/img/spider_man.jpg"))
        allHeroes.forEach { hero -> heroDao.insert(hero) }
        heroDao.allHeroes().observeForever { it ->
            assertThat(it, `is`(not(emptyCollectionOf(FavoriteHero::class.java))))
            assertThat(it, `is`(hasSize(allHeroes.size)))
        }
    }

    @Test
    fun getFavoriteHeroByIdWithDatabaseEmpty() {
        val favoriteHero = FavoriteHero(UUID.randomUUID().mostSignificantBits, name = "Iron Man", description = "My favorite super hero", thumbnail = "anyimage.jpg")
        heroDao.findHeroById(favoriteHero.id).test().assertError { valuePredicate -> valuePredicate is EmptyResultSetException }
    }

    @After
    fun afterTest() {
        database.close()
    }
}