package com.github.ramonrabello.favoritehero.core.di.module

import com.github.ramonrabello.favoritehero.MainActivity
import com.github.ramonrabello.favoritehero.detail.CharacterDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildActivityModule {
    @ContributesAndroidInjector(modules = [BuildFragmentModule::class])
    abstract fun contributeMainActivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun contributeCharacterDetailActivity() : CharacterDetailActivity
}