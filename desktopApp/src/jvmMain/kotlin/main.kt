import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Hands-on-KMP",
        resizable = false,
        state = WindowState(size = DpSize(500.dp, 680.dp)),
    ) {
        MainView()
    }
}