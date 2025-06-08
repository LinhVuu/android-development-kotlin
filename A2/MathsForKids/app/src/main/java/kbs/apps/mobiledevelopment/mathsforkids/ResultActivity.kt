package kbs.apps.mobiledevelopment.mathsforkids

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.text.HtmlCompat

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_result)

        // Link to the Quiz page.
        val i = intent

        // Get result from Quiz page.
        val quizResult:QuizResult?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            quizResult = i?.getParcelableExtra("QUIZ_RESULT", QuizResult::class.java)
        } else {
            @Suppress("DEPRECATION", "INFERRED_TYPE_VARIABLE_INTO_POSSIBLE_EMPTY_INTERSECTION")
            quizResult = i.getParcelableExtra("QUIZ_RESULT")
        }

        // Assigns result from Quiz page to new variables.
        val points = quizResult?.points
        val username = quizResult?.username
        val results = quizResult?.results

        // Initialise a variable to store the result to display.
        var resultDisplay: String = "Hi " + username + ", here is your result: <br/><br/>"

        // Get access to the text view to display the result.
        val resultDisplayTextView = findViewById<TextView>(R.id.text_view_result)

        // Loop through the results
        for ((index,value) in results?.withIndex()!!) {
            resultDisplay = resultDisplay + value + "<br/><br/>"
        }

        // Add Total Points to the display.
        resultDisplay = resultDisplay + "Total Points: " + points + "/100"

        // Display the result.
        // https://discuss.kotlinlang.org/t/1-how-can-i-change-text-colour-of-one-word-in-a-textview-using-kotlin-dont-even-know-where-to-start/12348
        resultDisplayTextView.text = HtmlCompat.fromHtml(resultDisplay, HtmlCompat.FROM_HTML_MODE_LEGACY)

        // Link to image element on the UI.
        val resultImageView = findViewById<ImageView>(R.id.image_view_after_quiz_result)

        if (points == 100) {
            resultImageView.setImageResource(R.drawable.congrats_static)
        } else {
            resultImageView.setImageResource(R.drawable.try_again_static)
        }

        // Link to button element on the UI.
        val backButton = findViewById<Button>(R.id.button_back_result)

        // When users click on the 'Back' button, go back to previous screen.
        // https://stackoverflow.com/questions/51684696/how-to-goback-to-the-previous-activity-kotlin-using-a-button-outside-the-act
        backButton.setOnClickListener {
            finish()
        }


    }
}