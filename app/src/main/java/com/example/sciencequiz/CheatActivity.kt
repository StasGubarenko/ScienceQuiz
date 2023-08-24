package com.example.sciencequiz
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.sciencequiz.databinding.ActivityCheatBinding

private const val CUR_INDEX = "curIndex"
private const val IS_CHEAT = "isCheat"

class CheatActivity : AppCompatActivity() {
    lateinit var binding : ActivityCheatBinding
    private val quizViewModel : QuizViewModel by lazy {
        QuizViewModel()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentIndex : Int = intent.getIntExtra(CUR_INDEX,0)
        quizViewModel.currentIndex = currentIndex

        binding.btShowAnswer.setOnClickListener {
            val resultAnswer = when{
                quizViewModel.arrayScienceQuestions[currentIndex].isAnswer -> R.string.button_true
                else -> R.string.button_false
            }

            binding.tvOtvet.apply {
                setText(resultAnswer)
                visibility = View.VISIBLE
                quizViewModel.isCheater = true
                val i = Intent()
                i.putExtra(IS_CHEAT,quizViewModel.isCheater)
                setResult(RESULT_OK,i)
            }

        }
    }
}