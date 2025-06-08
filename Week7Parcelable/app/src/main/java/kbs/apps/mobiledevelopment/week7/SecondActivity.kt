package kbs.apps.mobiledevelopment.week7

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
        val person:Person?

        person = i?.getParcelableExtra("KEY_PERSON", Person::class.java)
//        val intentString = i.getStringExtra("KEY_MAIN_STRING")
//        val intentInt = i.getIntExtra("KEY_MAIN_INT",0)

        val input_name = person?.name
        val input_age = person?.age

        val name = findViewById<TextView>(R.id.textResultName)
        val age = findViewById<TextView>(R.id.textResultAge)
        name.text = input_name.toString()
        age.text = input_age.toString()
    }
}