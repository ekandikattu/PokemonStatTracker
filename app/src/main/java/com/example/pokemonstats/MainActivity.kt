package com.example.pokemonstats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.material.textfield.TextInputEditText
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    var pokemonImageURL = ""
    var nameURL=  ""
    var hp = 0
    var attack = 0
    var defense = 0
    var spdefense = 0
    var spattack = 0
    var speed = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Log.d("pokemonImageURL", "Pokemon Image URL set")
        Log.d("pokemonStats", "Pokemon Stats set")
        val button = findViewById<Button>(R.id.button)
        val imageView = findViewById<ImageView>(R.id.pokemonImage)
        val inputView = findViewById<TextInputEditText>(R.id.name)
        val hpTextView = findViewById<TextView>(R.id.hpNum)
        val attackTextView = findViewById<TextView>(R.id.attackNum)
        val defenseTextView = findViewById<TextView>(R.id.defenseNum)
        val spAttackTextView = findViewById<TextView>(R.id.specialAttackNum)
        val spDefenseTextView = findViewById<TextView>(R.id.specialDefenseNum)
        val speedTextView = findViewById<TextView>(R.id.speedNum)

        getNextInfo(button, imageView, inputView, hpTextView, attackTextView, defenseTextView, spAttackTextView, spDefenseTextView, speedTextView)
    }

    private fun getNextInfo(button: Button, imageView: ImageView, textInputEditText: TextInputEditText, hpText: TextView, attackText: TextView, defenseText: TextView, spAttackText: TextView, spDefenseText: TextView, speedText: TextView)
    {
        button.setOnClickListener{
            nameURL = textInputEditText.text.toString()
            val client = AsyncHttpClient()

            client["https://pokeapi.co/api/v2/pokemon/$nameURL", object : JsonHttpResponseHandler()
            {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json:JsonHttpResponseHandler.JSON
                ) {
                    Log.d("Pokemon", "response successful$json")
                    pokemonImageURL = json.jsonObject.getJSONObject("sprites").getString("front_default")
                    hp = json.jsonObject.getJSONArray("stats").getJSONObject(0).getInt("base_stat")
                    attack = json.jsonObject.getJSONArray("stats").getJSONObject(1).getInt("base_stat")
                    defense = json.jsonObject.getJSONArray("stats").getJSONObject(2).getInt("base_stat")
                    spattack = json.jsonObject.getJSONArray("stats").getJSONObject(3).getInt("base_stat")
                    spdefense = json.jsonObject.getJSONArray("stats").getJSONObject(4).getInt("base_stat")
                    speed = json.jsonObject.getJSONArray("stats").getJSONObject(5).getInt("base_stat")
                    hpText.text = "$hp"
                    attackText.text = "$attack"
                    defenseText.text = "$defense"
                    spAttackText.text = "$spattack"
                    spDefenseText.text = "$spdefense"
                    speedText.text = "$speed"

                    Glide.with(this@MainActivity)
                        .load(pokemonImageURL)
                        .fitCenter()
                        .into(imageView)
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    throwable: Throwable?
                ) {
                    Log.d("Pokemon Error", errorResponse)
                    Toast.makeText(this@MainActivity, "Please type the pokemon name correctly (and lower case)", Toast.LENGTH_LONG).show()
                }
            }]

        }

    }
    /*
    private fun getInfo()
    {
        val client = AsyncHttpClient()

        client["https://pokeapi.co/api/v2/pokemon/$nameURL", object : JsonHttpResponseHandler()
        {
            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json:JsonHttpResponseHandler.JSON
            ) {
                Log.d("Pokemon", "response successful$json")
                pokemonImageURL = json.jsonObject.getJSONObject("sprites").getString("front_default")
                hp = json.jsonObject.getJSONArray("stats").getJSONObject(0).getInt("base_stat")
                attack = json.jsonObject.getJSONArray("stats").getJSONObject(1).getInt("base_stat")
                defense = json.jsonObject.getJSONArray("stats").getJSONObject(2).getInt("base_stat")
                spattack = json.jsonObject.getJSONArray("stats").getJSONObject(3).getInt("base_stat")
                spdefense = json.jsonObject.getJSONArray("stats").getJSONObject(4).getInt("base_stat")
                speed = json.jsonObject.getJSONArray("stats").getJSONObject(5).getInt("base_stat")

            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Pokemon Error", errorResponse)
                Toast.makeText(this@MainActivity, "Please type the pokemon name correctly (and lower case)", Toast.LENGTH_LONG).show()
            }
        }]
    }

     */
}