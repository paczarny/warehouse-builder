package pl.krakow.uek.wzisn2.etl.view

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleObjectProperty
import pl.krakow.uek.wzisn2.etl.controller.AppStylesheet
import tornadofx.*

class ProgressButton(title: String, disabled: SimpleBooleanProperty, actionOnClick: (SimpleObjectProperty<Pair<Long, Long>>) -> Unit) : View() {
    private var actionInProgress = SimpleBooleanProperty()
    var progressValues = SimpleDoubleProperty()
    var progressObs = SimpleObjectProperty(Pair<Long, Long>(0, 0))

    override val root = hbox {
        prefHeight = 10.0
        spacing = 20.0

        button {
            text = title
            addClass(AppStylesheet.button)
            action {
                runAsync {
                    try {
                        actionInProgress.value = true
                        disabled.value = true

                        progressObs.addListener { _, _, new ->
                            progressValues.value = new.first / new.second.toDouble()
                        }

                        actionOnClick(progressObs)
                    } finally {
                        actionInProgress.value = false
                        disabled.value = false
                    }
                }
            }
            disableWhen { disabled }
        }

        val isFinished = progressValues.isEqualTo(1.0, 0.01)

        progressbar(progressValues) {
            addClass(AppStylesheet.progressbar)
            visibleWhen { actionInProgress.or(isFinished) }
        }
        label {
            text = "Done!"
            visibleWhen { (!actionInProgress).and(isFinished) }
        }
    }
}