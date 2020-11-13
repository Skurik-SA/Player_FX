package GUISamples

import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.effect.DropShadow
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.media.Media
import javafx.scene.media.MediaException
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import javafx.scene.paint.Color
import javafx.stage.Stage
import kotlin.math.roundToInt

class PlayerLearning4: Application() {
    var massageArea = TextArea()


    override fun start(primaryStage: Stage) {
        // Locate the media content in the CLASSPATH
        var mediaUrl = javaClass.getResource("/quok.mp3")
        var mediaStringUrl = mediaUrl.toExternalForm()

        // Create a Media
        var media = Media(mediaStringUrl)

        // Create a Media Player
        var player = MediaPlayer(media)
        // Automatically begin the playback
        player.isAutoPlay = true

        // Create a 400X300 MediaView
        var mediaView = MediaView(player)
        mediaView.fitWidth = 400.0
        mediaView.fitHeight = 300.0
        mediaView.isSmooth = true

        // Create the DropShadow effect
        var dropShadow = DropShadow()
        dropShadow.offsetX = 5.0
        dropShadow.offsetY = 5.0
        dropShadow.color = Color.WHITE

        mediaView.effect = dropShadow

        var playButton = Button("Play")
        var pauseButtom = Button("Pause")
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

        pauseButtom.onAction = EventHandler { ActionEvent ->
            player.pause()
        }

        stopButton.onAction = EventHandler { ActionEvent ->
            player.stop()
        }

        player.onError = Runnable {
            printMessage(player.error)
        }

        media.onError = Runnable {
            printMessage(media.error)
        }

        mediaView.onError = EventHandler { MediaErrorEvent ->
            printMessage(MediaErrorEvent.mediaError)
        }

//        player.statusProperty().addListener(ChangeListener { observableValue, oldStatus, newStatus ->
//            massageArea.appendText("\nStatus changed from $oldStatus to $newStatus")
//        })
//
//        player.onPlaying = Runnable {
//            massageArea.appendText("\nPlaying Now")
//        }
//
//        player.onPaused = Runnable {
//            massageArea.appendText("\nPaused")
//        }
//
//        player.onStopped = Runnable {
//            massageArea.appendText("\nStopped now")
//        }

        player.onReady = Runnable {
            var metadata = media.metadata
            var durationSong = media.duration
            for (key in metadata.keys)
            {
                massageArea.appendText("\n $key = ${metadata.get(key)}")
                massageArea.appendText("\n${(durationSong.toMinutes())}")
            }
        }

        val controlBox = HBox(5.0, playButton, pauseButtom, stopButton)

        val root = VBox(5.0, mediaView, controlBox, massageArea)

        root.style = "-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;"

        val scene = Scene(root)

        primaryStage.title = "SDGASDF"
        primaryStage.scene = scene
        primaryStage.show()
    }

    private fun printMessage(mediaError: MediaException) {
        var errorType = MediaException.Type.UNKNOWN.toString()
        var errorMassage = MediaException.Type.UNKNOWN.toString()
        massageArea.appendText("\n" + "Type:" + "$errorType" + ", error mesage:" + "$errorMassage")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(PlayerLearning4::class.java)
        }
    }
}