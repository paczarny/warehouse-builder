package pl.krakow.uek.wzisn2.etl.controller

import pl.krakow.uek.wzisn2.etl.ModuleRunner
import tornadofx.*

class MainViewController : Controller() {
    fun startEtl() {
        println("ETL process started")
        ModuleRunner().run()
    }

    fun startE() {
        println("Extract process started")
    }

    fun startT() {
        println("Transform process started")
    }

    fun startL() {
        println("Load process started")
    }
}