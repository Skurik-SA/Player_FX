package GUISamples

import javafx.application.Application
import javafx.beans.InvalidationListener
import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.stage.FileChooser
import javafx.stage.Stage
import javafx.util.Duration
import java.io.File
import java.net.URI
import javax.swing.text.LabelView

class PlayerF: Application() {
    internal var selectedFile: File? = null
    internal var player: MediaPlayer? = null
    internal var musicSlider = Slider()

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {

        var players: MutableList<MediaPlayer> = ArrayList()


        var media: Media? = null
        var mediaUrl: URI? = null
        var timeSlider = Slider()

        val fileChooser = FileChooser()
        fileChooser.title = "Open File"
        fileChooser.extensionFilters.addAll(
            FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3")
        )

        var menu = Menu("File")

        var selectedMenuItem = MenuItem("Select")
        selectedMenuItem.setOnAction { e ->
            selectedFile = fileChooser.showOpenDialog(primaryStage)
            if (selectedFile != null) {

                player = selectFileToPlayer(selectedFile!!)
                player!!.isAutoPlay = true
//                mediaUrl = selectedFile!!.toURI()
//
//                println(mediaUrl.toString())
//                media = Media(mediaUrl.toString())
//
//                player = MediaPlayer(media)


                timeSlider.min = 0.0
                timeSlider.max = 100.0
            }
        }

        var playMenuItem = MenuItem("Play")
        var pauseMenuItem = MenuItem("Pause")
        var stopMenuItem = MenuItem("Stop")

        playMenuItem.setOnAction { e ->
            player?.play()
        }


        pauseMenuItem.setOnAction { e ->
            player?.pause()
        }

        pauseMenuItem.setOnAction { e ->
            player?.stop()
        }


        menu.items.addAll(selectedMenuItem, playMenuItem, pauseMenuItem, stopMenuItem)

        var trackMenu = Menu("Track")

        var nextMenuItem = MenuItem("Next")
        var prevMenuItem = MenuItem("Prev")

        trackMenu.items.addAll(nextMenuItem, prevMenuItem)

        var menuBar = MenuBar(menu, trackMenu)




        var playButton = Button("Play")
        playButton.setOnAction { e ->
            player?.play()
        }

        var pauseButton = Button("Pause")
        pauseButton.onAction = EventHandler {
            player?.pause()
        }

        var stopButton = Button("Stop")
        stopButton.setOnAction { ActionEvent ->
            player?.stop()
        }

        val volumeSlider = Slider(0.0, 1.0, 0.5)
        volumeSlider.majorTickUnit = 0.1
        volumeSlider.isShowTickLabels = true
        volumeSlider.valueProperty().addListener(InvalidationListener {
            player?.volume = volumeSlider.value
        })

        val rateSlider = Slider(0.0, 8.0, 1.0)
        rateSlider.majorTickUnit = 1.0
        rateSlider.isShowTickLabels = true
        rateSlider.valueProperty().addListener(InvalidationListener {
            player?.rate = rateSlider.value
        })

        val controlBox = HBox(playButton, pauseButton, stopButton)

        val sliderPane = GridPane()
        sliderPane.vgap = 10.0
        sliderPane.hgap = 5.0

        timeSlider.valueProperty().addListener(InvalidationListener {
           if (timeSlider.isValueChanging )
               player?.seek(Duration.seconds(timeSlider.value.toDouble()))
        })

        var playTime = Label("0/ 0")
        playTime.maxWidth = 130.0
        playTime.maxHeight = 50.0

        Thread(Runnable {
            while (true) {
                if (player != null) {
                    val currentTime = player?.currentTime!!.toSeconds()
                    val allTime =  player?.stopTime!!.toSeconds()


                    player?.currentTimeProperty()?.addListener { observable, oldValue, newValue -> timeSlider.value = newValue.toSeconds() }
                    timeSlider.maxProperty().bind(
                        Bindings.createDoubleBinding(
                            { player?.getTotalDuration()?.toSeconds() },
                            player?.totalDurationProperty()
                        )
                    )
                    timeSlider.value = currentTime

                    println("Cur time " + player?.currentTime!!.toSeconds())
                }
                try {
                    Thread.sleep(900)
                }
                catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }).start()
//
//
//        timeSlider.valueProperty().addListener { observable, oldValue, newValue -> player?.seek(Duration.seconds(newValue.toDouble())) }

//        timeSlider.valueProperty().addListener(InvalidationListener {
//            if (timeSlider.isValueChanging)
//            {
//                player.currentTime =
//            }
//        })


        sliderPane.addRow(0, Label("Volume"), volumeSlider)
        sliderPane.addRow(1, Label("Rate"), rateSlider)

        val playerHbox = HBox(playTime)
        val root = VBox(menuBar, controlBox, sliderPane, playerHbox, timeSlider)

        var scene = Scene(root, 600.0, 400.0)



        primaryStage.title = "Player"
        primaryStage.scene = scene
        primaryStage.show()
    }

    private fun selectFileToPlayer(selectedFile: File) : MediaPlayer {

        var tempPlayer = MediaPlayer(Media(selectedFile!!.toURI().toString()))
        tempPlayer.onError = Runnable { println("Media error occurred: " + tempPlayer.error) }

        return tempPlayer
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(PlayerF::class.java)
        }
    }
}