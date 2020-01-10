package pl.krakow.uek.wzisn2.etl.view

import javafx.beans.property.SimpleBooleanProperty
import pl.krakow.uek.wzisn2.etl.controller.AppStylesheet
import tornadofx.*

class ProgressButton(title: String, disabled: SimpleBooleanProperty, actionOnClick: () -> Unit) : View() {
    private var actionInProgress = SimpleBooleanProperty()
    private var isFinished = SimpleBooleanProperty()

    override val root = hbox {
        prefHeight = 10.0
        spacing = 20.0

        button {
            text = title
            addClass(AppStylesheet.button)
            action {
                runAsync {
                    invokeAction(disabled, actionOnClick)
                }
            }
            disableWhen { disabled }
        }
        progressindicator {
            addClass(AppStylesheet.progress)
            visibleWhen { actionInProgress }
        }
        label {
            text = "Done!"
            visibleWhen { isFinished }
        }
    }

    fun invokeAction(disabled: SimpleBooleanProperty, actionOnClick: () -> Unit) {
        try {
            isFinished.value = false
            actionInProgress.value = true
//            disabled.value = true

            actionOnClick()
        } finally {
            actionInProgress.value = false
            isFinished.value = true
//            disabled.value = false
        }
    }
}