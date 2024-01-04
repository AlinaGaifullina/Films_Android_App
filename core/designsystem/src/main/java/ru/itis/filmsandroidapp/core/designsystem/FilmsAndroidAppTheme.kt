package ru.itis.filmsandroidapp.core.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.itis.filmsandroidapp.core.designsystem.theme.baseTypography


private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF7A866B),
    secondary = Color(0xFF547231),
    surfaceTint = Color(0xFF2C2B27),
    tertiary = Color(0xFF817D70),
    surface = Color(0xFF24221B),
    error = Color(0xFF532424),
    background = Color(0xFF252319)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF000000),
    secondary = Color(0xFF525728),
    surfaceTint = Color(0xFFDBDDD9),
    tertiary = Color(0xFFDFD7BE),
    surface = Color(0xFFEEE3E3),
    error = Color(0xFFA22525),
    background = Color(0xFFB3A266)
)

@Composable
fun FilmsAndroidAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme =
        if (!darkTheme) {
            LightColorScheme
        } else {
            DarkColorScheme
        }
    //вот с этим не работает, не знаю почему:
//    val colors = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }
//    val view = LocalView.current
//    if (!view.isInEditMode) {
//        SideEffect {
//            val window = (view.context as Activity).window
//            window.statusBarColor = colors.primary.toArgb()
//            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
//        }
//    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = baseTypography,
        content = content
    )
}