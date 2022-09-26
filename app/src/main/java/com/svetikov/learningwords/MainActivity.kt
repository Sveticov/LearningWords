package com.svetikov.learningwords

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.svetikov.learningwords.data.DataWords
import com.svetikov.learningwords.model.Word
import com.svetikov.learningwords.ui.theme.LearningWordsTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Word", "Start")
        DataWords.wordsList.forEach { Log.d("Word", "$it") }

        super.onCreate(savedInstanceState)
        setContent {
            LearningWordsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val modelView: WordModelView = viewModel()

                    ChooseWord(modelView = modelView)
                }
            }
        }
    }
}

@Composable
fun ChooseWord(modelView: WordModelView) {
    var word by remember {
        mutableStateOf(Word())
    }
    var successText by remember {
        mutableStateOf("")
    }
    var startStatus by remember {
        mutableStateOf(0)
    }

    Column(modifier = Modifier.padding(20.dp)) {
//Image(bitmap = , contentDescription = )

        Button(onClick = {
            word = modelView.getWord()
            startStatus = 0
            successText = ""
            modelView.txtGlobal = ""

        }) {
            Text(text = " New Word")
        }
        Text(text = successText, fontSize = 55.sp)


        Row() {
            word.nameCharShuffle.forEach {

                val scope = rememberCoroutineScope()
                var statusWord by remember {
                    mutableStateOf(0)
                }
                // if (statusWord == startStatus) {

                Log.d("WORD", "statusWord:$statusWord ")
                if (startStatus == 0) {
                    statusWord = 0

                }

                var hide by remember {
                    mutableStateOf(true)
                }

                Card(
                    backgroundColor = when (statusWord) {
                        0 -> Color.LightGray
                        1 -> Color.Green
                        2 -> Color.Red
                        else -> {
                            Color.LightGray
                        }
                    },

                    elevation = 8.dp,

                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            if (statusWord != 1) {
                                statusWord = modelView.compareWord(word.nameCharExpect, it)
                                if (statusWord == 2)
                                    scope.launch {
                                        delay(1000L)
                                        statusWord = 0
                                    }
                                if (statusWord == 1) {
                                    successText = modelView.addLatter(it)
                                    startStatus = 4
                                }
                            }

                        },


                    ) {

                    Text(
                        text = "$it",
                        fontSize = 35.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                }


            }
        }


    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LearningWordsTheme {
        // ChooseWord("Android")
    }
}