package GUISamples

import javafx.application.Application
import javafx.beans.value.ChangeListener
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import javafx.stage.Stage
import javafx.util.Duration
import java.io.File
import java.util.*


/** Example of playing two videos with a single media player.  */
class AudioPlaylist : Application() {
    val currentlyPlaying = Label()
    val progress = ProgressBar()
    private var progressChangeListener: ChangeListener<Duration>? = null

    @Throws(Exception::class)
    override fun start(stage: Stage) {
        stage.title = "Simple Audio Player"
        val layout = StackPane()

        // determine the source directory for the playlist (either the first parameter to the program or a
        val params = parameters.raw
        val dir = if (params.size > 0) File(params[0]) else File("C:\\Users\\Public\\Music\\Sample Music")
        if (!dir.exists() && dir.isDirectory) {
            println("Cannot find audio source directory: $dir")
        }

        // create some media players.
        val players: MutableList<MediaPlayer> = ArrayList()
        for (file in dir.list{ dir, name -> name.endsWith(".mp3") }) players.add(
            createPlayer(
                "file:///" + (dir.toString() + "\\" + file).replace(
                    "\\",
                    "/"
                ).replace(" ".toRegex(), "%20")
            )
        )
        if (players.isEmpty()) {
            println("No audio found in $dir")
            return
        }

        // create a view to show the mediaplayers.
        val mediaView = MediaView(players[0])
        val skip = Button("Skip")
        val play = Button("Pause")

        // play each audio file in turn.
        for (i in players.indices) {
            val player = players[i]
            val nextPlayer = players[(i + 1) % players.size]
            player.onEndOfMedia = Runnable {
                player.currentTimeProperty().removeListener(progressChangeListener)
                mediaView.mediaPlayer = nextPlayer
                nextPlayer.play()
            }
        }

        // allow the user to skip a track.
        skip.onAction = EventHandler {
            val curPlayer = mediaView.mediaPlayer
            val nextPlayer = players[(players.indexOf(curPlayer) + 1) % players.size]
            mediaView.mediaPlayer = nextPlayer
            curPlayer.currentTimeProperty().removeListener(progressChangeListener)
            curPlayer.stop()
            nextPlayer.play()
        }

        // allow the user to play or pause a track.
        play.onAction = EventHandler {
            if ("Pause" == play.text) {
                mediaView.mediaPlayer.pause()
                play.text = "Play"
            } else {
                mediaView.mediaPlayer.play()
                play.text = "Pause"
            }
        }

        // display the name of the currently playing track.
        mediaView.mediaPlayerProperty().addListener { observableValue, oldPlayer, newPlayer ->
            setCurrentlyPlaying(
                newPlayer
            )
        }

        // start playing the first track.
        mediaView.mediaPlayer = players[0]
        mediaView.mediaPlayer.play()
        setCurrentlyPlaying(mediaView.mediaPlayer)

        // silly invisible button used as a template to get the actual preferred size of the Pause button.
        val invisiblePause = Button("Pause")
        invisiblePause.isVisible = false
        play.prefHeightProperty().bind(invisiblePause.heightProperty())
        play.prefWidthProperty().bind(invisiblePause.widthProperty())

        // layout the scene.
//        layout.style = "-fx-background-color: cornsilk; -fx-font-size: 20; -fx-padding: 20; -fx-alignment: center;"
//        layout.children.addAll(
//            invisiblePause,
//            VBoxBuilder.create().spacing(10).children(
//                currentlyPlaying,
//                HBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(skip, play, progress, mediaView).build()
//            ).build()
//        )
        progress.maxWidth = Double.MAX_VALUE
        HBox.setHgrow(progress, Priority.ALWAYS)
        val scene = Scene(layout, 600.0, 120.0)
        stage.scene = scene
        stage.show()
    }

    /** sets the currently playing label to the label of the new media player and updates the progress monitor.  */
    private fun setCurrentlyPlaying(newPlayer: MediaPlayer) {
        progress.progress = 0.0
        progressChangeListener =
            ChangeListener { observableValue, oldValue, newValue ->
                progress.progress = 1.0 * newPlayer.currentTime.toMillis() / newPlayer.totalDuration.toMillis()
            }
        newPlayer.currentTimeProperty().addListener(progressChangeListener)
        var source = newPlayer.media.source
        source = source.substring(0, source.length - ".mp3".length)
        source = source.substring(source.lastIndexOf("/") + 1).replace("%20".toRegex(), " ")
        currentlyPlaying.text = "Now Playing: $source"
    }

    /** @return a MediaPlayer for the given source which will report any errors it encounters
     */
    private fun createPlayer(aMediaSrc: String): MediaPlayer {
        val player = MediaPlayer(Media(aMediaSrc))
        player.onError = Runnable { println("Media error occurred: " + player.error) }
        return player
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            launch(AudioPlaylist::class.java)
        }
    }
}