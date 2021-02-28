package com.edgetechs.parkeze

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.singleuserlayout.view.*
import java.util.*

class UserItem(val user: User): Item<GroupieViewHolder>(){
    lateinit var addressS :String

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.txtLoc.text=user.address
        viewHolder.itemView.txtdesc.text=user.description
    }

    override fun getLayout()=R.layout.singleuserlayout


}

