package com.edgetechs.parkeze

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class ownerconfirm : AppCompatActivity() {
    lateinit var submit : Button
    lateinit var loader: ProgressBar
    lateinit var str1: String
    lateinit var str2: String
    lateinit var str3: String
    lateinit var str4: String
    lateinit var str5: String
    lateinit var latitude: String
    lateinit var longitude: String
    lateinit var addressS:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_confirm)

        submit=findViewById(R.id.sub)
        loader=findViewById(R.id.loader)
        val sharedPrefi = getSharedPreferences("Key", Context.MODE_PRIVATE)
        str1 =sharedPrefi.getString("name","0").toString()
        str2 =sharedPrefi.getString("email","0").toString()
        str3 =sharedPrefi.getString("phone","0").toString()
        str4=sharedPrefi.getString("pass","0").toString()
        str5=sharedPrefi.getString("Description","0").toString()

        val lat=intent.getDoubleExtra("latitude",0.0)
        val lng =intent.getDoubleExtra("longitude",0.0)
        loader.visibility= View.INVISIBLE
        submit.setOnClickListener {
            loader.visibility= View.VISIBLE
            latitude=lat.toString()
            longitude=lng.toString()

            val addresses: List<Address>
            val geocoder: Geocoder = Geocoder(this, Locale.getDefault())

            addresses = geocoder.getFromLocation(
                lat,
                lng,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5


            val address: String = addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            val city: String = addresses[0].getLocality()
            val state: String = addresses[0].getAdminArea()
            val country: String = addresses[0].getCountryName()
            val postalCode: String = addresses[0].getPostalCode()
            val knownName: String = addresses[0].getFeatureName()
            addressS = address + "," + city + "," + state + country + postalCode + knownName

            new()
            val intent = Intent(
                this,
                owner::class.java
            )
            startActivity(intent)
        }

    }
    fun new(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Owner")
        val addnew: DataHelper1 =
            DataHelper1(str1, str2, str3, str4, str5,latitude,longitude,addressS)
        myRef.child(str3).setValue(addnew)
    }
}
