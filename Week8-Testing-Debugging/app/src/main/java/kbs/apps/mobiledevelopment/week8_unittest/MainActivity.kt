package kbs.apps.mobiledevelopment.week8_unittest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.toString

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val clickButton = findViewById<Button>(R.id.button)
        val editText = findViewById<EditText>(R.id.editTextText)
        val displayText = findViewById<TextView>(R.id.displayText)

        // When Button is clicked.
        clickButton.setOnClickListener {
            val input_text = editText.getText().toString()
            displayText.text = validateInput(input_text)

        }

    }

    fun validateInput(editText: String): String {
        val displayText: String
        if (editText != "") {
            displayText = editText
        } else {
            displayText = "invalid input!"
        }
        return displayText
    }

}