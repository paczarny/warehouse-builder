package pl.krakow.uek.wzisn2.etl.view

import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment
import pl.krakow.uek.wzisn2.etl.controller.AppStylesheet
import pl.krakow.uek.wzisn2.etl.controller.MainViewController
import tornadofx.*

class MainView : View() {
    private val controller: MainViewController by inject()

    override val root = vbox {
        addClass(AppStylesheet.mainView)
        label {
            text = "Web Scrapper"
            style {
                paddingBottom = 20
                fontSize = 24.px
                textAlignment = TextAlignment.CENTER
                fontWeight = FontWeight.EXTRA_BOLD
            }
        }
        add(ProgressButton("Run ETL process") {
            controller.startEtl()
        })
        add(ProgressButton("Run E process") {
            controller.startE()
        })
        add(ProgressButton("Run T process") {
            controller.startT()
        })
        add(ProgressButton("Run L process") {
            controller.startL()
        })
    }
}