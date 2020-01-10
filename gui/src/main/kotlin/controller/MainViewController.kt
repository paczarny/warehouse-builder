package pl.krakow.uek.wzisn2.etl.controller

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.slf4j.LoggerFactory
import pl.krakow.uek.wzisn2.etl.advert.Advert
import pl.krakow.uek.wzisn2.etl.advert.AdvertRepository
import pl.krakow.uek.wzisn2.etl.advert.AdvertService
import pl.krakow.uek.wzisn2.etl.db.DatabaseConnector
import pl.krakow.uek.wzisn2.etl.etl.ExtractService
import pl.krakow.uek.wzisn2.etl.etl.LoadService
import pl.krakow.uek.wzisn2.etl.etl.TransformService
import pl.krakow.uek.wzisn2.etl.scrapper.DetailPageScrapper
import pl.krakow.uek.wzisn2.etl.view.ProgressButton
import tornadofx.*

class MainViewController : Controller() {
    val advertsSize = SimpleStringProperty()
    private val logger = LoggerFactory.getLogger(MainViewController::class.java)

    private var advertService: AdvertService
    private var connector: DatabaseConnector = DatabaseConnector()
    private val extractService: ExtractService
    private val transformService: TransformService
    private val loadService: LoadService
    private val advertRepository: AdvertRepository

    private var pages = emptyList<DetailPageScrapper>()
    private var transformedPages = emptyList<Advert>()

    var adverts: ObservableList<Advert> = FXCollections.observableArrayList<Advert>()

    init {
        val url = "https://www.olx.pl/nieruchomosci/domy/krakow/"
        connector.start()

        advertRepository = AdvertRepository(connector.db)
        advertService = AdvertService(advertRepository)
        extractService = ExtractService(url)
        transformService = TransformService()
        loadService = LoadService(advertRepository)

        refreshList()
    }

    fun startEtl(
            disabled: SimpleBooleanProperty,
            extractButton: ProgressButton,
            transformButton: ProgressButton,
            loadButton: ProgressButton
    ) {
        extractButton.invokeAction(disabled) { startE() }
        transformButton.invokeAction(disabled) { startT() }
        loadButton.invokeAction(disabled) { startL() }
    }

    fun startE() {
        logger.info("Extract process started")
        pages = extractService.extractAllAdverts()
    }

    fun startT() {
        println("Transform process started")
        transformedPages = transformService.transformPages(pages)
    }

    fun startL() {
        println("Load process started")
        loadService.load(transformedPages)
        refreshList()
    }

    fun exportAll() {
        logger.info("Export into the one CSV file")
        val header = arrayOf("Id", "Price", "Revision", "Area", "Market", "Construction Type", "Username")
        advertService.exportAllData(header, "files/allData.csv");
    }

    fun exportSingly() {
        logger.info("Export into single files")
        val header = arrayOf("Id", "Price", "Revision", "Area", "Market", "Construction Type", "Username")
        advertService.exportSingly(header, "files/");
    }

    fun clearDb() {
        val allAdverts = advertService.findAll()
        for (advert in allAdverts) {
            advertService.delete(advert)
        }
        refreshList()
    }

    private fun refreshList() {
        runLater {
            val allAdverts = advertService.findAll()
            adverts.clear()
            adverts.addAll(allAdverts)
            advertsSize.value = "${allAdverts.size} items"
        }
    }
}