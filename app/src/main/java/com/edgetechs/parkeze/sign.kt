package com.edgetechs.parkeze

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout

class sign : AppCompatActivity() {
    lateinit var phone :TextInputLayout
    lateinit var pass:TextInputLayout
    lateinit var next :Button
    lateinit var name:TextInputLayout

    lateinit var str1: String
    lateinit var str2: String
    lateinit var str3:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        phone = findViewById(R.id.phones)
        pass = findViewById(R.id.confpass)
        name=findViewById(R.id.name)
        next = findViewById(R.id.btnLogin)

        next.setOnClickListener {

            str1 = name.editText!!.text.toString()
            str2 = phone.editText!!.text.toString()
            str3 = pass.editText!!.text.toString()


            if (!validateFullname() || !validatePassword() || !validatephone()) {
                return@setOnClickListener
            } else {

                val sharedPref = getSharedPreferences("myKey", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("fullname", str1)
                editor.putString("phone", str2)
                editor.putString("pass", str3)
                editor.apply()

                val intent = Intent(this, driverotp::class.java)
                startActivity(intent)
            }
        }
    }


            fun validateFullname(): Boolean {
                val test = name.editText!!.text.toString().trim()
                if (test.isEmpty()) {
                    name.error = "Field cannot be empty"
                    return false
                }

                else {
                    name.error = null
                    name.isErrorEnabled = false
                    return true
                }
            }


            fun validatePassword(): Boolean {
                val test: String = pass.editText!!.text.toString().trim()

                if (test.isEmpty()) {
                    pass.error = "Field cannot be empty"
                    return false
                }
                else /*if (!test.contains(checkPassword)) {
            password.error = "Atleast 4 characters"
            return false
        } else*/ {
                    pass.error = null
                    pass.isErrorEnabled = false
                    return true
                }
            }

            fun validatephone(): Boolean {
                val test = phone.editText!!.text.toString().trim()
                if (test.isEmpty()) {
                    phone.error = "Field cant be empty"
                    return false
                } else if (test.length !== 10) {
                    phone.error = "Enter a valid Number"
                    return false
                } else {
                    phone.error = null
                    phone.isErrorEnabled = false
                    return true
                }
    }

}