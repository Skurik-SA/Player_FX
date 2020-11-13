package GUISamples

import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.effect.DropShadow
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.util.Duration
import java.awt.MediaTracker

class PlayerLearning6: Application() {

    override fun start(primaryStage: Stage) {
        var mediaUrl = javaClass.getResource("/quok.mp3")
        var mediaUrlString = mediaUrl.toExternalForm()

        var media = Media(mediaUrlString)

        var player = MediaPlayer(media)
        player.isAutoPlay = true
        player.rate = 8.0
        player.volume = 0.4

        // Set the Times of the Player
        player.startTime = Duration.minutes(1.0)
        player.stopTime = Duration.minutes(2.0)

        var mediaView = MediaView(player)
        mediaView.fitHeight = 300.0
        mediaView.fitWidth = 400.0
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

        var sliderPane = GridPane()
        sliderPane.vgap = 10.0
        sliderPane.hgap = 5.0

        val controlBox = HBox(5.0, playButton, pauseButton, stopButton)

        val root = VBox(5.0, mediaView, controlBox, sliderPane)

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
            launch(PlayerLearning6::class.java)
        }
    }
}