package kbs.apps.mobiledevelopment.mathsforkids

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        // Read and apply the seed from Welcome Page.
//        val seed = intent.getLongExtra("RANDOM_SEED", System.currentTimeMillis())
        val seed = intent.getLongExtra("RANDOM_SEED", 123456789)
        RandomProvider.setSeed(seed)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initiate a list to store correct answers.
        // https://stackoverflow.com/questions/50851332/declaring-list-of-multiple-types-in-kotlin
        val correctAnswers = mutableListOf<Any>()

        // Get access to items on the UI.
        // Access to questions.
        val question1TextView = findViewById<TextView>(R.id.text_view_question1_quiz)
        val question2TextView = findViewById<TextView>(R.id.text_view_question2_quiz)
        val question3TextView = findViewById<TextView>(R.id.text_view_question3_quiz)
        val question4TextView = findViewById<TextView>(R.id.text_view_question4_quiz)
        val question5TextView = findViewById<TextView>(R.id.text_view_question5_quiz)

        // Access to values of Answer 4 check boxes.
        val answer4CheckBox1 = findViewById<CheckBox>(R.id.check_box_answer4_option1_quiz)
        val answer4CheckBox2 = findViewById<CheckBox>(R.id.check_box_answer4_option2_quiz)
        val answer4CheckBox3 = findViewById<CheckBox>(R.id.check_box_answer4_option3_quiz)

        // Randomly generate 2 numbers.
        // Source: https://medium.com/@TheSomeshKumar/randoms-in-kotlin-3e8c4b46803
        val num1 = RandomProvider.nextInt(1..9)
        val num2 = RandomProvider.nextInt(1..num1)

        // Create question 1 with random numbers above.
        question1TextView.text = "$num1 + $num2 = ?"

        // Find answer 1.
        val answer1 = Functions().calculateQuestion1(num1, num2)

        // Add to the list of correct answers.
        correctAnswers.add(answer1.toString())

        // Create question 2 with random numbers above.
        val num3 = RandomProvider.nextInt(1..9)
        question2TextView.text = "Verify: \n $num1 x $num2 = $num3"

        // Find answer 2.
        var answer2: String
        if (num3 == Functions().calculateQuestion2(num1, num2)) {
            answer2 = "Correct"
        } else {
            answer2 = "Incorrect"
        }

        // Add to the list of correct answers.
        correctAnswers.add(answer2)

        // Create question 3 with random numbers above.
        question3TextView.text = "$num1 - $num2 = ?"

        // Link to the combo box for answer of question 3.
        val answer3Spinner = findViewById<Spinner>(R.id.spinner_answer3_quiz)

        // Add options to the combo box
        // https://developer.android.com/develop/ui/views/components/spinner
        // https://www.geeksforgeeks.org/spinner-in-kotlin/
        val options = listOf(Functions().calculateQuestion3(num1, num2) + 5,
            Functions().calculateQuestion3(num1, num2) - 5, Functions().calculateQuestion3(num1, num2))

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, options)
        answer3Spinner.adapter = adapter

        // Find answer 3.
        val answer3 = Functions().calculateQuestion3(num1, num2)

        // Add to the list of correct answers.
        correctAnswers.add(answer3.toString())

        // Create question 4 with random numbers above.
        question4TextView.text = "Which numbers are \n less than $num1?"
        val answer4Random1 = RandomProvider.nextInt(1..9)
        val answer4Random2 = RandomProvider.nextInt(1..9)
        val answer4Random3 = RandomProvider.nextInt(1..9)
        answer4CheckBox1.text = answer4Random1.toString()
        answer4CheckBox2.text = answer4Random2.toString()
        answer4CheckBox3.text = answer4Random3.toString()

        // Find answer 4.
        // Initiate a list to store options.
        // https://stackoverflow.com/questions/50851332/declaring-list-of-multiple-types-in-kotlin
        val optionAnswer4 = mutableListOf<Int>()
        optionAnswer4.add(answer4Random1)
        optionAnswer4.add(answer4Random2)
        optionAnswer4.add(answer4Random3)
        val answer4 = Functions().calculateQuestion4(num1, optionAnswer4)

        // Add to the list of correct answers.
        correctAnswers.add(answer4)

        // Create question 5 with random numbers.
        val num2Question5 = RandomProvider.nextInt(1..9)
        val answer5 = RandomProvider.nextInt(1..9)
        val num1Question5 = num2Question5 * answer5
        question5TextView.text = "$num1Question5 : $num2Question5 = ?"

        // Add to the list of correct answers.
        correctAnswers.add(answer5.toString())

        // Link to other elements on the UI.
        val resultButton = findViewById<Button>(R.id.button_result_quiz)
        val resetButton = findViewById<Button>(R.id.button_reset_quiz)
        val answer1EditText = findViewById<EditText>(R.id.edit_text_answer1_quiz)

        // Access to Radio Group to get the answer of question 2.
        // https://stackoverflow.com/questions/18179124/android-getting-value-from-selected-radiobutton
        val radioGroup = findViewById<RadioGroup>(R.id.radio_group_answer2_quiz)

        // Access answer 5.
        val answer5EditText = findViewById<EditText>(R.id.edit_text_answer5_quiz)

        // Get the username from Welcome page.
        val inputUsername = intent.getStringExtra("USERNAME_STRING")

        // When Result Button is clicked.
        resultButton.setOnClickListener {

            // Get value of answer 1.
            val answer1 = answer1EditText.getText().toString()

            // Get the value of selected answer.
            // If there is an option selected.
            val answer2: String
            val selectedId = radioGroup.checkedRadioButtonId

            // Add the selected option to the answer of question number 2.
            if (selectedId != -1) {
                val selectedAnswer2RadionButton = findViewById<RadioButton>(selectedId)
                answer2 = selectedAnswer2RadionButton.text.toString()
            } else {
                answer2 = ""
            }

            // Get the value of selected answer of question number 3.
            // https://www.geeksforgeeks.org/spinner-in-kotlin/
            val answer3 = answer3Spinner.selectedItem.toString()

            // Get the values of selected answers.
            // https://www.geeksforgeeks.org/how-to-check-if-checkbox-is-checked-in-android/
            val answers4 = mutableListOf<String>()

            // If the answer is selected, add to the answers list of question number 4.
            if (answer4CheckBox1.isChecked) {
                answers4.add(answer4CheckBox1.text.toString())
            }

            // If the answer is selected, add to the answers list of question number 4.
            if (answer4CheckBox2.isChecked) {
                answers4.add(answer4CheckBox2.text.toString())
            }

            // If the answer is selected, add to the answers list of question number 4.
            if (answer4CheckBox3.isChecked) {
                answers4.add(answer4CheckBox3.text.toString())
            }

            // Get value of answer 5.
            val answer5 = answer5EditText.getText().toString()

            // Add all answers to a list.
            // https://stackoverflow.com/questions/50851332/declaring-list-of-multiple-types-in-kotlin
            // The list contains both numbers, strings for answers 1, 2, 3, 5
            // and list of answers for answer 4.
            val answers = mutableListOf<Any>()

            // Add answers to list of answers.
            answers.add(answer1)
            answers.add(answer2)
            answers.add(answer3)
            answers.add(answers4)
            answers.add(answer5)

            // Validate results.
            val (results, points) = Functions().validateResult(answers, correctAnswers)

            // Navigate to the Result Activity.
            val resultActivity = Intent(this, ResultActivity::class.java)

            // Passing the username and answers to next page.
            resultActivity.putExtra("QUIZ_RESULT", QuizResult(inputUsername.toString(),
                                                            results, points))

            // Start the result page.
            startActivity(resultActivity)
        }

        // When Reset Button is clicked.
        resetButton.setOnClickListener {

            // Set answer of question 1 to empty.
            answer1EditText.setText("")

            // Clear the check in question 2.
            radioGroup.clearCheck()

            // Reset the selection of question 3.
            answer3Spinner.setSelection(0)

            // Clear the check in question 4.
            answer4CheckBox1.isChecked = false
            answer4CheckBox2.isChecked = false
            answer4CheckBox3.isChecked = false

            // Set answer of question 5 to empty.
            answer5EditText.setText("")

            // Notify users.
            Toast.makeText(this, "Quiz has been reset", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show()
    }

}