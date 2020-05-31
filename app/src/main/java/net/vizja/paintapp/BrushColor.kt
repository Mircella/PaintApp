package net.vizja.paintapp

import android.graphics.Color

enum class BrushColor(val value: String, val color: Int) {
    RED("red", Color.RED),
    GREEN("green", Color.GREEN),
    BLUE("blue", Color.BLUE),
    ERASER_COLOR("eraser_color", Color.WHITE);

    companion object {
        fun of(value: String) = values().find { it.value == value } ?: throw IllegalArgumentException()
    }
}