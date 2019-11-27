package pl.krakow.uek.wzisn2.etl.view

import javafx.beans.property.SimpleBooleanProperty
import pl.krakow.uek.wzisn2.etl.controller.AppStylesheet
import tornadofx.*

class ProgressButton(title: String, disable: SimpleBooleanProperty, actionOnClick: () -> Unit) : View() {
    var actionInProgress = SimpleBooleanProperty()

    override val root = hbox {
        prefHeight = 10.0
        button {
            text = title
            addClass(AppStylesheet.button)
            action {
                runAsync {
                    actionInProgress.value = true
                    disable.value = true
                    try {
                        actionOnClick()
                    } finally {
                        actionInProgress.value = false
                        disable.value = false
                    }
                }
            }
            disableWhen { actionInProgress }
            disableWhen { disable }
        }
        progressindicator {
            visibleWhen { actionInProgress }
        }
    }
}