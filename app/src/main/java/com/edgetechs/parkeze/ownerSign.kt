package com.edgetechs.parkeze

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputLayout

class ownerSign : AppCompatActivity() {
    lateinit var ownername :TextInputLayout
    lateinit var email:TextInputLayout
    lateinit var ownerphone:TextInputLayout
    lateinit var pass:TextInputLayout
    lateinit var landDesc:TextInputLayout
    lateinit var type:RadioGroup
    lateinit var btn:RadioButton
    lateinit var loader:ProgressBar
    lateinit var vehicle:String


    lateinit var str1: String
    lateinit var str2: String
    lateinit var str3:String
    lateinit var str4: String
    lateinit var str5: String
    lateinit var next :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_sign)

        ownername = findViewById(R.id.fullname)
        email = findViewById(R.id.email)
        ownerphone = findViewById(R.id.mobile)
        pass = findViewById(R.id.password)
        landDesc = findViewById(R.id.Parkingdesc)

        next = findViewById(R.id.nexti)
        loader = findViewById(R.id.loader)
        loader.visibility = View.INVISIBLE

        next.setOnClickListener {

            str1 = ownername.editText!!.text.toString()
            str2 = email.editText!!.text.toString()
            str3 = ownerphone.editText!!.text.toString()
            str4 = pass.editText!!.text.toString()
            str5 = landDesc.editText!!.text.toString()


            if (!validatename() || !validateemail() || !validatenum()) {
                return@setOnClickListener
            } else {
                val sharedPrefi = getSharedPreferences("Key", Context.MODE_PRIVATE)
                val editor = sharedPrefi.edit()
                editor.putString("name", str1)
                editor.putString("email", str2)
                editor.putString("phone", str3)

                editor.putString("pass", str4)
                editor.putString("Description", str5)
                editor.apply()


                val ntent = Intent(this@ownerSign, mapslogin::class.java)
                startActivity(ntent)

            }
        }
    }

   fun validatename(): Boolean {
        val tets: String = ownername.editText!!.text.toString()
        if (tets.isEmpty()) {
            ownername.error = "Enter Name"
            return false
        } else
            return true
    }

    fun validateemail(): Boolean {
        val tets: String = email.editText!!.text.toString()
        if (tets.isEmpty()) {
            email.error = "Enter mail"
            return false
        } else
            return true
    }

    fun validatenum(): Boolean {
        val test = ownerphone.editText!!.text.toString().trim()
        if (test.isEmpty()) {
            ownerphone.error = "Field cant be empty"
            return false
        } else if (test.length !== 10) {
           ownerphone.error = "Enter a valid Number"
            return false
        } else {
            ownerphone.error = null
            ownerphone.isErrorEnabled = false
            return true
        }
    }
}
