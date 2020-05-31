package net.vizja.paintapp

enum class BrushMode (val value: String){
    NORMAL("normal"), BLUR("blur"), EMBOSS("emboss");

    companion object {
        fun of(value: String) = values().find { it.value == value } ?: throw IllegalArgumentException()
    }
}