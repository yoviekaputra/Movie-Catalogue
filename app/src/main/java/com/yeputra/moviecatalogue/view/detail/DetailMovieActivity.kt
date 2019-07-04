package com.yeputra.moviecatalogue.view.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.yeputra.moviecatalogue.R
import com.yeputra.moviecatalogue.base.BaseToolbarActivity
import com.yeputra.moviecatalogue.model.FilmType
import com.yeputra.moviecatalogue.model.Movie
import com.yeputra.moviecatalogue.utils.Constans
import com.yeputra.moviecatalogue.utils.toast
import com.yeputra.moviecatalogue.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.app_bar.*
class DetailMovieActivity : BaseToolbarActivity<FavoriteViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        initViewConfigure()
    }

    @SuppressLint("SetTextI18n")
    private fun initViewConfigure() {
        var year = ""
        var adult = ""
        val movie = intent.getParcelableExtra<Movie>(Constans.INTENT_DATA)

        movie.release_date?.let {
            if (it.length > 4) year = "(${it.substring(0,4)})"
        }
        movie.adult?.let {
            if (it) adult = " | 17+"
        }

        toolbar_title.text = movie.original_title
        tv_title.text = "${movie.title?:"-"} $year"
        tv_rating.text = movie.vote_average.toString() + adult
        tv_overview.text = movie.overview

        Glide.with(this)
                .load(Constans.POSTER_URL + Constans.POSTER_LARGE + movie.backdrop_path)
                .placeholder(R.mipmap.ic_placeholder)
                .into(img_banner)

        Glide.with(this)
                .load(Constans.POSTER_URL + Constans.POSTER_MEDIUM + movie.poster_path)
                .placeholder(R.mipmap.ic_placeholder)
                .into(img_poster)

        bt_favorite.tag = false

        bt_favorite.setOnClickListener {
            if (it.tag as Boolean) {
                viewmodel.delete(movie.id.toString())
            } else {
                viewmodel.add(movie, FilmType.MOVIE)
            }
            viewmodel.isFavorited(movie.id.toString()).observe(this, setFlagFavorite)
        }

        viewmodel.isFavorited(movie.id.toString()).observe(this, setFlagFavorite)
    }

    val setFlagFavorite = Observer<Boolean> {
        bt_favorite.tag = it
        if (it) {
            bt_favorite.setImageResource(R.drawable.ic_favorite_selected)
        } else {
            bt_favorite.setImageResource(R.drawable.ic_favorite_unselect)
        }
        toast(it.toString())
    }

    override fun setToolbar(): Toolbar = toolbar

    override fun setButtonBack(): Boolean = true

    override fun initViewModel(): FavoriteViewModel {
        val vm = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        vm.setupView(this)
        return vm
    }

}
