package GUISamples

import javafx.animation.PathTransition
import javafx.animation.RotateTransition
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.paint.*
import javafx.scene.shape.*
import javafx.stage.Stage
import javafx.event.EventHandler
import javafx.scene.PerspectiveCamera
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.transform.Rotate
import javafx.scene.transform.Rotate.X_AXIS
import javafx.scene.transform.Rotate.Y_AXIS
import javafx.util.Duration

class Std: Application() {

    override fun start(primaryStage: Stage) {

        val sceneH = 1280.0
        val sceneW = 720.0

        val circle = Circle()
        circle.centerY = 135.0
        circle.centerX = 300.0
        circle.radius = 90.0
        circle.fill = Color.CRIMSON
        circle.strokeWidth = 20.0

        val path = Path()

        val moveTo = MoveTo(208.0, 71.0)

        val line1 = LineTo(421.0, 161.0)
        val line2 = LineTo(226.0, 232.0)
        val line3 = LineTo(332.0, 52.0)
        val line4 = LineTo(369.0, 250.0)
        val line5 = LineTo(208.0, 71.0)

        path.elements.add(moveTo)
        path.elements.addAll(line1, line2, line3, line4, line5)

        val pathTransition = PathTransition()
        pathTransition.duration = Duration(1000.0)
        pathTransition.node = circle
        pathTransition.path = path
        pathTransition.orientation = PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT
        pathTransition.cycleCount = 50
        pathTransition.isAutoReverse = false

        val Playbtn = Button("Play")
        Playbtn.layoutX = 300.0
        Playbtn.layoutY = 250.0

        circle.onMouseClicked = EventHandler { it: MouseEvent ->
            @Override
            circle.fill = Color.BLACK
        }

        Playbtn.onMouseClicked = EventHandler { it: MouseEvent ->
            pathTransition.play()
        }
        
        val Stopbtn = Button("Stop")
        Stopbtn.layoutX = 250.0
        Stopbtn.layoutY = 250.0

        Stopbtn.onMouseClicked = EventHandler { it: MouseEvent ->
            pathTransition.stop()
        }

        val root = Group(circle, Playbtn, Stopbtn)

        val scene = Scene(root, sceneH, sceneW)
        scene.fill = Color.LAVENDER

        primaryStage.title = "Exapmle APP"
        primaryStage.scene = scene
        primaryStage.show()
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
        launch(Std::class.java)
        }
    }
}
