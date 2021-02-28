package com.edgetechs.parkeze

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.textfield.TextInputLayout
import com.google.android.gms.location.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_splash.*

class master : AppCompatActivity() {

    lateinit var login :Button
    lateinit var  signup :Button
    lateinit var  phone :TextInputLayout
    lateinit var password :TextInputLayout
    lateinit var forgot :TextView
    lateinit var loader:ProgressBar

    lateinit var finalphone :String
    lateinit var finalpass:String

    lateinit var str1: String
    lateinit var str2: String
    lateinit var str3: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.edgetechs.parkeze.R.layout.activity_master)



        login=findViewById(com.edgetechs.parkeze.R.id.btnLogin)
        signup=findViewById(com.edgetechs.parkeze.R.id.btnsgn)
        phone=findViewById(com.edgetechs.parkeze.R.id.phones)
        password=findViewById(com.edgetechs.parkeze.R.id.pass)
        forgot=findViewById(com.edgetechs.parkeze.R.id.forgot)
        loader=findViewById(com.edgetechs.parkeze.R.id.progressBar)

        val ntent=Intent(this,MainActivity::class.java)
        loader.visibility=View.INVISIBLE
       login.setOnClickListener {
        finalphone = phone.editText!!.text.toString()
        finalpass = password.editText!!.text.toString()
           loader.visibility = View.VISIBLE


        val checkuser =
            FirebaseDatabase.getInstance().getReference("Driver").orderByChild("phone")
                .equalTo(finalphone)
        checkuser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@master,"$error",Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    phone.error = null
                    phone.isErrorEnabled = false

                    val pa = snapshot.child(finalphone).child("pass").getValue().toString()
                    if (pa.equals(finalpass)) {
                        password.error = null
                        password.isErrorEnabled = false


                        str1 = snapshot.child(finalphone).child("fullname").getValue().toString()
                        str2 = snapshot.child(finalphone).child("phone").getValue().toString()
                        str3 =
                            snapshot.child(finalphone).child("pass").getValue().toString()


                        val sharedPref = getSharedPreferences("theKey", Context.MODE_PRIVATE)
                        val editor = sharedPref.edit()
                        editor.putString("fullname", str1)
                        editor.putString("phone", str2)
                        editor.putString("pass", str3)
                        editor.apply()


                        startActivity(ntent)

                    } else {
                        loader.visibility = View.INVISIBLE
                        Toast.makeText(this@master, "Password Invalid", Toast.LENGTH_SHORT).show()

                    }
                }
                    else
                    {
                        loader.visibility = View.INVISIBLE
                        Toast.makeText(this@master,"No such user found",Toast.LENGTH_SHORT).show()
                    }
                }


            })}

        signup.setOnClickListener {
            val intent=Intent(this,sign::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        finishAffinity()
    }
}