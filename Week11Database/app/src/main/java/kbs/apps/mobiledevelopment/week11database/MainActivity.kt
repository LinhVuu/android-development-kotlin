package kbs.apps.mobiledevelopment.week11database

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: TestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val tv_textView = findViewById<TextView>(R.id.textView)
        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)

        viewModel.currentNumber.observe(this, Observer {
            tv_textView.text = it.toString()
        } )

//        viewModel.currentBoolean.observe(this, Observer {
//            tv_textView.text = it.toString()
//        }

//        )
        incrementText()
    }

    private fun incrementText(){
        val btn_button = findViewById<Button>(R.id.button)
        btn_button.setOnClickListener {
            viewModel.currentNumber.value = ++viewModel.number
//            viewModel.currentBoolean.value = viewModel.number %2 == 0
        }
    }
}