package kbs.apps.mobiledevelopment.fragmentedbooks

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val titleFragment = TitleFragment()

        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragmentContainerView, WelcomeFragment())
            addToBackStack(null)
            commit()
        }

        val buttonTitle = findViewById<Button>(R.id.buttonTitle)

        buttonTitle.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, titleFragment)
                addToBackStack(null)
                commit()
            }
        }

        val buttonDetails = findViewById<Button>(R.id.buttonDetails)

        buttonDetails.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, DetailsFragment())
                addToBackStack(null)
                commit()
            }
        }

        val buttonAction = findViewById<Button>(R.id.buttonAction)

        buttonAction.setOnClickListener{
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, ActionFragment())
                addToBackStack(null)
                commit()
            }
        }

    }
}