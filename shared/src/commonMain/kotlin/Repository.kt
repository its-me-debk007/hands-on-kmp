import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import model.MoviesDTO
import model.Result

class Repository {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }.also { initLogger() }

    suspend fun getMovies(): List<Result> {
        val body: MoviesDTO = httpClient.get(BASE_URL + "discover/movie") {
            url {
                parameters.append("api_key", API_KEY)
            }
        }.body()

        return body.results
    }

    suspend fun searchMovies(query: String): List<Result> {
        val body: MoviesDTO = httpClient.get(BASE_URL + "search/movie") {
            url {
                parameters.append("api_key", API_KEY)
                parameters.append("query", query)
            }
        }.body()

        return body.results
    }
}