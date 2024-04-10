package com.av.digiLearn.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.av.digiLearn.Dashboard.DashboardActivity
import com.av.digiLearn.Login.LoginActivity
import com.av.digiLearn.R
import com.av.digiLearn.Viewmodel.RegisterViewModel
import com.av.digiLearn.ui.theme.DigiLearnTheme


class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterViewModel
    var email by mutableStateOf("")
    var password by mutableStateOf("")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContent {
           DigiLearnTheme {
               Surface(
                   modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background
               )
               {

               RegisterActivityLayout()
               }
           }
       }

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java);
    }

    @Composable
    fun RegisterActivityLayout() {
        Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Image(
                painter = painterResource(id = R.drawable.registration_icon),
                contentDescription = "Weather Icon",
                modifier = Modifier.height(100.dp).width(100.dp).align(Alignment.CenterHorizontally).padding(0.dp,12.dp)
            )

            TitleTextField()
            UsernameTextField()
            PasswordTextField()
            RegisterButton()
            LogInTextField()
        }

        val loginStatusObserver = Observer<Boolean> {
                status ->
            if (status)
            {
                startActivity(Intent(this, DashboardActivity::class.java))
                Toast.makeText(this,"Success",Toast.LENGTH_LONG)
                finish()
            }
            else
            {
                Toast.makeText(this,"Failed",Toast.LENGTH_LONG)
            }
        }

        viewModel.loginStatus.observe(this,loginStatusObserver);
    }

    private @Composable
    fun TitleTextField() {
        Text(text = "Registration", modifier = Modifier.fillMaxWidth().padding(0.dp,12.dp), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)    }

    @Composable
    fun LogInTextField() {
        Text(text = "Already have an Account?",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(0.dp,24.dp).clickable {
            startActivity(Intent(this, LoginActivity::class.java));
                finish()
        })
    }

    @Composable
    fun RegisterButton(){
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(16.dp, 0.dp), onClick = {
            userRegister(email, password);
        }) {
            Text(text = "Register")
        }
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