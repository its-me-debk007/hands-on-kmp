package repository

import API_KEY
import BASE_URL
import io.ktor.client.call.body
import io.ktor.client.request.get
import model.MoviesDTO
import model.Result
import network.httpClient

class RepositoryImpl: Repository {

    override suspend fun getMovies(): List<Result> {
        val body: MoviesDTO = httpClient.get(BASE_URL + "discover/movie") {
            url {
                parameters.append("api_key", API_KEY)
            }
        }.body()

        return body.results
    }

    override suspend fun searchMovies(query: String): List<Result> {
        val body: MoviesDTO = httpClient.get(BASE_URL + "search/movie") {
            url {
                parameters.append("api_key", API_KEY)
                parameters.append("query", query)
            }
        }.body()

        return body.results
    }
}