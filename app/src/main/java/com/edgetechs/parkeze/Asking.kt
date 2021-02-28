package com.edgetechs.parkeze

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView

class Asking : AppCompatActivity() {
    lateinit var driver :LottieAnimationView
    lateinit var  owne:LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asking)

        driver=findViewById(R.id.drive)
        owne=findViewById(R.id.owner)

        driver.setOnClickListener{
            val intent= Intent(this,master::class.java)
            startActivity(intent)
        }
        owne.setOnClickListener{
            val intent= Intent(this,owner::class.java)
            startActivity(intent)
        }
    }
}