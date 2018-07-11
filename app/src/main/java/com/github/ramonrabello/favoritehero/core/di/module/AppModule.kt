package com.github.ramonrabello.favoritehero.core.di.module

import android.app.Application
import android.content.Context
import com.github.ramonrabello.favoritehero.FavoriteHeroApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, NetworkModule::class])
class AppModule {
    @Provides
    @Singleton
    internal fun provideContext(application: FavoriteHeroApplication) : Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    internal fun provideApplication(application: FavoriteHeroApplication) : Application {
        return application
    }
}