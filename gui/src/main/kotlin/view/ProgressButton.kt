package pl.krakow.uek.wzisn2.etl.view

import pl.krakow.uek.wzisn2.etl.controller.AppStylesheet
import tornadofx.*

class ProgressButton(title: String, actionOnClick: () -> Unit) : View() {

    override val root = hbox {
        button {
            text = title
            addClass(AppStylesheet.button)
            action { actionOnClick() }
        }
    }
}