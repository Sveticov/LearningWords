package com.svetikov.learningwords

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svetikov.learningwords.data.DataWords
import com.svetikov.learningwords.model.Word
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class WordModelView : ViewModel() {

    fun getWord(): Word {
        val idRandom = Random.nextInt(1, DataWords.wordsList.size + 1)
        var word = DataWords.wordsList

            .last { it.id == idRandom }

        var nameShuffle = word.name.toMutableList().shuffled().toMutableList()
        var nameRight = word.name.toMutableList()
        word.nameCharShuffle = nameShuffle
        word.nameCharExpect = nameRight
        Log.d("WORD", "name :$nameShuffle    word : $nameRight")


        return word
    }

    var count: Int = 0
    private var index = 0
    private var status: Int = 0
    val list = mutableListOf<Boolean>(false)
    fun compareWord(rightWord: MutableList<Char>, expectLatter: Char): Int {

        Log.d("WORD", " $rightWord   $expectLatter  ${count++}")
        if (rightWord[index] == expectLatter) {
            index++
            status = 1
            Log.d("WORD", "true  $index   $status")
        } else {
            status = 2
        }
        if (index == rightWord.size) {
            viewModelScope.launch {
                delay(1000L)
                index = 0
                status = 0

                Log.d("WORD", "success status: $status   index :$index")
            }


            Log.d("WORD", "success status: $status   index :$index")
        }
        return status
    }

    var txtGlobal = ""
    fun addLatter(it: Char):String {
        txtGlobal += it.toString()
        Log.d("WORD", "success status: $txtGlobal")
        return txtGlobal
    }
}

//data class StatusCompare(
//
//)