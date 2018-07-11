package com.github.ramonrabello.favoritehero.favorites

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.core.ktx.obtainViewModel
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import com.github.ramonrabello.favoritehero.heroes.ItemSpacingDecoration
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_hero_list.view.recycler_view
import javax.inject.Inject

/**
 * Fragment that displays a list of favorite heroes.
 */
class FavoriteHeroListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: FavoriteHeroListViewModel
    lateinit var heroAdapter: FavoriteHeroAdapter

    companion object {
        fun newInstance(): FavoriteHeroListFragment {
            return FavoriteHeroListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_hero_list, container, false)
        heroAdapter = FavoriteHeroAdapter()
        view.recycler_view.apply {
            adapter = heroAdapter
            setLayoutManager(LinearLayoutManager(context))
            addItemDecoration(ItemSpacingDecoration(topOffset = 10))
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (view.context as FragmentActivity).obtainViewModel(viewModelFactory, FavoriteHeroListViewModel::class.java)
        observeFavoriteHeroes()
    }

    private fun observeFavoriteHeroes() {
        val favoriteHeroListObserver = Observer<List<FavoriteHero>> { favoriteHeroes ->
            favoriteHeroes?.let { heroAdapter.addItems(favoriteHeroes) }
        }
        viewModel.observeFavoriteHeroes().observe(this, favoriteHeroListObserver)
    }
}