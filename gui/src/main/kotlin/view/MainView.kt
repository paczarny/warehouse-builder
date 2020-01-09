package pl.krakow.uek.wzisn2.etl.view

import javafx.beans.property.SimpleBooleanProperty
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment
import pl.krakow.uek.wzisn2.etl.advert.Advert
import pl.krakow.uek.wzisn2.etl.controller.AppStylesheet
import pl.krakow.uek.wzisn2.etl.controller.MainViewController
import tornadofx.*

class MainView : View() {
    private val controller: MainViewController by inject()
    private val disable = SimpleBooleanProperty(false)

    override val root = borderpane {
        left = vbox {
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
            val extractButton = ProgressButton("Run E process", disable) {
                controller.startE(disable)
            }
            val transformButton = ProgressButton("Run T process", disable) {
                controller.startT(disable)
            }
            val loadButton = ProgressButton("Run L process", disable) {
                controller.startL(disable)
            }
            add(ProgressButton("Run ETL process", disable) {
                controller.startEtl(extractButton.actionInProgress, transformButton.actionInProgress, loadButton.actionInProgress)
            })

            add(extractButton)
            add(transformButton)
            add(loadButton)
            add(ProgressButton("Export singly", disable) {
                controller.exportSingly()
            })
            add(ProgressButton("Export all", disable) {
                controller.exportAll()
            })
            add(ProgressButton("Clear database", disable) {
                controller.clearDb()
            })
        }

        center = vbox {
            style {
                padding = box(20.px)
            }
            tableview<Advert> {
                items = controller.adverts
                column("Id", Advert::getId)
                column("Price", Advert::getPrice)
                column("Revision", Advert::getRevision)
                column("Area", Advert::getArea)
                column("Market", Advert::getMarket)
                column("Construction Type", Advert::getConstructionType)
                column("Username", Advert::getUsername)
            }
            label(controller.advertsSize)
        }
    }
}