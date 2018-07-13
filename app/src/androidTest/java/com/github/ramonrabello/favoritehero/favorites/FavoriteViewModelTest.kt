package com.github.ramonrabello.favoritehero.favorites

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.test.runner.AndroidJUnit4
import com.github.ramonrabello.favoritehero.data.repository.local.FavoriteHeroLocalRepository
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import com.github.ramonrabello.favoritehero.heroes.FavoriteHeroViewModel
import kotlinx.coroutines.experimental.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Unit tests for [FavoriteHeroViewModel] class.
 */
@RunWith(AndroidJUnit4::class)
class FavoriteViewModelTest {

    //@Mock
    lateinit var repository: FavoriteHeroLocalRepository

    //@Mock
    lateinit var favoriteHero: FavoriteHero

    //@InjectMocks
    lateinit var favoriteHeroViewModel: FavoriteHeroViewModel

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun beforeTest() {
        //MockitoAnnotations.initMocks(this)
        repository = mock(FavoriteHeroLocalRepository::class.java)
        favoriteHero = mock(FavoriteHero::class.java)
        favoriteHeroViewModel = FavoriteHeroViewModel(repository)

        `when`(favoriteHero.id).thenReturn(1)
        `when`(favoriteHero.name).thenReturn("Spider-Man")
        `when`(favoriteHero.description).thenReturn("A great super hero")
        `when`(favoriteHero.thumbnail).thenReturn("spider-man.jpg")
    }

    @Test
    fun shouldVerifyIfFindByIdWasCalled() {
        favoriteHeroViewModel.addOrRemoveFavoriteHero(favoriteHero)
        launch { verify(repository).findById(anyLong()) }
    }
}