package com.av.knowYourWeather.Viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel

class LoginViewModel: ViewModel() {

    private val TAG ="LOGIN_TAG";
    var auth = Firebase.auth;

    val loginStatus : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun loginUserWithEmailAndPassword(email: String, password: String)
    {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful)
                {
                    loginStatus.postValue(true)
                    Log.d(TAG,"login user with email: success")
                }
                else
                {
                    Log.d(TAG,"Login user with email: failure", task.exception);
                }
            }
    }
}