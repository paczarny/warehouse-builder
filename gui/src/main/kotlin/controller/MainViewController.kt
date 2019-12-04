package pl.krakow.uek.wzisn2.etl.controller

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.slf4j.LoggerFactory
import pl.krakow.uek.wzisn2.etl.advert.Advert
import pl.krakow.uek.wzisn2.etl.advert.AdvertService
import pl.krakow.uek.wzisn2.etl.db.DatabaseConnector
import tornadofx.*
import java.lang.Thread.sleep

class MainViewController : Controller() {
    val advertsSize = SimpleStringProperty()
    private val logger = LoggerFactory.getLogger(MainViewController::class.java)

    private var advertService: AdvertService
    private var connector: DatabaseConnector = DatabaseConnector()

    var adverts: ObservableList<Advert> = FXCollections.observableArrayList<Advert>()

    init {
        connector.start()
        advertService = AdvertService(connector)
        refreshList()
    }

    fun startEtl(progressProperty: SimpleObjectProperty<Pair<Long, Long>>) {
        logger.info("ETL process started")
        val url = "https://www.olx.pl/nieruchomosci/domy/krakow/"
        val lastPage = advertService.getLastPage(url)

        for (i in 1 until lastPage) {
            logger.info("Processing $i/$lastPage page")
            updateProgress(progressProperty, i, lastPage)
            advertService.scrapListPage("$url?page=$i")
        }
        updateProgress(progressProperty, lastPage, lastPage)
        logger.info("End")
    }

    private fun updateProgress(progressProperty: SimpleObjectProperty<Pair<Long, Long>>, i: Int, lastPage: Int, refresh: Boolean = true) {
        runLater {
            progressProperty.set(Pair(i.toLong(), lastPage.toLong()))
            if (refresh) refreshList()
        }
    }

    fun startE(progressProperty: SimpleObjectProperty<Pair<Long, Long>>) {
        logger.info("Extract process started")
        val lastPage = 10
        for (i in 1 until lastPage) {
            logger.info("Processing $i/$lastPage page")
            updateProgress(progressProperty, i, 10)
            sleep(1000)
        }
        updateProgress(progressProperty, lastPage, lastPage)
    }

    fun startT() {
        println("Transform process started")
    }

    fun startL() {
        println("Load process started")
    }

    fun clearDb(progressProperty: SimpleObjectProperty<Pair<Long, Long>>) {
        val allAdverts = advertService.findAll()
        for ((index, advert) in allAdverts.withIndex()) {
            updateProgress(progressProperty, index, allAdverts.size, false)
            advertService.delete(advert)
        }
        updateProgress(progressProperty, allAdverts.size, allAdverts.size)
    }

    private fun refreshList() {
        val allAdverts = advertService.findAll()
        adverts.clear()
        adverts.addAll(allAdverts)
        advertsSize.value = "${allAdverts.size} items"
    }
}