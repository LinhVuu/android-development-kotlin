package kbs.apps.mobiledevelopment.week7

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        val actionButton = findViewById<Button>(R.id.button)
        val name = findViewById<EditText>(R.id.editName)
        val age = findViewById<EditText>(R.id.editAge)


//        String text_name = (String) name;
        actionButton.setOnClickListener {
//            val input_name = name.getText()
            val i = Intent(this, SecondActivity::class.java)
            val input_name = name.getText()
            val input_age = age.getText()
            i.putExtra("KEY_PERSON", Person(input_name.toString(), input_age.toString().toIntOrNull()))
//            i.putExtra("KEY_MAIN_STRING", input_name.toString())
//            i.putExtra("KEY_MAIN_STRING", "aaa")
//            i.putExtra("KEY_MAIN_INT", input_age.toString().toIntOrNull())
            startActivity(i)
        }

    }
}