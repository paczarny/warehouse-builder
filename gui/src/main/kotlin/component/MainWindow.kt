package pl.krakow.uek.wzisn2.etl.component

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