import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class Repository {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }.also { initLogger() }

    fun makeApiCall() {
        val job = GlobalScope.launch (Dispatchers.IO) {
            val response = httpClient.get("https://dummyjson.com/products/1")
            Napier.d(response.status.description)
            cancel()
        }

    }
}