package GUISamples

import javafx.application.Application
import javafx.beans.InvalidationListener
import javafx.beans.Observable
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.control.TextArea
import javafx.scene.effect.DropShadow
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import javafx.scene.paint.Color
import javafx.stage.Stage

class PlayerLearning5: Application() {

    var messageArea = TextArea()

    override fun start(primaryStage: Stage) {

        var cycleSlider = Slider(1.0, 5.0, 1.0)
        cycleSlider.majorTickUnit = 1.0
        cycleSlider.isShowTickLabels = true

        var volumeSlider = Slider(0.0, 1.0, 5.0)
        volumeSlider.majorTickUnit = 0.1
        volumeSlider.isShowTickLabels = true

        var rateSlider = Slider(0.0, 8.0, 1.0)
        rateSlider.majorTickUnit = 1.0
        rateSlider.isShowTickLabels = true

        var balanceSlider = Slider(-1.0, 1.0, 0.0)
        balanceSlider.majorTickUnit = 0.2
        balanceSlider.isShowTickLabels = true

        var mediaUrl = javaClass.getResource("/video.mp4")
        var mediaUrlString = mediaUrl.toExternalForm()

        var media = Media(mediaUrlString)

        var player = MediaPlayer(media)
        player.isAutoPlay = true

        var mediaView = MediaView(player)
        mediaView.fitWidth = 400.0
        mediaView.fitHeight = 300.0
        mediaView.isSmooth = true

        var dropShadow = DropShadow()
        dropShadow.offsetY = 5.0
        dropShadow.offsetX = 5.0
        dropShadow.color = Color.WHITE

        mediaView.effect = dropShadow

        var playButton = Button("Play")
        var pauseButton = Button("Pause")
        var stopButton = Button("Stop")

        playButton.onAction = EventHandler { ActionEvent ->
            if (player.status == MediaPlayer.Status.PLAYING)
            {
                player.stop()
                player.play()
            }
            else
            {
                player.play()
            }
        }

        pauseButton.onAction = EventHandler { ActionEvent ->
            player.pause()
        }

        stopButton.onAction = EventHandler { ActionEvent ->
            player.stop()
        }


        cycleSlider.valueProperty().addListener(InvalidationListener {
            if (cycleSlider.isValueChanging)
            {
                messageArea.appendText("\nCycle Count changed to: ${cycleSlider.value.toInt()}")
                player.cycleCount = cycleSlider.value.toInt()
            }
        })

        volumeSlider.valueProperty().addListener(InvalidationListener {
            if (volumeSlider.isValueChanging)
            {
                messageArea.appendText("\nVolume changed to: ${volumeSlider.value}")
                player.volume = volumeSlider.value
            }
        })

        rateSlider.valueProperty().addListener(InvalidationListener {
            if (rateSlider.isValueChanging)
            {
                messageArea.appendText("\nRate changed to: ${rateSlider.value}")
                player.rate = rateSlider.value
            }
        })

        balanceSlider.valueProperty().addListener(InvalidationListener {
            if(balanceSlider.isValueChanging)
            {
                messageArea.appendText("\nBalance changed to: ${balanceSlider.value}")
                player.balance = balanceSlider.value
            }
        })

        player.onEndOfMedia = Runnable {
            messageArea.appendText("\nEnd of Media")
        }

        player.onRepeat = Runnable {
            messageArea.appendText("\nRepeating media")
        }

        var sliderPane = GridPane()
        sliderPane.vgap = 10.0
        sliderPane.hgap = 5.0

        sliderPane.addRow(0, Label("CycleCount"), cycleSlider)
        sliderPane.addRow(1, Label("Volue"), volumeSlider)
        sliderPane.addRow(2, Label("Rate"), rateSlider)
        sliderPane.addRow(3, Label("Balance"), balanceSlider)

        val controlBox = HBox(5.0, playButton, pauseButton, stopButton)

        val root = VBox(5.0, mediaView, controlBox, sliderPane, messageArea)

        root.style = "-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;"

        val scene = Scene(root)

        primaryStage.title = "HIDID"
        primaryStage.scene = scene
        primaryStage.show()




    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(PlayerLearning5::class.java)
        }
    }
}