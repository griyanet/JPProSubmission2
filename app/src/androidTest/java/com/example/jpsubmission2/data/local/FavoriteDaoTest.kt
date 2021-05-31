package com.example.jpsubmission2.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.jpsubmission2.data.local.entity.FavoritesMovie
import com.example.jpsubmission2.data.remote.responses.MovieResultsItem
import com.example.jpsubmission2.getOrAwaitValue
import com.example.jpsubmission2.util.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class FavoriteDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get: Rule
    val testCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test.db")
    lateinit var database: IMDBDatabase
    private lateinit var dao: FavoritesDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.favoritesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertFavorite() = testCoroutineRule.runBlockingTest {
        val favoriteMovie = FavoritesMovie(
            1, MovieResultsItem(
                "An elite Navy SEAL uncovers an international conspiracy",
                "Tom Clancy's Without Remorse",
                "/rEm96ib0sPiZBADNKBHKBv5bve9.jpg",
                "/fPGeS6jgdLovQAKunNHX8l0avCy.jpg",
                "2021-04-29",
                7.3,
                56179
            ), true
        )
        val favoriteMovie2 = FavoritesMovie(
            1, MovieResultsItem(
                "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior",
                "Mortal Kombat",
                "/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg",
                "/6ELCZlTA5lGUops70hKdB83WJxH.jpg",
                "2021-04-07",
                7.6,
                460465
            ), true
        )
        dao.insertFavorite(favoriteMovie)
        dao.insertFavorite(favoriteMovie2)
        val allFavorites = dao.observeAllFavorites().getOrAwaitValue()
        assertThat(allFavorites).contains(favoriteMovie2)
        assertThat(allFavorites).containsNoDuplicates()
    }

    @Test
    fun deleteFavorite() = testCoroutineRule.runBlockingTest {
        val favoriteMovie = FavoritesMovie(
            1, MovieResultsItem(
                "An elite Navy SEAL uncovers an international conspiracy",
                "Tom Clancy's Without Remorse",
                "/rEm96ib0sPiZBADNKBHKBv5bve9.jpg",
                "/fPGeS6jgdLovQAKunNHX8l0avCy.jpg",
                "2021-04-29",
                7.3,
                56179
            ), false
        )
        dao.insertFavorite(favoriteMovie)
        dao.deleteFavorite(favoriteMovie)
        val allFavorites = dao.observeAllFavorites().getOrAwaitValue()
        assertThat(allFavorites).doesNotContain(favoriteMovie)
    }

    @Test
    fun observeAllFavorites() = testCoroutineRule.runBlockingTest {
        val favoriteMovie = FavoritesMovie(
            1, MovieResultsItem(
                "An elite Navy SEAL uncovers an international conspiracy",
                "Tom Clancy's Without Remorse",
                "/rEm96ib0sPiZBADNKBHKBv5bve9.jpg",
                "/fPGeS6jgdLovQAKunNHX8l0avCy.jpg",
                "2021-04-29",
                7.3,
                56179
            ), true
        )
        val favoriteMovie2 = FavoritesMovie(
            1, MovieResultsItem(
                "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior",
                "Mortal Kombat",
                "/nkayOAUBUu4mMvyNf9iHSUiPjF1.jpg",
                "/6ELCZlTA5lGUops70hKdB83WJxH.jpg",
                "2021-04-07",
                7.6,
                460465
            ), true
        )
        dao.insertFavorite(favoriteMovie)
        dao.insertFavorite(favoriteMovie2)
        val allFavorites = dao.observeAllFavorites().getOrAwaitValue()
        assertThat(allFavorites).isNotNull()
    }

}