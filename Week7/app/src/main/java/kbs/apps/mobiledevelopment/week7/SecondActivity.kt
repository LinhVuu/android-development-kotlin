package kbs.apps.mobiledevelopment.week7

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val i = intent
        val intentString = i.getStringExtra("KEY_MAIN_STRING")
        val intentInt = i.getIntExtra("KEY_MAIN_INT",0)
        val name = findViewById<TextView>(R.id.textResultName)
        val age = findViewById<TextView>(R.id.textResultAge)
        name.text = intentString.toString()
        age.text = intentInt.toString()
    }
}