package com.github.ramonrabello.favoritehero.core.di.module

import com.github.ramonrabello.favoritehero.favorites.FavoriteHeroListFragment
import com.github.ramonrabello.favoritehero.heroes.HeroListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeHeroListFragment() : HeroListFragment

    @ContributesAndroidInjector
    abstract fun contributeHeroDetailFragment() : FavoriteHeroListFragment
}
