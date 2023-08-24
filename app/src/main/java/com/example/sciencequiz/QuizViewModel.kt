package com.example.sciencequiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    var currentIndex = 0
    var isCheater = false


    val arrayScienceQuestions = arrayOf(
        Science(R.string.question_one,false),
        Science(R.string.question_two,true),
        Science(R.string.question_three,false),
        Science(R.string.question_four,true),
        Science(R.string.question_five,false),
        Science(R.string.question_six,false),
        Science(R.string.question_seven,true)
    )

    fun changeIndex(){
        if (arrayScienceQuestions.size - 1 == currentIndex){
            currentIndex = 0
        }else{
            currentIndex++
        }
    }
}