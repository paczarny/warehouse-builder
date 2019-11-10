package com.github.kbednarz.webscraper.component

import tornadofx.View
import tornadofx.hbox
import tornadofx.label

class MainWindow : View() {

    override val root = hbox {
        label {
            text = "Hello World"
        }
    }
}