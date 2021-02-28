package com.edgetechs.parkeze

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class owner : AppCompatActivity() {
    lateinit var login : Button
    lateinit var  signup : Button
    lateinit var  phone : TextInputLayout
    lateinit var password : TextInputLayout
    lateinit var forgot : TextView
    lateinit var loader:ProgressBar

    lateinit var finalphone :String
    lateinit var finalpass:String

    lateinit var str1: String
    lateinit var str2: String
    lateinit var str3: String
    lateinit var str4: String
    lateinit var str5: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner)

        login = findViewById(com.edgetechs.parkeze.R.id.btnLogin)
        signup = findViewById(com.edgetechs.parkeze.R.id.btnsgn)
        phone = findViewById(com.edgetechs.parkeze.R.id.phones)
        password = findViewById(com.edgetechs.parkeze.R.id.pass)
        forgot = findViewById(com.edgetechs.parkeze.R.id.forgot)

        loader=findViewById(com.edgetechs.parkeze.R.id.loader)

        val ntent=Intent(this,MainActivity::class.java)
        loader.visibility= View.INVISIBLE
        login.setOnClickListener {
            finalphone = phone.editText!!.text.toString()
            finalpass = password.editText!!.text.toString()
            loader.visibility = View.VISIBLE


            val checkuser =
                FirebaseDatabase.getInstance().getReference("Owner").orderByChild("phone")
                    .equalTo(finalphone)
            checkuser.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@owner,"$error", Toast.LENGTH_SHORT).show()
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
                            str2 = snapshot.child(finalphone).child("email").getValue().toString()
                            str3 = snapshot.child(finalphone).child("phone").getValue().toString()
                            str4=snapshot.child(finalphone).child("pass").getValue().toString()
                            str4=snapshot.child(finalphone).child("desc").getValue().toString()


                            val sharedPref = getSharedPreferences("theKey", Context.MODE_PRIVATE)
                            val editor = sharedPref.edit()
                            editor.putString("fullname", str1)
                            editor.putString("email", str2)
                            editor.putString("phone", str3)
                            editor.putString("pass", str4)
                            editor.putString("desc", str5)
                            editor.apply()


                            startActivity(ntent)

                        } else {
                            loader.visibility = View.INVISIBLE
                            Toast.makeText(this@owner, "Password Invalid", Toast.LENGTH_SHORT).show()

                        }
                    }
                    else
                    {
                        loader.visibility = View.INVISIBLE
                        Toast.makeText(this@owner,"No such user found", Toast.LENGTH_SHORT).show()
                    }
                }


            })}



        signup.setOnClickListener {
            val intent = Intent(this, ownerSign::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
