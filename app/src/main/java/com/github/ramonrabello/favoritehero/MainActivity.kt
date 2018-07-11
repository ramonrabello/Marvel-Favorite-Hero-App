package com.github.ramonrabello.favoritehero

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.github.ramonrabello.favoritehero.favorites.FavoriteHeroListFragment
import com.github.ramonrabello.favoritehero.heroes.HeroListFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.navigation
import kotlinx.android.synthetic.main.activity_main.toolbar
import javax.inject.Inject
import android.app.SearchManager
import android.content.Context
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.view.Menu


/**
 * Main activity that holds both fragments and bottom navigation view.
 */
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidJnjector: DispatchingAndroidInjector<Fragment>
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_heros -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_content, HeroListFragment.newInstance()).commit()
                toolbar.title = getString(R.string.title_heros)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorites -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_content, FavoriteHeroListFragment.newInstance()).commit()
                toolbar.title = getString(R.string.title_favorites)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        toolbar.title = getString(R.string.title_heros)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_content, HeroListFragment.newInstance()).commit()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidJnjector
}
