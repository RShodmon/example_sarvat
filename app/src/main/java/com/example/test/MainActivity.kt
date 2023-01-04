package com.example.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.getButton.setOnClickListener { getUsers() }
        binding.getUserButton.setOnClickListener { getShowUser() }
        binding.postButton.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateActivity::class.java)
            this@MainActivity.startActivity(intent)
        }
        binding.putButton.setOnClickListener {
            val intent = Intent(this@MainActivity, UpdateActivity::class.java)
            this@MainActivity.startActivity(intent)
        }
    }

    private fun getUsers() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gorest.co.in")
            .build()
        val service = retrofit.create(APIService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getUsers()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("Printed JSON :", prettyJson)
                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                    intent.putExtra("json_results", prettyJson)
                    this@MainActivity.startActivity(intent)
                } else {
                    Log.e("ERROR", response.code().toString())
                }
            }
        }
    }

    private fun getShowUser() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gorest.co.in")
            .build()
        val service = retrofit.create(APIService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getShowUser()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string()
                        )
                    )
                    Log.d("Printed JSON :", prettyJson)
                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                    intent.putExtra("json_results", prettyJson)
                    this@MainActivity.startActivity(intent)
                } else {
                    Log.e("ERROR", response.code().toString())
                }
            }
        }
    }
}