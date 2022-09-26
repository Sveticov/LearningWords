package com.svetikov.learningwords.model

data class Word(
    val id: Int = 0,
    var name: String = "",
    val image: String = "",
    var nameCharShuffle:MutableList<Char> = mutableListOf('d'),
    var nameCharExpect:MutableList<Char> = mutableListOf('d'),
)
