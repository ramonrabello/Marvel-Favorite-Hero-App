package com.github.ramonrabello.favoritehero.detail

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.github.ramonrabello.favoritehero.R
import com.github.ramonrabello.favoritehero.core.ktx.load
import com.github.ramonrabello.favoritehero.core.ktx.obtainViewModel
import com.github.ramonrabello.favoritehero.core.ktx.toTypeface
import com.github.ramonrabello.favoritehero.data.model.DetailData
import com.github.ramonrabello.favoritehero.data.repository.local.entity.FavoriteHero
import com.github.ramonrabello.favoritehero.heroes.FavoriteHeroViewModel
import com.github.ramonrabello.favoritehero.heroes.ItemSpacingDecoration
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import kotlinx.android.synthetic.main.activity_character_detail.fab
import kotlinx.android.synthetic.main.activity_character_detail.hero_parallax_image
import kotlinx.android.synthetic.main.activity_character_detail.toolbar
import kotlinx.android.synthetic.main.content_character_detail.comics_recycler_view
import kotlinx.android.synthetic.main.content_character_detail.comics_section_title
import kotlinx.android.synthetic.main.content_character_detail.events_recycler_view
import kotlinx.android.synthetic.main.content_character_detail.events_section_title
import kotlinx.android.synthetic.main.content_character_detail.hero_description
import kotlinx.android.synthetic.main.content_character_detail.series_recycler_view
import kotlinx.android.synthetic.main.content_character_detail.series_section_title
import kotlinx.android.synthetic.main.content_character_detail.stories_recycler_view
import kotlinx.android.synthetic.main.content_character_detail.stories_section_title
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

/**
 * Activity that displays character details.
 */
class CharacterDetailActivity : AppCompatActivity(), HasActivityInjector {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var dispatchingAndroidJnjector: DispatchingAndroidInjector<Activity>
    private lateinit var favoriteHeroViewModel: FavoriteHeroViewModel
    private lateinit var detailViewModel: CharacterDetailViewModel
    private val comicAdapter = DetailDataAdapter()
    private val eventAdapter = DetailDataAdapter()
    private val storyAdapter = DetailDataAdapter()
    private val seriesAdapter = DetailDataAdapter()

    companion object {
        const val EXTRA_FAVORITE_HERO = "com.github.ramonrabello.favoritehero.detail.CharacterDetailActivity.EXTRA_FAVORITE_HERO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val heartOutlineDrawable = ContextCompat.getDrawable(this, R.drawable.ic_heart_outline)
        val heartFilledDrawable = ContextCompat.getDrawable(this, R.drawable.ic_heart_filled)
        favoriteHeroViewModel = obtainViewModel(viewModelFactory, FavoriteHeroViewModel::class.java)
        detailViewModel = obtainViewModel(viewModelFactory, CharacterDetailViewModel::class.java)
        favoriteHeroViewModel.favoriteIconStateLiveData.observe(this, Observer<Boolean> { isFavorite ->
            val drawableTint = ContextCompat.getColor(this, android.R.color.white)
            isFavorite?.let {
                if (isFavorite) {
                    updateFabState(heartFilledDrawable, drawableTint)
                } else {
                    updateFabState(heartOutlineDrawable, drawableTint)
                }
            }
        })

        comics_recycler_view.apply {
            adapter = comicAdapter
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            addItemDecoration(ItemSpacingDecoration(topOffset = 10))
        }

        events_recycler_view.apply {
            adapter = eventAdapter
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            addItemDecoration(ItemSpacingDecoration(topOffset = 10))
        }

        stories_recycler_view.apply {
            adapter = storyAdapter
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            addItemDecoration(ItemSpacingDecoration(topOffset = 10))
        }

        series_recycler_view.apply {
            adapter = seriesAdapter
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            addItemDecoration(ItemSpacingDecoration(topOffset = 10))
        }

        hero_description.toTypeface("OpenSans-Regular")
        comics_section_title.toTypeface("OpenSans-SemiBold")
        events_section_title.toTypeface("OpenSans-SemiBold")
        stories_section_title.toTypeface("OpenSans-SemiBold")
        series_section_title.toTypeface("OpenSans-SemiBold")
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            android.R.id.home -> {
                ActivityCompat.finishAfterTransition(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        val favoriteHero: FavoriteHero = intent.getParcelableExtra(EXTRA_FAVORITE_HERO)
        fab.setOnClickListener { _ -> launch { favoriteHeroViewModel.addOrRemoveFavoriteHero(favoriteHero) } }
        bindData(favoriteHero)
    }

    private fun bindData(favoriteHero: FavoriteHero) {
        observeCharacterDetails()
        detailViewModel.loadCharacterDetails(favoriteHero.id)
        favoriteHeroViewModel.observeFavoriteHeroState(favoriteHero.id)
        ViewCompat.setTransitionName(hero_description, favoriteHero.description)
        ViewCompat.setTransitionName(hero_parallax_image, favoriteHero.thumbnail)
        toolbar.title = favoriteHero.name
        hero_description.text = favoriteHero.description
        hero_parallax_image.load(favoriteHero.thumbnail)
    }

    private fun observeCharacterDetails() {
        detailViewModel.characterComicsLiveData.observe(this, Observer<List<DetailData>> { comics ->
            comics?.let { comicAdapter.addItems(comics) }
        })

        detailViewModel.characterEventsLiveData.observe(this, Observer<List<DetailData>> { events ->
            events?.let { eventAdapter.addItems(events) }
        })

        detailViewModel.characterStoriesLiveData.observe(this, Observer<List<DetailData>> { stories ->
            stories?.let { storyAdapter.addItems(stories) }
        })

        detailViewModel.characterSeriesLiveData.observe(this, Observer<List<DetailData>> { series ->
            series?.let { seriesAdapter.addItems(series) }
        })
    }

    private fun updateFabState(drawable: Drawable?, @ColorInt drawableTint: Int) {
        val wrappedDrawable = drawable?.let { DrawableCompat.wrap(drawable) }
        wrappedDrawable?.let { DrawableCompat.setTint(wrappedDrawable, drawableTint) }
        fab.setImageDrawable(wrappedDrawable)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidJnjector
    }
}
