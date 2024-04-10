package com.av.knowYourWeather

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.av.knowYourWeather.Login.LoginActivity
import com.av.knowYourWeather.Viewmodel.MainViewModel
import com.av.knowYourWeather.ui.theme.DigiLearnTheme

class MainActivity : ComponentActivity() {
lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DigiLearnTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainActivityLayout()
                }
            }
        }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun MainActivityLayout() {
 //       val navController = rememberNavController()
        var emailTextField by remember { mutableStateOf("") }
        var passwordTextField by remember { mutableStateOf("") }
        
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
  //          Greeting("rt")
            LogOutBtn()
            TextField(value = emailTextField, onValueChange = {emailTextField=it},
                label = { Text(text = "Email")}
            )
            TextField(value = passwordTextField, onValueChange = {passwordTextField=it},
                label = { Text(text = "Password")},
                modifier = Modifier.padding(0.dp,16.dp)
            )
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Log in")
            }
        }
    }

    @Composable
    private fun LogOutBtn() {
    Button(onClick = {
        viewModel.userLogOut()
        startActivity(Intent(this,LoginActivity::class.java))
    }) {
        Text(text = "Log out")
    }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Column(modifier = Modifier.background(Color.Black), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = "Hello $name!", color = Color.Green, fontFamily = FontFamily.Cursive,
                modifier = Modifier
                    .padding(5.dp)
                    .background(Color.Cyan))
            Text(text = "Hedfdo $name!", color = Color.Green, fontFamily = FontFamily.Cursive,
                modifier = Modifier.padding(5.dp))
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        DigiLearnTheme {
            Surface (
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MainActivityLayout()
            }

        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController)
    {

    }
}


