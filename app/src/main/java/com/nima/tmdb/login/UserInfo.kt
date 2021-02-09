package com.nima.tmdb.login

import android.content.Context
import android.util.Log
import com.nima.tmdb.R
import com.nima.tmdb.login.state.TAG
import com.nima.tmdb.models.login.LoginInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserInfo(private var context: Context) {
    private var userName  :String = ""
    private var password  :String = ""


    fun saveUserInfo(_userName : String , _password : String) {
         Log.d(TAG, "saveUserInfo: $_userName ------- $_password")
         CoroutineScope(Dispatchers.IO).launch {
             val sharedPref = context.getSharedPreferences(R.string.usr_pass_file.toString(),Context.MODE_PRIVATE)
             with (sharedPref.edit()) {
                 putString(R.string.username.toString(),_userName)
                 putString(R.string.password.toString(),_password)
                 apply()
             }
         }
     }

    fun getUserInfo(requestToken: String)  : LoginInfo{
         val sharedPref = context.getSharedPreferences(R.string.usr_pass_file.toString(),Context.MODE_PRIVATE)
         userName = sharedPref.getString(R.string.username.toString(),"").toString()
         password = sharedPref.getString(R.string.password.toString(),"").toString()
        Log.d(TAG, "getUserInfo: $userName +++ $password ---")
        return LoginInfo(
            username = userName,
            password = password,
            request_token = requestToken
        )
    }
    fun isUsernameEmptyOrNot() : Boolean{
        val sharedPref = context.getSharedPreferences(R.string.usr_pass_file.toString(),Context.MODE_PRIVATE)
        userName = sharedPref.getString(R.string.username.toString(),"").toString()
        return userName.isEmpty()
    }
}