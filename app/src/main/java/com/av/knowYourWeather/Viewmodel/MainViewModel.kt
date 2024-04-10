package com.av.knowYourWeather.Viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainViewModel: ViewModel() {
    private val TAG ="MAIN_VIEW_MODEL_TAG";
    var auth = Firebase.auth;

    fun userLogOut()
    {
        auth.signOut()
    }
}