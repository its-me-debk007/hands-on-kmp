import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
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

    suspend fun makeApiCall(): List<Result> {
        val response = GlobalScope.async(Dispatchers.IO) {
            val body: MoviesDTO =
                httpClient.get(BASE_URL + "discover/movie") {
                    url {
                        parameters.append("api_key", API_KEY)
                    }
                }.body()

            body.results
        }

        return response.await()
    }
}