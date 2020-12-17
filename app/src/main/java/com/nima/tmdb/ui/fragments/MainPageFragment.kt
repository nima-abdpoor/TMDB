package com.nima.tmdb.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nima.tmdb.R
import com.nima.tmdb.login.UserInfo
import com.nima.tmdb.login.state.TAG
import com.nima.tmdb.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.fragment_login.view.*

class MainPageFragment : Fragment() {

    private var navController: NavController? = null
    lateinit var userInfo : UserInfo
    var username : String =""
    var password : String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userInfo = UserInfo(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.login_button.setOnClickListener{
            username = view.username.text.toString()
            password = view.password.text.toString()
            saveData(username , password)
            checkLogin()
        }
    }

    private fun saveData(username: String, password: String) {
        userInfo.saveUserInfo(username , password)
    }

    private fun checkLogin() {

    }
}