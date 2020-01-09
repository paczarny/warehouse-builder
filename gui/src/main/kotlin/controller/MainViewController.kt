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
            extractInProgress: SimpleBooleanProperty,
            transformInProgress: SimpleBooleanProperty,
            loadInProgress: SimpleBooleanProperty
    ) {
        startE(extractInProgress)
        startT(transformInProgress)
        startL(loadInProgress)
    }

    fun startE(inProgress: SimpleBooleanProperty) {
        logger.info("Extract process started")
        inProgress.value = true
        pages = extractService.extractAllAdverts()
        inProgress.value = false
    }

    fun startT(inProgress: SimpleBooleanProperty) {
        println("Transform process started")
        inProgress.value = true
        transformedPages = transformService.transformPages(pages)
        inProgress.value = false
    }

    fun startL(inProgress: SimpleBooleanProperty) {
        println("Load process started")
        inProgress.value = true
        loadService.load(transformedPages)
        inProgress.value = false
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