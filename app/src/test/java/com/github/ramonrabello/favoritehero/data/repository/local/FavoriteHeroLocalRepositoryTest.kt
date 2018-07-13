package com.github.ramonrabello.favoritehero.data.repository.local

import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import kotlinx.coroutines.experimental.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Unit tests for [FavoriteHeroLocalRepository] class.
 */
class FavoriteHeroLocalRepositoryTest {

    @Mock
    lateinit var dao: FavoriteHeroDao
    @Mock
    private lateinit var hero: FavoriteHero
    private lateinit var repository: FavoriteHeroLocalRepository

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
        repository = FavoriteHeroLocalRepository(dao)
    }

    @Test
    fun `given a hero then should verify if it was inserted`() {
        launch {
            repository.insert(hero)
            verify(dao).insert(hero)
        }
    }

    @Test
    fun `given a hero then should verify if it was deleted`() {
        repository = FavoriteHeroLocalRepository(dao)
        repository.delete(hero)
        verify(dao).delete(hero)
    }

    @Test
    fun `verify search heroes by name`() {
        repository = FavoriteHeroLocalRepository(dao)
        launch {
            repository.searchByName("Spider-Man")
            verify(dao).searchHeroByName(hero.name)
        }
    }

    @Test
    fun `given a hero id should test if it was found`() {
        repository = FavoriteHeroLocalRepository(dao)
        launch {
            repository.findById(hero.id)
            verify(dao).findHeroById(hero.id)
        }
    }

    @Test
    fun `should get all heroes from repository`() {
        repository = FavoriteHeroLocalRepository(dao)
        launch {
            repository.loadAllHeroes()
            verify(dao).loadAllFavoriteHeroes()
        }
    }
}