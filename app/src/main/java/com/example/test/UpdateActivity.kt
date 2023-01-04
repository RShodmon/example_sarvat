package com.example.test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.ActivityUpdateBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit


class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener { updateUser() }
    }

    private fun updateUser() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gorest.co.in")
            .build()
        val service = retrofit.create(APIService::class.java)
        val jsonObject = JSONObject()
        jsonObject.put("name", binding.name.text)
        jsonObject.put("email", binding.email.text)
        jsonObject.put("gender", "female")
        jsonObject.put("status", "active")
        val jsonObjectString = jsonObject.toString()
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.updateUser(requestBody)
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
                    binding.msg.text = prettyJson
                } else {
                    Log.e("ERROR", response.code().toString())
                }
            }
        }
    }

}