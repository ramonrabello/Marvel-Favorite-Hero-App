package com.github.ramonrabello.favoritehero

import com.github.ramonrabello.favoritehero.core.di.Injector
import com.github.ramonrabello.favoritehero.core.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class FavoriteHeroApplication : DaggerApplication() {

    companion object {
        var instance: FavoriteHeroApplication? = null
            private set
    }

    override fun applicationInjector(): AndroidInjector<FavoriteHeroApplication> =
            DaggerAppComponent.builder().create(this@FavoriteHeroApplication)

    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
        instance = this
    }
}