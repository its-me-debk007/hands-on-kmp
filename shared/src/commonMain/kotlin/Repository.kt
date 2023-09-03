import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.async

class Repository {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }.also { initLogger() }

    suspend fun makeApiCall(): Int {
        val responseCode = GlobalScope.async(Dispatchers.IO) {
            val response = httpClient.get("https://dummyjson.com/products/1")
            response.status.value
        }

        return responseCode.await()

    }
}