package com.example.sciencequiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProviders
import com.example.sciencequiz.databinding.ActivityMainBinding

private const val INDEX = "index"
private const val CUR_INDEX = "curIndex"
private const val IS_CHEAT = "isCheat"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var quizViewModel: QuizViewModel
    private var info : Boolean? = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizViewModel = ViewModelProviders.of(this)[QuizViewModel::class.java]

        val currentIndex = savedInstanceState?.getInt(INDEX) ?: 0
        quizViewModel.currentIndex = currentIndex

        binding.question.setText(quizViewModel.arrayScienceQuestions[quizViewModel.currentIndex].question)

        binding.btNext.setOnClickListener {
            quizViewModel.changeIndex()
            binding.question.setText(quizViewModel.arrayScienceQuestions[quizViewModel.currentIndex].question)
            if(info == true){
                info = false
            }
        }

        binding.btTrue.setOnClickListener {
            checkAnswer(true)
        }

        binding.btFalse.setOnClickListener {
            checkAnswer(false)
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result : ActivityResult ->
            if(result.resultCode == RESULT_OK){
                info = result.data?.getBooleanExtra(IS_CHEAT,false)
            }
        }

        binding.btCheat.setOnClickListener {
            val intent = Intent(this@MainActivity,CheatActivity::class.java)
            intent.putExtra(CUR_INDEX,quizViewModel.currentIndex)
            launcher.launch(intent)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(INDEX,quizViewModel.currentIndex)
    }

    private fun checkAnswer(answer: Boolean) {
        val currentAnswer = quizViewModel.arrayScienceQuestions[quizViewModel.currentIndex].isAnswer

        val currentToast = if (info == false && currentAnswer == answer) {
            R.string.toast_correct
        } else if (info == true && currentAnswer != answer) {
            R.string.cheating2
        } else  if(info == true && currentAnswer == answer ){
            R.string.cheating
        }
        else{
            R.string.toast_incorrect
        }

        info = false
        Toast.makeText(this, currentToast, Toast.LENGTH_SHORT).show()
    }
}