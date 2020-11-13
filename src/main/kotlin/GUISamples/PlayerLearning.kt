package GUISamples

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.media.AudioClip
import javafx.stage.Stage


class PlayerLearning: Application() {
    var audioClip: AudioClip? = null

    override fun start(primaryStage: Stage) {

        var resource = javaClass.getResource("/quok.mp3")
        println("work")
        audioClip = AudioClip(resource.toExternalForm())


        var playButton = Button("Play")
        var stopButton = Button("Stop")

        var cycleSlider = Slider(1.0, 5.0, 1.0)
        cycleSlider.majorTickUnit = 1.0
        cycleSlider.isShowTickLabels = true

        var volumeSlider = Slider(0.0, 1.0, 0.5)
        volumeSlider.majorTickUnit = 0.1
        volumeSlider.isShowTickLabels = true

        var rateSlider = Slider(0.0, 8.0, 1.0)
        rateSlider.majorTickUnit = 1.0
        rateSlider.isShowTickLabels = true

        var balanceSlider = Slider(-1.0, 1.0, 0.0)
        balanceSlider.majorTickUnit = 0.2
        balanceSlider.isShowTickLabels =true

        var panSlider = Slider(-1.0, 1.0, 1.0)
        panSlider.majorTickUnit = 0.2
        panSlider.isShowTickLabels = true

        var prioritySlider = Slider(0.0, 10.0, 0.0)
        prioritySlider.majorTickUnit = 1.0
        prioritySlider.isShowTickLabels = true

        playButton.onAction = EventHandler { ActionEvent ->
            audioClip?.play()
            println("GG")
        }

        stopButton.onAction = EventHandler { ActionEvent ->
            audioClip?.stop()
        }


        audioClip?.cycleCountProperty()?.bind(cycleSlider.valueProperty())
        audioClip?.volumeProperty()?.bind(volumeSlider.valueProperty())
        audioClip?.rateProperty()?.bind(rateSlider.valueProperty())
        audioClip?.balanceProperty()?.bind(balanceSlider.valueProperty())
        audioClip?.panProperty()?.bind(panSlider.valueProperty())
        audioClip?.priorityProperty()?.bind(prioritySlider.valueProperty())

        var sliderPane = GridPane()
        sliderPane.hgap = 5.0
        sliderPane.vgap = 10.0

        sliderPane.addRow(0, Label("CycleCount:"), cycleSlider);
        sliderPane.addRow(1, Label("Volume:"), volumeSlider);
        sliderPane.addRow(2, Label("Rate:"), rateSlider);
        sliderPane.addRow(3, Label("Balance:"), balanceSlider);
        sliderPane.addRow(4, Label("Pan:"), panSlider);
        sliderPane.addRow(5, Label("Priority:"), prioritySlider);

        var buttonBox = HBox(5.0, playButton, stopButton)

        val root = VBox(5.0, sliderPane, buttonBox)
        root.prefWidth = 300.0
        root.prefHeight = 350.0

        root.style = "-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;"

        val scene = Scene(root)
        primaryStage.title = "LELFRL"
        primaryStage.scene = scene
        primaryStage.show()

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(PlayerLearning::class.java)
        }
    }
}