package GUISamples

import javafx.application.Application
import javafx.fxml.FXMLLoader.load
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class GUI7 : Application() {

    val layout = "/main.fxml"

    override fun start(primaryStage: Stage?) {
        primaryStage?.scene = Scene(load<Parent?>(GUI7.javaClass.getResource(layout)))
        primaryStage?.setTitle("XML App");
        primaryStage?.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(GUI7::class.java)
        }
    }
}