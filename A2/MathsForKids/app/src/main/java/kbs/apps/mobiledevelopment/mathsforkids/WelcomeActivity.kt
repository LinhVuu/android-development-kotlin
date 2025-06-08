package kbs.apps.mobiledevelopment.mathsforkids

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WelcomeActivity : AppCompatActivity() {

    // Create the screen.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)

        // Link to elements on the UI.
        val userNameEditText = findViewById<EditText>(R.id.edit_text_username_welcome)
        val startButton = findViewById<Button>(R.id.button_start_welcome)

        // When clicking the button, move to the Main page (Quiz page).
        startButton.setOnClickListener {
//            viewModel.currentUsername.value = viewModel.username

            // Move to the Main page (Quiz page).
            val i = Intent(this, QuizActivity::class.java)

            // Get the text input as a username.
            val inputUsername = userNameEditText.getText()

            // Pass the username to the next screen.
            // https://medium.com/@AryanBeast/pass-send-data-custom-models-from-one-activity-to-another-activity-84ffccb25803
            i.putExtra("USERNAME_STRING", inputUsername.toString())

            // As the questions are generated randomly, a seed is used to perform UI tests
            // Pass a seed as the current time for production
            // https://stackoverflow.com/questions/5799179/java-random-seed
//            i.putExtra("RANDOM_SEED", 123456789)
            i.putExtra("RANDOM_SEED", System.currentTimeMillis())

            // Start the next screen.
            startActivity(i)
        }

    }
}