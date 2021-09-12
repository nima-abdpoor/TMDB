package com.nima.tmdb.business.interactors.splash

import com.nima.tmdb.business.interactors.login.GetSessionId
import com.nima.tmdb.business.interactors.login.Login

class SplashInteractors(
    val getSessionId: GetSessionId,
    val login: Login,
    val token: GetToken
)