package net.vizja.paintapp

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View


class DrawView(context: Context) : View(context), View.OnTouchListener {

    var currentPaint: Paint = Paint()
    val paintedPoints = mutableListOf<PaintedPoints>()
    var selectedBrushColor = BrushColor.RED
    var selectedBrushMode = BrushMode.NORMAL
    var selectedBrushThickness = "1"
    var cachedBrushColor: BrushColor? = null
    var cachedBrushMode: BrushMode? = null
    var cachedBrushThickness: String? = null

    init {
        isFocusable = true
        isFocusableInTouchMode = true
        setBackgroundColor(Color.WHITE)
        setOnTouchListener(this)
        initBrush()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return event?.let {
            val point = Point()
            point.x = Math.round(it.x)
            point.y = Math.round(it.y)
            paintedPoints.find { it.paint == currentPaint }?.points?.add(point)
            invalidate()
            true
        } ?: false
    }

    override fun onDraw(canvas: Canvas?) {
        paintedPoints.forEach {
            it.points.forEach { point ->
                canvas?.drawCircle(point.x * 1.0f, point.y * 1.0f, it.radius, it.paint)
            }
        }
    }

    fun initBrush() {
        val paint = preparePaint()
        paintedPoints.add(PaintedPoints(paint, getRadius(), mutableListOf()))
        this.currentPaint = paint
    }

    private fun preparePaint(): Paint {
        val paint = Paint()
        setFilter(paint)
        paint.color = selectedBrushColor.color
        return paint
    }

    private fun setFilter(paint:Paint) {
        when(selectedBrushMode){
            BrushMode.EMBOSS -> {
                paint.colorFilter = embossFilter()
            }
            BrushMode.BLUR -> {
                paint.maskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.NORMAL)
            }
            BrushMode.NORMAL -> {
                paint.maskFilter = MaskFilter()
            }
        }
    }
    private fun getRadius(): Float{
        return try {
            selectedBrushThickness.toFloat()
        } catch (e: NumberFormatException) {
            1f
        } * 10
    }

    private fun embossFilter(): ColorFilter {
        val negativeColourMatrix = floatArrayOf(
            -1f, 0f, 0f, 0f, 255f,
            0f, -1f, 0f, 0f, 255f,
            0f, 0f, -1f, 0f, 255f,
            0f, 0f, 0f, 1f, 0f
        )
        val colorMatrix = ColorMatrix(negativeColourMatrix)
        return ColorMatrixColorFilter(colorMatrix)
    }

    fun clearView() {
        this.paintedPoints.clear()
        invalidate()
    }

    fun setBrushMode() {
        if (listOf(cachedBrushColor, cachedBrushThickness, cachedBrushMode).all { it!=null }) {
            selectedBrushThickness = checkNotNull(cachedBrushThickness)
            selectedBrushMode = checkNotNull(cachedBrushMode)
            selectedBrushColor = checkNotNull(cachedBrushColor)
        }
    }

    fun setEraserMode(){
        cachedBrushColor = selectedBrushColor
        cachedBrushMode = selectedBrushMode
        cachedBrushThickness = selectedBrushThickness
        selectedBrushColor = BrushColor.ERASER_COLOR
        selectedBrushMode = BrushMode.NORMAL
        selectedBrushThickness = "4"
    }

    fun clearCache(){
        cachedBrushColor = null
        cachedBrushMode = null
        cachedBrushThickness = null
    }
}