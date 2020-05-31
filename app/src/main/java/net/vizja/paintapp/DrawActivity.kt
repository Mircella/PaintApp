package net.vizja.paintapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class DrawActivity : AppCompatActivity() {

    var brushMode = "normal"
    var brushThickness = "1"

    private lateinit var view: DrawView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        view = DrawView(this)
        setContentView(view)
        view.requestFocus()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Change color" -> {
                val colorDialog = ColorDialog {
                    view.selectedBrushColor = it
                    view.clearCache()
                    view.initBrush()
                }
                colorDialog.show(supportFragmentManager, "colorChoiceDialog")
            }
            "Choose brush mode" -> {
                val brushModeDialog = BrushModeDialog {
                    view.selectedBrushMode = it
                    view.clearCache()
                    view.initBrush()
                }
                brushModeDialog.show(supportFragmentManager, "brushModeDialog")
            }
            "Choose thickness" -> {
                val brushThicknessDialog = BrushThicknessDialog {
                    view.selectedBrushThickness = it
                    view.clearCache()
                    view.initBrush()
                }
                brushThicknessDialog.show(supportFragmentManager, "brushThicknessDialog")
            }
            "Brush" -> {
                view.setBrushMode()
                view.initBrush()
            }
            "Eraser" -> {
                view.setEraserMode()
                view.initBrush()

            }
            "Clear" -> {
                view.clearView()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
