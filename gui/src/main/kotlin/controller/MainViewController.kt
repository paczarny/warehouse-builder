package pl.krakow.uek.wzisn2.etl.controller

import javafx.beans.property.SimpleObjectProperty
import org.slf4j.LoggerFactory
import pl.krakow.uek.wzisn2.etl.advert.AdvertService
import pl.krakow.uek.wzisn2.etl.db.DatabaseConnector
import tornadofx.*
import java.lang.Thread.sleep

class MainViewController : Controller() {
    private val logger = LoggerFactory.getLogger(MainViewController::class.java)

    private var advertService: AdvertService
    private var connector: DatabaseConnector = DatabaseConnector()

    init {
        connector.start()
        advertService = AdvertService(connector)
    }

    fun startEtl(it: SimpleObjectProperty<Pair<Long, Long>>) {
        logger.info("ETL process started")
        val url = "https://www.olx.pl/nieruchomosci/domy/krakow/"
        val lastPage = advertService.getLastPage(url)

        for (i in 1 until lastPage) {
            logger.info("Processing $i/$lastPage page")
            it.set(Pair(i.toLong(), lastPage.toLong()))
            advertService.scrapListPage("$url?page=$i")
        }
        it.set(Pair(lastPage.toLong(), lastPage.toLong()))

        logger.info("End")
    }

    fun startE(it: SimpleObjectProperty<Pair<Long, Long>>) {
        logger.info("Extract process started")
        val lastPage = 10L
        for (i in 1 until lastPage) {
            logger.info("Processing $i/$lastPage page")
            it.set(Pair(i, 10))
            sleep(1000)
        }
        it.set(Pair(lastPage, lastPage))

    }

    fun startT() {
        println("Transform process started")
    }

    fun startL() {
        println("Load process started")
    }
}