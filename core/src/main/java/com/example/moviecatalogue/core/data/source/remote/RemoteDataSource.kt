package com.example.moviecatalogue.core.data.source.remote

import com.example.moviecatalogue.core.data.source.remote.network.ApiResponse
import com.example.moviecatalogue.core.data.source.remote.network.ApiService
import com.example.moviecatalogue.core.data.source.remote.response.ResultsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllMovie(): Flow<ApiResponse<List<ResultsItem>>> {
        return flow {
            try {
                Timber.d("Get movie without query")
                val response = apiService.getAllMovie()
                if (response.results != null) {

                    val data = response.results.filterNotNull()
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Empty)
                }
                Timber.d(response.results.toString())
            } catch (e: Exception) {
                Timber.e(e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllMovie(query: String): Flow<ApiResponse<List<ResultsItem>>> {
        return flow {
            try {
                Timber.d("Get movie with query")
                val response = apiService.getAllMovie(query)
                if (response.results != null) {
                    val data = response.results.filterNotNull()
                    emit(ApiResponse.Success(data))
                } else {
                    emit(ApiResponse.Empty)
                }
                Timber.d(response.results.toString())
            } catch (e: Exception) {
                Timber.e(e.toString())
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}