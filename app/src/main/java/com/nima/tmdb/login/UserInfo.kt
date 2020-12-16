package com.nima.tmdb.login

import android.app.Activity
import android.content.Context
import com.nima.tmdb.R
import com.nima.tmdb.models.login.LoginInfo

class UserInfo(_activity : Activity) {
    private var userName  :String = ""
    private var password  :String = ""
    private var activity : Activity = _activity

    fun saveUserInfo(userName : String , password : String){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(R.string.username.toString(),userName)
            putString(R.string.password.toString(),password)
            apply()
        }
    }
    fun getUserInfo(requestToken: String)  :LoginInfo{
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
         userName = sharedPref.getString(R.string.username.toString(),"").toString()
         password = sharedPref.getString(R.string.password.toString(),"").toString()
        return LoginInfo(
            username = userName,
            password = password,
            request_token = requestToken
        )
    }
}