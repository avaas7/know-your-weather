package com.av.digiLearn.Login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.av.digiLearn.Dashboard.DashboardActivity
import com.av.digiLearn.MainActivity
import com.av.digiLearn.R
import com.av.digiLearn.Register.RegisterActivity
import com.av.digiLearn.Viewmodel.LoginViewModel
import com.av.digiLearn.Viewmodel.WeatherViewModel
import com.av.digiLearn.ui.theme.DigiLearnTheme

class LoginActivity : ComponentActivity() {
    private val viewModel by lazy { LoginViewModel() }
    var email by mutableStateOf("AvashPalikhe@gmail.com")
    var password by mutableStateOf("123456")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigiLearnTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                )
                {
                LoginActivityLayout()
                 }
            }
        }

        val loginStatusObserver = Observer<Boolean> {
            status ->
            if (status)
            {
                Toast.makeText(this,"Success",Toast.LENGTH_LONG)
                startActivity(Intent(this,DashboardActivity::class.java))
                finish()
            }
            else
            {
                Toast.makeText(this,"Failed",Toast.LENGTH_LONG)
            }
        }

        viewModel.loginStatus.observe(this,loginStatusObserver);
    }

    @Composable
    private fun LoginActivityLayout() {
        Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Image(
                painter = painterResource(id = R.drawable.weather_icon),
                contentDescription = "Weather Icon",
                modifier = Modifier.height(100.dp).width(100.dp).align(Alignment.CenterHorizontally).padding(0.dp,12.dp)
            )
            TitleTextField()
            UsernameTextField()
            PasswordTextField()
            LoginButton()
            RegisterTextField()
        }
    }

    private @Composable
    fun TitleTextField() {
        Text(text = "Know Your Weather", modifier = Modifier.fillMaxWidth().padding(0.dp,12.dp), textAlign = TextAlign.Center,fontWeight = FontWeight.Bold)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UsernameTextField() {
        TextField(
            value = email,
            onValueChange = { newText -> email = newText },
            placeholder = { Text(text = "Enter Email") },
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(56.dp),
            textStyle = TextStyle(
                color = Color.White,
                fontFamily = FontFamily.SansSerif
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle, // Replace with your icon resource
                    contentDescription = null, // Provide a content description
                    tint = Color.Gray // Set your desired tint color
                )
            },
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PasswordTextField() {
        TextField(
            value = password,
            onValueChange = { newText -> password = newText },
            placeholder = { Text(text = "Enter Password") },
            singleLine = true,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(56.dp)
                .background(color = Color.White),
            textStyle = TextStyle(
                color = Color.Black,
                fontFamily = FontFamily.SansSerif
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock, // Replace with your icon resource
                    contentDescription = null, // Provide a content description
                    tint = Color.Gray // Set your desired tint color
                )
            }
            , visualTransformation = PasswordVisualTransformation()
        )
    }

    @Composable
    fun LoginButton(){
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(16.dp, 0.dp), onClick = {
            userLogin(email, password);
        }) {
            Text(text = "Login")
        }
    }

    private fun userLogin(email: String, password: String) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Enter email and password",Toast.LENGTH_LONG).show()
            return
        }
        viewModel.loginUserWithEmailAndPassword(email,password)
    }

    @Composable
    fun RegisterTextField() {
        Text(text = "Register",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 24.dp)
                .clickable {
                    startActivity(Intent(this, RegisterActivity::class.java));
                })
    }

    override fun onStart() {
        super.onStart()
        reload()
    }

    private fun reload() {
        val currentUser = viewModel.auth.currentUser
        if (currentUser!=null)
        {
            startActivity(Intent(this,DashboardActivity::class.java))
        }
    }

    @Preview
    @Composable
    fun preview()
    {
        DigiLearnTheme {
            Surface(modifier = Modifier.fillMaxSize()
                , color = Color.White){
                LoginActivityLayout()
            }

        }
    }
}
