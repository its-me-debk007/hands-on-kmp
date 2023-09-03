import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun App() {
    MaterialTheme {
        var greetingText by remember { mutableStateOf("Hello, KMP!") }
        var showImage by remember { mutableStateOf(false) }
        val repository by remember { mutableStateOf(Repository()) }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                onClick = {
                    showImage = !showImage
                    GlobalScope.launch(Dispatchers.Main) {
                        greetingText = repository.makeApiCall().toString()
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Magenta)
            ) {
                Text(greetingText, color = Color.White)
            }
            AnimatedVisibility(showImage) {
                KamelImage(
                    asyncPainterResource("https://unsplash.com/photos/Nhx2IVkw22s"),
                    null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

expect fun getPlatformName(): String

expect fun initLogger()