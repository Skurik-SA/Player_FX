package GUISamples

import javafx.application.Application
import javafx.beans.InvalidationListener
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import javafx.stage.Stage
//import kotlinx.coroutines.Runnable

import java.io.File
import java.net.URI
import java.util.regex.Pattern

class MPlayer : Application() {
    internal var selectedFile: File? = null
    internal var mplayer: MediaPlayer? = null
    internal var musicSlider: Slider

    init {
        musicSlider = Slider()
    }

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        var media: Media? = null
        var url: URI? = null

        val root = object : BorderPane() {
            init {
                val filenameLabel = Label("sfaas")
                val fileChooser = FileChooser()
                fileChooser.title = "Open File"
                fileChooser.extensionFilters.addAll(
                    ExtensionFilter("Audio Files", "*.wav", "*.mp3")
                )

                val trackNumber = Text("1")
                trackNumber.font = Font.font("Arial")

                val rateSlider = Slider(0.0, 8.0, 1.0)
                rateSlider.isShowTickLabels = true
                rateSlider.majorTickUnit = 1.0
                rateSlider.valueProperty().addListener(InvalidationListener {
                    mplayer?.rate = rateSlider.value
                })

                val menubar = object : MenuBar() {
                    init {
                        val menu = object : Menu("File") {
                            init {
                                val selectMenuItem = object : MenuItem("Select") {
                                    init {
                                        setOnAction { e ->
                                            selectedFile = fileChooser.showOpenDialog(primaryStage)
                                            if (selectedFile != null) {

                                                val spacePattern = Pattern.compile(" ")
                                                url = selectedFile!!.toURI()
                                                //val matcher = spacePattern.matcher(url)
                                                //url = matcher.replaceAll("\\ ")

                                                println(url.toString())
                                                media = Media(url.toString())

                                                mplayer = MediaPlayer(media)

                                                musicSlider.min = 0.0
                                                musicSlider.max = 100.0
                                            }
                                        }
                                    }
                                }

                                val playMenuItem = object : MenuItem("Play") {
                                    init {
                                        setOnAction { e ->
                                            if (selectedFile != null) {
                                                mplayer?.play()
                                                if (musicSlider.value == 100.0)
                                                {
                                                    musicSlider.value = 0.0
                                                }
                                            }
                                        }
                                    }
                                }

                                val pauseMenuItem = object : MenuItem("Pause") {
                                    init {
                                        setOnAction { e ->
                                            if (selectedFile != null) {
                                                mplayer?.pause()
                                            }
                                        }
                                    }
                                }

                                val stopMenuItem = object : MenuItem("Stop") {
                                    init {
                                        setOnAction { e ->
                                            if (selectedFile != null) {
                                                mplayer?.stop()
                                            }
                                        }
                                    }
                                }
                                items.addAll(selectMenuItem, playMenuItem, pauseMenuItem, stopMenuItem)

                            }
                        }


                val vbox = object : VBox() {
                    init {
                        children.add(filenameLabel)

                        val hbox = object : HBox() {
                            init {
                                val playButton = Button("Play")
                                val pauseButton = Button("Pause")
                                val stopButton = Button("Stop")


                                playButton.setOnAction { e -> mplayer?.play() }
                                pauseButton.setOnAction { e -> mplayer?.pause() }
                                stopButton.setOnAction { e -> mplayer?.stop()}

//                                val stopButton = object : Button("Stop") {
//                                    init {
//                                        setOnAction { e -> mplayer?.stop() }
//                                    }
//                                }
                                children.addAll(playButton, pauseButton, stopButton)
                            }
                        }
                        children.add(hbox)

                        val hbox2 = object : HBox() {
                            init {
                                val nextButton = Button("Next")
                                val prevButton = Button("Prev")

                                children.addAll(prevButton, nextButton)
                            }

                        }

                        val hbox3 = object : HBox() {
                            init {
                                children.addAll(rateSlider)
                            }
                        }

                        children.addAll(hbox2, hbox3)
                    }
                }
                center = vbox

                val vbox2 = object : VBox() {
                    init {
                        val blockOfTracks = object : HBox() {
                            init {
                                val numberBox = object : VBox() {
                                    init {
                                        var numLabel = Label("No   ")
                                        var totalTimeOfTrack: Double? = null
                                        if (selectedFile != null) {


                                        }
                                        children.add(numLabel)
                                    }

                                }

                                val nameTrackBox = object : VBox() {
                                    init {
                                        var nameLabel = Label("Track  ")
                                        var nTrack = Text(url.toString())
                                        children.addAll(nameLabel, nTrack)
                                    }
                                }

                                val timeTrack = object : VBox() {
                                    init {
                                        var timeLabel = Label("Time  ")

                                        children.add(timeLabel)
                                    }
                                }

                                children.addAll(numberBox, nameTrackBox, timeTrack)
                            }

                        }
                        children.add(blockOfTracks)
                    }
                }
                left = vbox2



                        val menuP2 = object : Menu("Player") {
                            init {
                                var barCompositionChoiceNext = object: MenuItem("Next") {
                                    init {

                                    }
                                }

                                var barCompositionChoicePrev = object: MenuItem("Prev") {
                                    init {

                                    }
                                }

                                items.addAll(barCompositionChoiceNext,barCompositionChoicePrev)
                            }
                        }
                        menus.addAll(menu, menuP2)
                    }
                }
                top = menubar

                bottom = musicSlider
            }
        }

        Thread(Runnable {
            while (true) {
                if (mplayer != null) {
                    val currentTime = mplayer?.currentTime!!.toSeconds()
                    val allTime =  mplayer?.stopTime!!.toSeconds()

                    musicSlider.value = currentTime * 100.0 / allTime
                    println("Cur time " + currentTime * 100.0 / allTime)
                }
                try {
                    Thread.sleep(900)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }).start()


        val scene = Scene(root, 600.0, 200.0)

        primaryStage.scene = scene
        primaryStage.show()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(MPlayer::class.java)
        }
    }
}

