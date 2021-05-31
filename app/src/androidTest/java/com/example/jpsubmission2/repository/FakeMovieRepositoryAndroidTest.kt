package com.example.jpsubmission2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.data.remote.responses.*
import com.example.jpsubmission2.utils.Resource
import com.example.jpsubmission2.utils.Status
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FakeMovieRepositoryAndroidTest : MovieRepository {
    private val favoriteItems = mutableListOf<FavoritesMovie>()
    private val observableFavorites = MutableLiveData<List<FavoritesMovie>>()

    private fun refreshLivedata() {
        observableFavorites.postValue(favoriteItems)
    }

    override suspend fun getMovies(): Resource<MovieResponse> {
        val movies = MovieResponse(
            1,
            1, listOf(
                MovieResultsItem(
                    "An elite Navy SEAL uncovers an international conspiracy",
                    "Tom Clancy's Without Remorse",
                    "/rEm96ib0sPiZBADNKBHKBv5bve9.jpg",
                    "/fPGeS6jgdLovQAKunNHX8l0avCy.jpg",
                    "2021-04-29",
                    7.3,
                    56179
                ),
            ), 1
        )
        return Resource(Status.SUCCESS, movies, "Success")
    }

    override suspend fun getTvs(): Resource<TvResponse> {
        val tvs = TvResponse(
            1,
            1, listOf(
                TvResultsItem(
                    "2021-03-24",
                    "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning...",
                    "en",
                    "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
                    "/9Jmd1OumCjaXDkpllbSGi2EpJvl.jpg",
                    "The Flash",
                    7.7,
                    60735
                ),
            ), 1
        )
        return Resource(Status.SUCCESS, tvs, "Success")
    }

    override suspend fun getMovieDetails(id: Int): Resource<MovieDetailResponse> {
        val movieDetails = MovieDetailResponse(
            "en",
            "/6ELCZlTA5lGUops70hKdB83WJxH.jpg",
            76706000,
            460465,
            20000000,
            "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's",
            "Mortal Kombat",
            "/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg",
            "2021-04-07",
            7.6,
            "https://www.mortalkombatmovie.net"
        )
        return Resource(Status.SUCCESS, movieDetails, "Success")
    }

    override suspend fun getTvDetails(id: Int): Resource<TvDetailResponse> {
        val tvDetails = TvDetailResponse(
            "en",
            "/dYvIUzdh6TUv4IFRq8UBkX7bNNu.jpg",
            120168,
            "2021-03-24",
            "A videotape hidden in Sara's closet becomes a key piece of evidence that makes everyone realize",
            "/o7uk5ChRt3quPIv8PcvPfzyXdMw.jpg",
            "¿Quién mató a Sara?",
            7.8,
            "https://www.netflix.com/title/81166747"
        )
        return Resource(Status.SUCCESS, tvDetails, "Success")
    }

    override suspend fun insertFavorite(favorite: FavoritesMovie) {
        favoriteItems.add(favorite)
        refreshLivedata()
    }

    override suspend fun deleteFavorite(favorite: FavoritesMovie) {
        favoriteItems.remove(favorite)
        refreshLivedata()
    }

    override fun observeFavorites(): LiveData<List<FavoritesMovie>> {
        return observableFavorites
    }


}