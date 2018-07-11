package com.github.ramonrabello.favoritehero.heroes

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
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
import com.github.ramonrabello.favoritehero.data.model.Character
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_hero_list.recycler_view
import kotlinx.android.synthetic.main.fragment_hero_list.view.recycler_view
import javax.inject.Inject

/**
 * Fragment that displays a list of heroes.
 */
class HeroListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: HeroListViewModel
    lateinit var heroAdapter: CharacterAdapter
    private var currentOffset: Int = 0
    private var isSearching: Boolean = false

    companion object {
        fun newInstance(): HeroListFragment {
            return HeroListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val itemView = inflater.inflate(R.layout.fragment_hero_list, container, false)
        heroAdapter = CharacterAdapter(viewModelFactory)
        itemView.recycler_view.apply {
            adapter = heroAdapter
            hideProgress()
            setLayoutManager(LinearLayoutManager(context))
            recycler_view.setOnMoreListener { _, _, _ -> loadMore() }
            addItemDecoration(ItemSpacingDecoration(topOffset = 10))
        }
        return itemView
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
        // Retrieve the SearchView and plug it into SearchManager
        val searchView = MenuItemCompat.getActionView(menu?.findItem(R.id.action_search)) as SearchView
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
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
            viewModel.loadHeroes(0)
            isSearching = true
            true
        }
    }

    private fun searchCharacters(queryText: String) {
        isSearching = if (queryText.isNotEmpty()) {
            viewModel.searchHeroes(queryText)
            true
        } else {
            viewModel.loadHeroes()
            false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("lmState", recycler_view.recyclerView.layoutManager.onSaveInstanceState())
    }

    private var recyclerState: Parcelable? = null

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        recyclerState = savedInstanceState?.getParcelable("lmState")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!recycler_view.progressView.isShown) {
            recycler_view.showProgress()
        }
        viewModel = (view.context as FragmentActivity).obtainViewModel(viewModelFactory, HeroListViewModel::class.java)
        initObserver()
        viewModel.loadHeroes(currentOffset)
    }

    private fun initObserver() {
        val heroesListObserver = Observer<List<Character>> { results ->
            results?.let {
                if (isSearching){
                    heroAdapter.clear()
                }
                heroAdapter.addItems(results)
                isSearching = false
                recyclerState?.let {
                    recycler_view.recyclerView.layoutManager?.onRestoreInstanceState(recyclerState)
                    recyclerState = null
                }
                if (!recycler_view.recyclerView.isShown) {
                    recycler_view.showRecycler()
                }
                recycler_view.hideMoreProgress()
            }
        }
        viewModel.allHeroesLiveData.observe(this, heroesListObserver)
    }

    private fun loadMore(incrementOffset: Int = 20) {
        if (!recycler_view.isLoadingMore) {
            recycler_view.showMoreProgress()
        }
        currentOffset = currentOffset.plus(incrementOffset)
        viewModel.loadHeroes(currentOffset)
    }
}