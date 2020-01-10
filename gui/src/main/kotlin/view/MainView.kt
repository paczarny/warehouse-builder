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
    private val disableETL = SimpleBooleanProperty(false)
    private val disableE = SimpleBooleanProperty(false)
    private val disableT = SimpleBooleanProperty(true)
    private val disableL = SimpleBooleanProperty(true)

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
            val extractButton = ProgressButton("Run E process", disableE) {
                disableAll()
                controller.startE()
                enable(disableETL, disableE, disableT)
            }
            val transformButton = ProgressButton("Run T process", disableT) {
                disableAll()
                controller.startT()
                enable(disableETL, disableE, disableT, disableL)
            }
            val loadButton = ProgressButton("Run L process", disableL) {
                disableAll()
                controller.startL()
                enable(disableETL, disableE, disableT, disableL)
            }

            add(ProgressButton("Run ETL process", disableETL) {
                disableAll()
                controller.startEtl(disableETL, extractButton, transformButton, loadButton)
                enable(disableETL, disableE, disableT, disableL)
            })
            add(extractButton)
            add(transformButton)
            add(loadButton)
            add(ProgressButton("Export singly", disableETL) {
                controller.exportSingly()
            })
            add(ProgressButton("Export all", disableETL) {
                controller.exportAll()
            })
            add(ProgressButton("Clear database", disableETL) {
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

    private fun disableAll() {
        disableETL.value = true
        disableE.value = true
        disableT.value = true
        disableL.value = true
    }

    private fun enable(vararg booleans: SimpleBooleanProperty) {
        booleans.forEach { it.value = false }
    }
}