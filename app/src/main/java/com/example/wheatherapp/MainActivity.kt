package com.example.wheatherapp

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lat=intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
        Toast.makeText(this,lat+""+long,Toast.LENGTH_LONG).show()
        window.statusBarColor = Color.parseColor("#DFD3E3")
     getJsonData(lat,long)

    }

    private fun getJsonData(lat:String?,long:String?){
        val API_KEY="c40e11ca07345b4114abe8d0a13ee011"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_KEY}"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener{ response ->
                setValues(response)

//                Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()

            },
            Response.ErrorListener { Toast.makeText(this,"Error",Toast.LENGTH_LONG).show() })

        queue.add(jsonRequest)
    }

    @SuppressLint("SimpleDateFormat", "ResourceType")
    private fun setValues(response:JSONObject){
        val city_name = findViewById<TextView>(R.id.city_name)
        val date_ = findViewById<TextView>(R.id.date)
        val hrs = findViewById<TextView>(R.id.hrs)
        val mins = findViewById<TextView>(R.id.mins)
        val weathertype = findViewById<TextView>(R.id.weather_type)
        val country_name = findViewById<TextView>(R.id.country_name)
       val  max_temp = findViewById<TextView>(R.id.max_temp)
        val min_temp = findViewById<TextView>(R.id.min_temp)
        val temp = findViewById<TextView>(R.id.temp)
        val weather_type = findViewById<TextView>(R.id.weather_type)
//      val description = findViewById<TextView>(R.id.description)
         val feels_like = findViewById<TextView>(R.id.feels_like)
        val pressure = findViewById<TextView>(R.id.pressure)
        val humidity = findViewById<TextView>(R.id.humidity)
        val windspeed = findViewById<TextView>(R.id.windSpeed)
        val sunrise_ = findViewById<TextView>(R.id.sunrise)
        val sunset_= findViewById<TextView>(R.id.sunset)
        val deg = findViewById<TextView>(R.id.deg)

        city_name.text = response.getString("name")
        weathertype.text =response.getJSONArray("weather").getJSONObject(0).getString("main")
        weather_type.text = response.getJSONArray("weather").getJSONObject(0).getString("main")
        var tempr = response.getJSONObject("main").getString("temp")
        tempr = ((((tempr).toFloat() - 273.15)).toInt()).toString()
        temp.text = "${tempr}"+"\u00B0C"
        var mintemp = response.getJSONObject("main").getString("temp_min")
        mintemp = ((((mintemp).toFloat() - 273.15)).toInt()).toString()
        min_temp.text = "${mintemp}"+"\u00B0C"
        var maxtemp = response.getJSONObject("main").getString("temp_max")
        maxtemp = ((ceil((maxtemp).toFloat() - 273.15)).toInt()).toString()
        max_temp.text = "${maxtemp}"+"\u00B0C"

        val dt = response.getLong("dt")
         date_.text = SimpleDateFormat( "EEE, MMM d, ''yy").format(Date(dt * 1000))
         hrs.text = SimpleDateFormat("hh").format(Date(dt * 1000))
        mins.text = SimpleDateFormat("mm").format(Date(dt * 1000))

       var pressures= response.getJSONObject("main").getString("pressure")
        pressure.text = "${pressures}"+"hPa"
        var humiditys= response.getJSONObject("main").getString("humidity")
        humidity.text = "${humiditys}"+"%"
        var windspeeds= response.getJSONObject("wind").getString("speed")
        windspeed.text = "${windspeeds}"+"m/s"
       var degs =response.getJSONObject("wind").getString("deg")
        deg.text="${degs}"+"°"

        var sunrise_time = response.getJSONObject("sys").getLong("sunrise")
        val sunriseDate = Date(sunrise_time * 1000)
        val dateFormat = SimpleDateFormat("hh:mm a")
        sunrise_.text = dateFormat.format(sunriseDate)

        var sunset_time = response.getJSONObject("sys").getLong("sunset")
        val sunsetDate = Date(sunset_time* 1000)
        sunset_.text = dateFormat.format(sunsetDate)



        country_name.text = response.getJSONObject("sys").getString("country")
//        description.text = response.getJSONArray("weather").getJSONObject(0).getString("description")
        var feellike = response.getJSONObject("main").getString("feels_like")
        feellike= ((((feellike).toFloat() - 273.15)).toInt()).toString()
        feels_like.text = "${feellike}"+"\u00B0C"

        val weatherbg = findViewById<ImageView>(R.id.weather_bg)
        val weathericon = findViewById<ImageView>(R.id.weather_icon)
        val weatherlayout = findViewById<LinearLayout>(R.id.ll_main)
        val weatherlayout2 = findViewById<LinearLayout>(R.id.ll_main2)
//        val top = findViewById<RelativeLayout>(R.id.rl_toolbar)

        if (weathertype.text == "Thunderstrom") {
            weatherbg.setImageResource(R.drawable.thunderstrom)
            weathericon.setImageResource(R.drawable.stormy)
            weatherlayout.setBackgroundResource(R.drawable.thunderstrom)
            weatherlayout2.setBackgroundResource(R.drawable.thunderstrom)
//            top.setBackgroundResource(R.drawable.thunderstrom)

        } else if (weathertype.text == "Rain"){
            weatherbg.setImageResource(R.drawable.rain)
            weathericon.setImageResource(R.drawable.rainy)
            weatherlayout.setBackgroundResource(R.drawable.rain)
            weatherlayout2.setBackgroundResource(R.drawable.rain)
//            top.setBackgroundResource(R.drawable.rain)

        }

        else if (weathertype.text == "Clouds"){
            weatherbg.setImageResource(R.drawable.thunderstrom)
            weathericon.setImageResource(R.drawable.cloudy)
            weatherlayout.setBackgroundResource(R.drawable.thunderstrom)
            weatherlayout2.setBackgroundResource(R.drawable.thunderstrom)
//            top.setBackgroundResource(R.drawable.thunderstrom)

        }

        else if (weathertype.text == "Drizzle"){
            weatherbg.setImageResource(R.drawable.drizzle)
            weathericon.setImageResource(R.drawable.drizzles)
            weatherlayout.setBackgroundResource(R.drawable.drizzle)
            weatherlayout2.setBackgroundResource(R.drawable.drizzle)
//            top.setBackgroundResource(R.drawable.drizzle)

        }
        else if (weathertype.text == "Snow"){
            weatherbg.setImageResource(R.drawable.thunderstrom)
            weathericon.setImageResource(R.drawable.snowy)
            weatherlayout.setBackgroundResource(R.drawable.thunderstrom)
            weatherlayout2.setBackgroundResource(R.drawable.thunderstrom)
//            top.setBackgroundResource(R.drawable.thunderstrom)

        }
        else if (weathertype.text == "Clear"){
            weatherbg.setImageResource(R.drawable.clear)
            weathericon.setImageResource(R.drawable.clera_cloud)
            weatherlayout.setBackgroundResource(R.drawable.clear)
            weatherlayout2.setBackgroundResource(R.drawable.clear)
//            top.setBackgroundResource(R.drawable.clear)

        }
        else if(weathertype.text == "Smoke"){
            weatherbg.setImageResource(R.drawable.smoke)
            weathericon.setImageResource(R.drawable.smokes)
            weatherlayout.setBackgroundResource(R.drawable.smoke)
            weatherlayout2.setBackgroundResource(R.drawable.smoke)
//            top.setBackgroundResource(R.drawable.smoke)
        }

        else if(weathertype.text == "Dust"){
            weatherbg.setImageResource(R.drawable.dust)
            weathericon.setImageResource(R.drawable.dusty)
            weatherlayout.setBackgroundResource(R.drawable.dust)
            weatherlayout2.setBackgroundResource(R.drawable.dust)
//            top.setBackgroundResource(R.drawable.smoke)
        }
        else if(weathertype.text == "Squalls"){
            weatherbg.setImageResource(R.drawable.squalls)
            weathericon.setImageResource(R.drawable.squall)
            weatherlayout.setBackgroundResource(R.drawable.squalls)
            weatherlayout2.setBackgroundResource(R.drawable.squalls)
//            top.setBackgroundResource(R.drawable.smoke)
        }
        else if(weathertype.text =="Tornado"){
            weatherbg.setImageResource(R.drawable.tornado)
            weathericon.setImageResource(R.drawable.tornados)
            weatherlayout.setBackgroundResource(R.drawable.tornado)
            weatherlayout2.setBackgroundResource(R.drawable.tornado)
//            top.setBackgroundResource(R.drawable.smoke)
        }


        else {
            weatherbg.setImageResource(R.drawable.background_main)
            weathericon.setImageResource(R.drawable.elses)
            weatherlayout.setBackgroundResource(R.drawable.background_main)
            weatherlayout2.setBackgroundResource(R.drawable.background_main)
//            top.setBackgroundResource(R.drawable.background_main)

        }



    }


}