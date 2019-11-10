package com.github.kbednarz.webscraper

import com.github.kbednarz.webscraper.component.MainWindow
import tornadofx.App
import tornadofx.launch

class MainApp : App(MainWindow::class)

fun main(args: Array<String>) {
    launch<MainApp>(args)
}