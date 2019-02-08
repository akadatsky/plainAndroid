package com.company.plainandroid

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.*

class SecondActivity : AppCompatActivity() {

    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        ioScope.launch {
            delay(3000L)
            uiScope.launch {
                textView.text = "test2"
            }
        }
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }

}
