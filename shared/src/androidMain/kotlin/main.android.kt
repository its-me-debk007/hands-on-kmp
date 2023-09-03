import androidx.compose.runtime.Composable
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

actual fun getPlatformName(): String = "Android"

@Composable fun MainView() = App()

actual fun initLogger() = Napier.base(DebugAntilog())
