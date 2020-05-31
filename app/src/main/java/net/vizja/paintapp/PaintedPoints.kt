package net.vizja.paintapp

import android.graphics.Paint
import android.graphics.Point

data class PaintedPoints(val paint: Paint, val radius: Float, val points: MutableList<Point>)