import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import screen.HomePage

@Composable
fun App() {
    MaterialTheme {
        Surface(color = Background, modifier = Modifier.fillMaxSize()) {
            Navigator(HomePage())
        }
    }
}

expect fun getPlatformName(): String

expect fun initLogger()