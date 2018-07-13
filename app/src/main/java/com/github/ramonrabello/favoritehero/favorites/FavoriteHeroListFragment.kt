package com.github.ramonrabello.favoritehero.favorites

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.core.di.Injectable
import com.github.ramonrabello.favoritehero.core.ktx.obtainViewModel
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import com.github.ramonrabello.favoritehero.core.view.ItemSpacingDecoration
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_favorite_hero_list.recycler_view
import kotlinx.android.synthetic.main.fragment_hero_list.view.recycler_view
import javax.inject.Inject

/**
 * Fragment that displays a list of favorite heroes.
 */
class FavoriteHeroListFragment : DaggerFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FavoriteHeroListViewModel
    private lateinit var heroAdapter: FavoriteHeroAdapter

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
        observeLiveData()
        viewModel.loadAllHeroes()
    }

    private fun observeLiveData() {
        viewModel.favoriteHeroesLiveData.observe(this, Observer<List<FavoriteHero>> { favoriteHeroes ->
            favoriteHeroes?.let { heroAdapter.addItems(favoriteHeroes) }
        })
        viewModel.errorLiveData.observe(this, Observer { message ->
            message?.let { Snackbar.make(recycler_view, message, Snackbar.LENGTH_SHORT) }
        })
    }
}