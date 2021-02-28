package com.edgetechs.parkeze

import android.content.Intent
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.parcel.Parcelize

class Nearby : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    companion object {
        val UserKey = "User Key";
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby)
        recyclerView = findViewById(R.id.recycler)

        fetchuser()
    }

    private fun fetchuser() {
        val ref = FirebaseDatabase.getInstance().getReference("Owner")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()

                snapshot.children.forEach {
                    val user = it.getValue(User::class.java)
                    adapter.add(UserItem(user!!))
                }
                adapter.setOnItemClickListener { item, view ->

                    val user = item as UserItem

                    val intent = Intent(view.context, Details::class.java)
                    intent.putExtra(UserKey, user.user)
                    startActivity(intent)

                    finish()
                }
                recyclerView.adapter = adapter
            }

        })
    }
}



@Parcelize
class User (val address: String , val description:String) : Parcelable {
    constructor():this("",""); }
