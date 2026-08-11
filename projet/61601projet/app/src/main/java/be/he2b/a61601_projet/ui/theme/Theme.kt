package be.he2b.a61601_projet.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BlueDarkPrimary,
    secondary = BlueDarkSecondary,
    tertiary = BlueDarkTertiary,
    background = BlueDarkBackground,
    surface = BlueDarkSurface,
    onPrimary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = BlueLightPrimary,
    secondary = BlueLightSecondary,
    tertiary = BlueLightTertiary,
    background = BlueLightBackground,
    surface = BlueLightSurface,
    onPrimary = Color.White,
    onBackground = Color(0xFF0D1B2A),
    onSurface = Color(0xFF0D1B2A)
)

@Composable
fun _61601projetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // couleurs dynamiques Material You désactivées : sinon Android 12+
    // écrase notre palette avec l'accent du fond d'écran.
    dynamicColor: Boolean = false,
    Content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = Content
    )
}
