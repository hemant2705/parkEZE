package com.edgetechs.parkeze

import android.location.Address

class DataHelper1 {

        lateinit var fullname :String
        lateinit var email:String

        lateinit var phone:String
        lateinit var pass:String
        lateinit var desc:String
        lateinit var latitude:String
        lateinit var longitude:String
        lateinit var address :String
        constructor(){}
        constructor(
            fullname: String,
            emailAdress: String,
            phone: String,
            pass:String,
            desc:String,
            latitude:String,
            longitude:String,
            address: String

        )
        {
            this.fullname = fullname
            this.email=emailAdress
            this.phone = phone
            this.pass = pass
            this.desc=desc
            this.latitude=latitude
            this.longitude=longitude
            this.address=address
        }
}