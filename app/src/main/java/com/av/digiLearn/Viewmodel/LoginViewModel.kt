package com.av.digiLearn.Viewmodel

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.av.digiLearn.Login.LoginActivity
import com.av.digiLearn.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

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