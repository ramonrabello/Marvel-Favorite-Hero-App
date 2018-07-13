package com.github.ramonrabello.favoritehero.heroes

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.support.test.runner.AndroidJUnit4
import com.github.ramonrabello.favoritehero.data.model.Character
import com.github.ramonrabello.favoritehero.data.repository.remote.MarvelRemoteRepository
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Unit tests for [HeroListViewModel] class.
 */
@RunWith(AndroidJUnit4::class)
class HeroListViewModelTest {

    @Mock
    lateinit var repository: MarvelRemoteRepository

    //@InjectMocks
    lateinit var heroListViewModel: HeroListViewModel

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
        heroListViewModel = HeroListViewModel(repository)
    }

//    @Test
//    fun `should test if LiveData has values after loading heroes`() {
//        val liveData = heroListViewModel.allHeroesLiveData.testObserver()
//        heroListViewModel.loadHeroes()
//        assertTrue(liveData.observedValues.isNotEmpty())
//    }
//
//    @Test
//    fun `should test if LiveData has values after searching heroes`() {
//        val liveData = heroListViewModel.searchResultsLiveData.testObserver()
//        heroListViewModel.searchHeroes("Spider-Man")
//        assertTrue(liveData.observedValues.isNotEmpty())
//    }
}