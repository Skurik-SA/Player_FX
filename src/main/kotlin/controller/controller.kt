package controller

import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.media.MediaPlayer

class MainController {
    @FXML
    lateinit var testButton: Button
    lateinit var playButton: Button
    internal var mplayer: MediaPlayer? = null

    fun initialize() {
        println("Controller working")

    }

    fun clickMe() {

        println("Button Clicked")
    }

    fun playButtonf() {
        playButton.setOnAction { e->
            mplayer?.play()
        }
    }
}