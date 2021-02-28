package com.edgetechs.parkeze

    class DataHelper {

        lateinit var fullname :String
        lateinit var phone:String
        lateinit var pass:String
        constructor(){}
        constructor(
            fullname: String,
            username: String,
            emailAdress: String)
        {
            this.fullname = fullname
            this.phone = username
            this.pass = emailAdress}
}