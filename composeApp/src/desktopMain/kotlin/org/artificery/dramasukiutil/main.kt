package org.artificery.dramasukiutil

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.artificery.dramasukiutil.db.getDatabaseBuilder
import org.artificery.dramasukiutil.di.desktopModule
import org.artificery.dramasukiutil.presentation.screen.MainScreen
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun main() = application {
    val database = getDatabaseBuilder().build()
    startKoin {
        modules(
            desktopModule,
            module {
                single { database }
            }
        )
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "DramaSukiUtil",
    ) {
        Scaffold {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}