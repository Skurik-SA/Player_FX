package GUISamples

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.effect.DropShadow
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import javafx.scene.paint.Color
import javafx.stage.Stage

class PlayerLearning2: Application() {

    override fun start(primaryStage: Stage) {
        // Locate the media content in the CLASSPATH
        var mediaUrl = javaClass.getResource("/video.mp4")
        var mediaStringUrl = mediaUrl.toExternalForm()

        // Create a Media
        var media = Media(mediaStringUrl)

        // Create a Media Player
        var player = MediaPlayer(media)
        // Automatically begin the playback
        player.isAutoPlay = true

        // Create a 400X300 MediaView
        var mediaView = MediaView(player)
        mediaView.fitHeight = 300.0
        mediaView.fitWidth = 400.0
        mediaView.isSmooth = true

        // Create the DropShadow effect
        var dropShadow = DropShadow()
        dropShadow.offsetY = 5.0
        dropShadow.offsetX = 5.0
        dropShadow.color = Color.WHITE

        mediaView.effect = dropShadow

        // Create the Buttons
        var playButton = Button("Play")
        var stopButton = Button("Stop")
        var pauseButton = Button("Pause")

        // Create the Event Handlers for the Button
        playButton.onAction = EventHandler { ActionEvent ->
            if (player.status == MediaPlayer.Status.PLAYING) {
                player.stop()
                player.play()
            }
            else {
                player.play()
            }
        }

        stopButton.onAction = EventHandler { ActionEvent ->
            player.stop()
        }

        pauseButton.onAction = EventHandler { ActionEvent ->
            player.pause()
        }

        // Create the HBox
        val controlBox = HBox(5.0, playButton, pauseButton, stopButton)

        // Create the VBox
        val root = VBox(5.0, mediaView, controlBox)

        // Set the Style-properties of the HBox
        root.style = "-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;"

        val scene = Scene(root)
        primaryStage.title = "SDJSF"
        primaryStage.scene = scene
        primaryStage.show()

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(PlayerLearning2::class.java)
        }
    }
}