package com.github.ramonrabello.favoritehero.heroes

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.VisibleForTesting
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.core.di.Injectable
import com.github.ramonrabello.favoritehero.core.ktx.obtainViewModel
import com.github.ramonrabello.favoritehero.core.view.ItemSpacingDecoration
import com.github.ramonrabello.favoritehero.data.model.Character
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_hero_list.recycler_view
import javax.inject.Inject

/**
 * Fragment that displays a list of heroes.
 */
class HeroListFragment : DaggerFragment(), Injectable, CharacterViewHolder.OnFavoriteItemListener {

    @Inject
    @VisibleForTesting
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: HeroListViewModel
    private lateinit var favoriteHeroViewModel: FavoriteHeroViewModel
    private lateinit var heroAdapter: CharacterAdapter
    private var recyclerState: Parcelable? = null

    companion object {
        fun newInstance(): HeroListFragment {
            return HeroListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_hero_list, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryText: String): Boolean {
                searchCharacters(queryText)
                return true
            }

            override fun onQueryTextChange(queryText: String): Boolean {
                searchCharacters(queryText)
                return true
            }
        })

        searchView.setOnCloseListener {
            heroAdapter.clear()
            recycler_view.showProgress()
            viewModel.loadHeroes()
            viewModel.isSearching = true
            true
        }
    }

    private fun searchCharacters(queryText: String) {
        observeSearchResults()
        viewModel.searchHeroes(queryText)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!recycler_view.progressView.isShown) {
            recycler_view.showProgress()
        }
        viewModel = (view.context as FragmentActivity).obtainViewModel(viewModelFactory, HeroListViewModel::class.java)
        favoriteHeroViewModel =  (view.context as FragmentActivity).obtainViewModel(viewModelFactory, FavoriteHeroViewModel::class.java)
        heroAdapter = CharacterAdapter(this, favoriteHeroViewModel)
        recycler_view.apply {
            adapter = heroAdapter
            setupMoreListener(viewModel, HeroListViewModel.RESULTS_OFFSET)
            setLayoutManager(LinearLayoutManager(context))
            addItemDecoration(ItemSpacingDecoration(topOffset = 5))
        }
        initObserver()
        viewModel.loadHeroes(viewModel.currentOffset)
    }

    override fun onStop() {
        super.onStop()
        viewModel.currentOffset = 0
        heroAdapter.clear()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("lmState", recycler_view.recyclerView.layoutManager.onSaveInstanceState())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }

    private fun observeSearchResults(){
        val heroListSearchObserver = Observer<List<Character>> { results ->
            results?.let {

                if (recyclerState != null) {
                    recycler_view.recyclerView.layoutManager.onRestoreInstanceState(recyclerState)
                }
                heroAdapter.clear()
                heroAdapter.addItems(results)
            }
        }
        viewModel.searchResultsLiveData.observe(this, heroListSearchObserver)
    }

    private fun initObserver() {
        val heroesListObserver = Observer<List<Character>> { results ->
            results?.let {

                if (recyclerState != null){
                    recycler_view.recyclerView.layoutManager.onRestoreInstanceState(recyclerState)
                }
                heroAdapter.addItems(results)
                //viewModel.isSearching = false

                if (!recycler_view.recyclerView.isShown) {
                    recycler_view.showRecycler()
                }
                recycler_view.hideMoreProgress()
            }
        }
        viewModel.allHeroesLiveData.observe(this, heroesListObserver)
        viewModel.errorLiveData.observe(this, Observer { errorMessage ->
            errorMessage?.let { Snackbar.make(recycler_view, errorMessage, Snackbar.LENGTH_SHORT).show() }
        })
    }

    override fun onItemFavorited(favoriteHero: FavoriteHero, itemPosition: Int) {
        favoriteHeroViewModel.observeFavoriteHeroState(favoriteHero)
        favoriteHeroViewModel.addOrRemoveFavoriteHero(favoriteHero)
    }
}