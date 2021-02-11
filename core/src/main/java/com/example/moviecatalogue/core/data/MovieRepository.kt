package com.example.moviecatalogue.core.data

import com.example.moviecatalogue.core.data.source.local.LocalDataSource
import com.example.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.example.moviecatalogue.core.data.source.remote.network.ApiResponse
import com.example.moviecatalogue.core.data.source.remote.response.ResultsItem
import com.example.moviecatalogue.core.domain.model.Movie
import com.example.moviecatalogue.core.domain.repository.IMovieRepository
import com.example.moviecatalogue.core.utils.AppExecutors
import com.example.moviecatalogue.core.utils.DataMapper
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                Timber.d("Get movie without query: loadFromDB")
                return localDataSource.getAllMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                Timber.d("Get movie without query: shouldFetch")
                var state = false

                val calendar: Calendar = Calendar.getInstance()
                val day: Int = calendar.get(Calendar.DAY_OF_WEEK)

                if (data == null || data.isEmpty() || day == Calendar.SUNDAY)
                    state = true

                return state
            }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> {
                Timber.d("Get movie without query: createCall")
                return remoteDataSource.getAllMovie()
            }

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                Timber.d("Get movie without query: saveCallResult")
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()

    override fun getAllMovie(query: String): Flow<Resource<List<Movie>>> {
        return flow {
            Timber.d("Get movie with query")
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getAllMovie(query).first()) {
                is ApiResponse.Success -> {
                    val movieEntity = DataMapper.mapResponsesToEntities(apiResponse.data)
                    val movie = DataMapper.mapEntitiesToDomain(movieEntity)
                    emit(Resource.Success(movie))
                }
                is ApiResponse.Empty -> {
                    val movie = ArrayList<Movie>() as List<Movie>
                    emit(Resource.Success(movie))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error<List<Movie>>(apiResponse.errorMessage))
                }
            }
        }
    }

    override fun getFavorite(): Flow<List<Movie>> {
        Timber.d("Get favorite movie")
        return localDataSource.getFavorite().map {
            val movie = DataMapper.mapFavoriteEntToMovieEnt(it)
            DataMapper.mapEntitiesToDomain(movie)
        }
    }


    override fun checkFavorite(favoriteId: String): Flow<Int> {
        Timber.d("Check favorite state")
        return localDataSource.checkFavorite(favoriteId)
    }

    override fun insertFavorite(favorite: Movie) {
        Timber.d("Insert favorite movie")
        val movieEntity = DataMapper.mapDomainToEntity(favorite)
        if (movieEntity != null) {
            val favoriteEntity = DataMapper.mapMovieEntToFavoriteEnt(movieEntity)
            appExecutors.diskIO().execute { localDataSource.insertFavorite(favoriteEntity) }
        }
    }

    override fun deleteFavorite(favorite: Movie) {
        Timber.d("Delete favorite movie")
        val movieEntity = DataMapper.mapDomainToEntity(favorite)
        if (movieEntity != null) {
            val favoriteEntity = DataMapper.mapMovieEntToFavoriteEnt(movieEntity)
            appExecutors.diskIO().execute { localDataSource.deleteFavorite(favoriteEntity) }
        }
    }
}