package com.github.ramonrabello.favoritehero.core.di.component

import com.github.ramonrabello.favoritehero.FavoriteHeroApplication
import com.github.ramonrabello.favoritehero.core.di.module.AppModule
import com.github.ramonrabello.favoritehero.core.di.module.BuildActivityModule
import com.github.ramonrabello.favoritehero.core.di.module.BuildFragmentModule
import com.github.ramonrabello.favoritehero.core.di.module.RoomModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 *
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    RoomModule::class,
    BuildActivityModule::class,
    BuildFragmentModule::class])
interface AppComponent : AndroidInjector<FavoriteHeroApplication> {
    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<FavoriteHeroApplication>()
}