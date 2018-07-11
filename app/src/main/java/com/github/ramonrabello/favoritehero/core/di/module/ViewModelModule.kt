package com.github.ramonrabello.favoritehero.core.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.ramonrabello.favoritehero.core.di.annotation.ViewModelKey
import com.github.ramonrabello.favoritehero.core.viewmodel.ViewModelFactory
import com.github.ramonrabello.favoritehero.detail.CharacterDetailViewModel
import com.github.ramonrabello.favoritehero.favorites.FavoriteHeroListViewModel
import com.github.ramonrabello.favoritehero.heroes.FavoriteHeroViewModel
import com.github.ramonrabello.favoritehero.heroes.HeroListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Module that provides ViewModel instances.
 */
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HeroListViewModel::class)
    abstract fun bindHeroListViewModel(heroListViewModel: HeroListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteHeroViewModel::class)
    abstract fun bindFavoriteHeroViewModel(favoriteHeroViewModel: FavoriteHeroViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteHeroListViewModel::class)
    abstract fun bindFavoriteHeroListViewModel(favoriteHeroListViewModel: FavoriteHeroListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailViewModel::class)
    abstract fun bindCharacterDetailViewModel(characterDetailViewModel: CharacterDetailViewModel) : ViewModel
}