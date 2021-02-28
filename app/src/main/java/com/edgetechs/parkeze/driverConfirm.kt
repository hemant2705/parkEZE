package com.edgetechs.parkeze

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_driver_confirm.*

class driverConfirm : AppCompatActivity() {

    lateinit var submit :Button
    lateinit var loader:ProgressBar
    lateinit var str1: String
    lateinit var str2: String
    lateinit var str3: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_confirm)

        submit=findViewById(R.id.sub)
        loader=findViewById(R.id.loader)
        val sharedPrefi = getSharedPreferences("myKey", Context.MODE_PRIVATE)
        str1 =sharedPrefi.getString("fullname","0").toString()
        str2=sharedPrefi.getString("phone","0").toString()
        str3=sharedPrefi.getString("pass","0").toString()

        loader.visibility= View.INVISIBLE
        submit.setOnClickListener {
            loader.visibility= View.VISIBLE
            new()
            val intent = Intent(
                this,
                master::class.java
            )
            startActivity(intent)
        }

    }
    fun new(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Driver")
        val addnew: DataHelper =
            DataHelper(
                str1,
                str2,
                str3)
        myRef.child(str2).setValue(addnew)
    }
}