package com.example.jpsubmission2.utils

import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.data.remote.responses.MovieResultsItem
import com.example.jpsubmission2.data.remote.responses.TvResultsItem

object FakeDataTest {

    fun generateFavorites(): ArrayList<FavoritesMovie> {
        val favoritesMovie = ArrayList<FavoritesMovie>()
        favoritesMovie.add(
            FavoritesMovie(
                1, MovieResultsItem(
                    "An elite Navy SEAL uncovers an international conspiracy while seeking justice for the murder of his pregnant wife.",
                    "Tom Clancy's Without Remorse",
                    "/rEm96ib0sPiZBADNKBHKBv5bve9.jpg",
                    "/fPGeS6jgdLovQAKunNHX8l0avCy.jpg",
                    "2021-04-29",
                    7.2,
                    567189
                ), true
            )
        )
        favoritesMovie.add(
            FavoritesMovie(
                2, MovieResultsItem(
                    "An elite Navy SEAL uncovers an international conspiracy",
                    "Tom Clancy's Without Remorse",
                    "/rEm96ib0sPiZBADNKBHKBv5bve9.jpg",
                    "/fPGeS6jgdLovQAKunNHX8l0avCy.jpg",
                    "2021-04-29",
                    7.3,
                    56179
                ), false
            )
        )
        return favoritesMovie
    }

    fun generateMoviItems(): ArrayList<MovieResultsItem> {
        val moviRes = ArrayList<MovieResultsItem>()
        moviRes.add(
            MovieResultsItem(
                "An elite Navy SEAL uncovers an international conspiracy",
                "Tom Clancy's Without Remorse",
                "/rEm96ib0sPiZBADNKBHKBv5bve9.jpg",
                "/fPGeS6jgdLovQAKunNHX8l0avCy.jpg",
                "2021-04-29",
                7.3,
                56179
            )
        )
        moviRes.add(
            MovieResultsItem(
                "An elite Navy SEAL uncovers an international conspiracy",
                "Tom Clancy's Without Remorse",
                "/rEm96ib0sPiZBADNKBHKBv5bve9.jpg",
                "/fPGeS6jgdLovQAKunNHX8l0avCy.jpg",
                "2021-04-29",
                7.3,
                56179
            )
        )
        return moviRes
    }

    fun generateTvItems(): ArrayList<TvResultsItem> {
        val tvRes = ArrayList<TvResultsItem>()
        tvRes.add(
            TvResultsItem(
                "2021-03-24",
                "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning...",
                "en",
                "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
                "/9Jmd1OumCjaXDkpllbSGi2EpJvl.jpg",
                "The Flash",
                7.7,
                60735
            )
        )
        tvRes.add(
            TvResultsItem(
                "2021-03-24",
                "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning...",
                "en",
                "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
                "/9Jmd1OumCjaXDkpllbSGi2EpJvl.jpg",
                "The Flash",
                7.7,
                60735
            )
        )
        return tvRes
    }
}