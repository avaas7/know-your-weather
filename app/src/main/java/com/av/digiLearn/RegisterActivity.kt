package com.av.digiLearn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.av.digiLearn.Login.LoginActivity
import com.av.digiLearn.Viewmodel.RegisterViewModel
import com.av.digiLearn.ui.theme.DigiLearnTheme


class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContent {
           DigiLearnTheme {
               Surface(modifier = Modifier.fillMaxSize()
                   , color = Color.White
               ) {
                   RegisterActivityLayout()
               }
           }
       }

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java);

  //      userRegister("avashpalikhe@gmail.com","123456");
    }

    @Composable
    fun RegisterActivityLayout() {
        Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(text = "Welcome to DigiLearn", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            UsernameTextField()
            PasswordTextField()
            LoginButton()
        }
    }

    @Composable
    fun LoginButton(){
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp).padding(16.dp,0.dp), onClick = { /*TODO*/ }) {
            Text(text = "Login")
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UsernameTextField() {
        var username by remember { mutableStateOf("") }

        TextField(
            value = username,
            onValueChange = { newText -> username = newText },
            placeholder = { Text(text = "Enter User Name") },
            singleLine = true,
            modifier = Modifier
                .background(color = Color.White)
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
        var password by remember { mutableStateOf("") }

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

    @Preview
    @Composable
    fun preview()
    {
        DigiLearnTheme {
            Surface(modifier = Modifier.fillMaxSize()
                , color = Color.White
            ) {
                RegisterActivityLayout()
            }
        }
    }

    override fun onStart() {
        super.onStart()
      /*  val currentUser = viewModel.auth.currentUser
        if (currentUser!=null)
        {
            reload();
        }*/
    }

    private fun reload() {
        startActivity(Intent(this, LoginActivity::class.java));
    }

    private fun userRegister(email: String, password: String)
    {
        viewModel.createUserWithEmailAndPassword(email,password)
    }
}