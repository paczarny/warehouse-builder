package pl.krakow.uek.wzisn2.etl.controller

import pl.krakow.uek.wzisn2.etl.ModuleRunner
import tornadofx.*

class MainViewController : Controller() {
    private var moduleRunner: ModuleRunner = ModuleRunner()

    fun startEtl() {
        println("ETL process started")
        moduleRunner.run()
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