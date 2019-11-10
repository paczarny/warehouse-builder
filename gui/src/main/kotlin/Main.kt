package pl.krakow.uek.wzisn2.etl

import pl.krakow.uek.wzisn2.etl.component.MainWindow
import tornadofx.App
import tornadofx.launch

class MainApp : App(MainWindow::class)

fun main(args: Array<String>) {
    launch<MainApp>(args)
}