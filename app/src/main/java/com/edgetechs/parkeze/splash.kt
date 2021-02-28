package com.edgetechs.parkeze

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.airbnb.lottie.ImageAssetDelegate
import com.airbnb.lottie.LottieAnimationView

class splash : AppCompatActivity() {
    lateinit var loading: LottieAnimationView
    lateinit var imglogo :ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        imglogo = findViewById(R.id.imageView)
        loading = findViewById(R.id.load)


        imglogo.animate().translationX(1600f).setDuration(1000).setStartDelay(3500)
        loading.animate().translationX(1600f).setDuration(1000).setStartDelay(3500)


        Handler(Looper.getMainLooper()).postDelayed({
            val intent =
                Intent(this@splash, MainActivity::class.java)
            startActivity(intent)
        }, 4000)

    }
}