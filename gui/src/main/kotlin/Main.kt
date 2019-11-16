package pl.krakow.uek.wzisn2.etl

import org.slf4j.simple.SimpleLogger
import pl.krakow.uek.wzisn2.etl.component.MainWindow
import tornadofx.App

class MainApp : App(MainWindow::class)

fun main(args: Array<String>) {
    applyDefaultConfiguration()
//    launch<MainApp>(args)
    ModuleRunner().run()
}

private fun applyDefaultConfiguration() {
    System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG")
}