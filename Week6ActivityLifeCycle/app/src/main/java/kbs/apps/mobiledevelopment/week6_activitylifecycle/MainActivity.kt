package kbs.apps.mobiledevelopment.week6_activitylifecycle

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        println("On Create")
    }

    override fun onStart() {
        super.onStart()
        println("On Start")
    }

    override fun onResume(){
        super.onResume()
        println("On Resume")
    }

    override fun onPause() {
        super.onPause()
        println("On Pause")
    }

    override fun onStop() {
        super.onStop()
        println("On Stop")
    }

    override fun onRestart() {
        super.onRestart()
        println("On Restart")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("On Destroy")
    }



    // always store data in onPause()
    // press Ctrl O to open Override


}