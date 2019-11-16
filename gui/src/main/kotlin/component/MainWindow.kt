package pl.krakow.uek.wzisn2.etl.component

import tornadofx.View
import tornadofx.hbox
import tornadofx.*

class MainWindow : View() {

    override val root = hbox {
        label {
            text = "Hello World"
        }
        button {
            text = "Run"
            action { println("Hello") }
        }
    }
}