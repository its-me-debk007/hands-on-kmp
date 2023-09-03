package repository

import model.Result

interface Repository {

    suspend fun getMovies(): List<Result>
    suspend fun searchMovies(query: String): List<Result>
}