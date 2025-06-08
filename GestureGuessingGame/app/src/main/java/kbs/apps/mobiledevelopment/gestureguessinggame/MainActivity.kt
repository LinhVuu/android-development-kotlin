package kbs.apps.mobiledevelopment.gestureguessinggame

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log

class MainActivity : AppCompatActivity() {

    private var currentPlayer = 1
    private val player1Actions = mutableListOf<String>()
    private val player2Actions = mutableListOf<String>()
    private val player1Log = mutableListOf<String>()
    private val player2Log = mutableListOf<String>()
    private var hasStarted = false
    private lateinit var statusText: TextView
    private lateinit var actionsSummary: TextView
    private lateinit var activitiesLog: TextView
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val actionButton = findViewById<Button>(R.id.actionButton)
        val nextPlayerButton = findViewById<Button>(R.id.nextPlayerButton)
        val checkGuessButton = findViewById<Button>(R.id.checkGuessButton)
        val resetButton = findViewById<Button>(R.id.resetButton)

        statusText = findViewById(R.id.statusText)
        resultText = findViewById(R.id.resultText)
        actionsSummary = findViewById(R.id.actionsSummary)
        activitiesLog = findViewById(R.id.activitiesLog)

        Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show()

        actionButton.setOnClickListener {
            Toast.makeText(this, "Tap Detected!", Toast.LENGTH_SHORT).show()
            recordAction("Tap button")
            Log.d("TAG", "Button's clicked")
            Log.i("TAG", "Button's clicked")

        }

        nextPlayerButton.setOnClickListener {
            currentPlayer = 2
            statusText.text = "Player 2: Perform your 2 actions!"
        }

        checkGuessButton.setOnClickListener {
            checkGuess()
        }

        resetButton.setOnClickListener {
            resetGame()
        }

    }

    override fun onResume() {
        super.onResume()
        if (hasStarted) {
            recordAction("Re-Open App")
            recordLog("Resume")
        } else {
            hasStarted = true
        }
    }

    override fun onPause() {
        super.onPause()
        recordLog("Pause")
    }

    override fun onDestroy() {
        super.onDestroy()
        recordLog("Destroy")
        Toast.makeText(this, "Game Reset!", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        recordLog("Restart")
        Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        if (hasStarted) {
            recordLog("Start")
        }
    }

    override fun onStop() {
        super.onStop()
        recordLog("Stop")
    }

    private fun recordAction(action: String) {
        if (currentPlayer == 1 && player1Actions.size < 3) {
            player1Actions.add(action)
            statusText.text = "Player 1 Action ${player1Actions.size}: $action"
        } else if (currentPlayer == 2 && player2Actions.size < 3) {
            player2Actions.add(action)
            statusText.text = "Player 2 Action ${player2Actions.size}: $action"
        }
    }

    private fun recordLog(activity: String) {
        if (currentPlayer == 1) {
            player1Log.add(activity)
        } else if (currentPlayer == 2) {
            player2Log.add(activity)
        }
    }

    private fun checkGuess() {
        val isCorrect = player1Actions == player2Actions
        (if (isCorrect) "Correct! 🎉" else "Wrong! Try again 😅").also { resultText.text = it }
        displayActionsSummary()
        displayActivitiesLog()
    }

    private fun displayActionsSummary() {
        val summary = """
        Actions Summary:
            Player 1 Actions: $player1Actions
            Player 2 Actions: $player2Actions
    """.trimIndent()

        actionsSummary.text = summary
    }

    private fun displayActivitiesLog() {
        val summary = """
        Activity LifeCycle Log:
            Player 1 Log: $player1Log
            Player 2 Log: $player2Log
    """.trimIndent()

        activitiesLog.text = summary
    }

    // Function to reset the game state
    private fun resetGame() {

        // Clear action lists
        player1Actions.clear()
        player2Actions.clear()

        // Reset current player
        currentPlayer = 1
        hasStarted = false

        // Clear status and result text views
        statusText.text = ""
        resultText.text = ""
        activitiesLog.text = ""
        actionsSummary.text = ""

    }
}