package pl.krakow.uek.wzisn2.etl.controller

import tornadofx.*

class AppStylesheet : Stylesheet() {
    companion object {
        val button by cssclass()
        val mainView by cssclass()
    }

    init {
        button {
            fontSize = 13.px
        }
        mainView {
            padding = box(20.px)
            spacing = 20.px
        }
    }
}