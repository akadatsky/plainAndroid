package com.company.plainandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    val bgScope = CoroutineScope(Dispatchers.IO)
    val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            startActivity<SecondActivity>()
        }

        buttonToast.setOnClickListener {
            toast("TEST")
        }

        buttonAlert.setOnClickListener {
            alert(Appcompat, "Hi, I'm Roy", "Have you tried turning it off and on again?") {
                yesButton { toast("Oh…") }
            }.show()
        }

        buttonSelector.setOnClickListener {
            val countries = listOf("USA", "Japan", "Australia")
            selector("Where are you from?", countries) { _, i ->
                toast("So you're living in ${countries[i]}, right?")
            }
        }

        buttonLogs.setOnClickListener {
            Timber.e("asdfasdf: Test logs")
        }


        val apiService = Retrofit.Builder()
            .baseUrl("https://api.privatbank.ua")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        buttonNet.setOnClickListener {
            bgScope.launch {
                try {
                    val response = apiService.pbRequest(true, "07.01.2019").execute().body()
                    Timber.i("asdafsd: $response")
                    response?.exchangeRate?.forEach {
                        if (it.currency == "USD") {
                            uiScope.launch {
                                toast(it.saleRate.toString())
                            }
                            return@forEach
                        }
                    }
                } catch (e: Exception) {
                    Timber.i("asdafsd: $e")
                }
            }
        }

    }

}
