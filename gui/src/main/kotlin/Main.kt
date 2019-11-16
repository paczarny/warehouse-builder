package pl.krakow.uek.wzisn2.etl

import org.slf4j.simple.SimpleLogger
import pl.krakow.uek.wzisn2.etl.controller.AppStylesheet
import pl.krakow.uek.wzisn2.etl.view.MainView
import tornadofx.*

class MainApp : App(MainView::class, AppStylesheet::class)

fun main(args: Array<String>) {
    applyDefaultConfiguration()
    launch<MainApp>(args)
}

private fun applyDefaultConfiguration() {
    System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG")
}