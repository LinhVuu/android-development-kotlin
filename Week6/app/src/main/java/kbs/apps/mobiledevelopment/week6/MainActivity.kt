package kbs.apps.mobiledevelopment.week6

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var counter = 0
        val incrementButton = findViewById<Button>(R.id.buttonIncrement1)
        val decrementButton = findViewById<Button>(R.id.buttonDecrement1)
        val numberDisplay = findViewById<TextView>(R.id.numberDisplay)

        incrementButton.setOnClickListener {
            counter += 1
            numberDisplay.text = counter.toString()
        }

        decrementButton.setOnClickListener {
            counter -= 1
            numberDisplay.text = counter.toString()
        }
    }

}