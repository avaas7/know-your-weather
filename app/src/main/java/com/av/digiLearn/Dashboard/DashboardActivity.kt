package com.av.digiLearn.Dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.av.digiLearn.Login.LoginActivity
import com.av.digiLearn.Viewmodel.WeatherViewModel
import com.av.digiLearn.model.CurrentWeatherResponse
import com.av.digiLearn.model.ForecastWeatherResponse
import com.av.digiLearn.model.ForecastdayItem
import com.av.digiLearn.ui.theme.DigiLearnTheme
import kotlinx.coroutines.flow.collectLatest

//
//weather api key: 8186ffa9755f41e89da182836240103

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)

class DashboardActivity : ComponentActivity() {

    private val weatherViewModel by lazy { WeatherViewModel() }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val homeTab = TabBarItem(
                title = "Current",
                selectedIcon = Icons.Filled.Place,
                unselectedIcon = Icons.Outlined.Place
            )
            val moreTab = TabBarItem(
                title = "Forecast",
                selectedIcon = Icons.Filled.DateRange,
                unselectedIcon = Icons.Outlined.DateRange
            )

            val tabBarItems = listOf(homeTab,moreTab)

            val navController = rememberNavController()

            DigiLearnTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                )
                {
                    Scaffold(bottomBar = { TabView(tabBarItems, navController) }) {
                        NavHost(navController = navController, startDestination = homeTab.title) {
                            composable(homeTab.title) {
                                HomeTab(homeTab)
                            }
                            composable(moreTab.title) {
                                MoreView()
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun HomeTab(homeTab: TabBarItem) {
        var weatherDataState by remember { mutableStateOf<CurrentWeatherResponse?>(null) }
        var country by remember { mutableStateOf("Kathmandu") }

        lifecycleScope.launchWhenStarted {
            weatherViewModel.currentWeatherDataStateFlow.collectLatest {
                it?.let { it ->
                    if (it != null) {
                        weatherDataState = it
                    }
                }
            }
        }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                TextField(
                    value = country,
                    onValueChange = { newText -> country = newText },
                    placeholder = { Text(text = "Enter your city name.") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 16.dp),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontFamily = FontFamily.SansSerif
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search, // Replace with your icon resource
                            contentDescription = null, // Provide a content description
                            tint = Color.Gray // Set your desired tint color
                        )
                    },
                )
                // BasicTextField(value = , onValueChange = )
                Button(
                    onClick = {
                        weatherViewModel.getCurrentWeatherData(country)
                              weatherViewModel.getForecastWeatherData(country)
                              },
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                ) {
                    Text(text = "Search")
                }
                if (weatherDataState != null) {
                    WeatherLayout(weatherDataState)
                }
                //       Text(text = )
            }
        }
    }

    @Composable
    fun WeatherLayout(weatherDataState: CurrentWeatherResponse?) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 32.dp, 0.dp, 0.dp)
        ) {
            Text(
                text = weatherDataState?.location?.name.toString() + ", " + weatherDataState?.location?.country,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 32.sp
            )
            Text(
                text = weatherDataState?.location?.lat.toString() + ", " + weatherDataState?.location?.lon,
                style = TextStyle(fontWeight = FontWeight.Light)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 32.dp, 0.dp, 0.dp),
            ) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                    Text(text = "Now", style = TextStyle(fontWeight = FontWeight.Bold))
                    Text(
                        text = weatherDataState?.current?.tempF.toString() + "°",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        fontSize = 64.sp
                    )
                    Text(text = "Humidity: " + weatherDataState?.current?.humidity + ", " + "Wind: " + weatherDataState?.current?.windMph)
                }
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    Text(
                        text = weatherDataState?.current?.condition?.text.toString(),
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        fontSize = 16.sp
                    )
                    ImageFromCDN(url = weatherDataState?.current?.condition?.icon?.let { "https:$it" }
                        ?: "")
                    Text(text = "Feels like " + weatherDataState?.current?.feelslikeF)
                }
            }

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 32.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(text = "UV", style = TextStyle(fontWeight = FontWeight.Bold))
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = weatherDataState?.current?.uv.toString()
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp, 0.dp, 0.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Pressure in mb",
                                style = TextStyle(fontWeight = FontWeight.Bold),
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = weatherDataState?.current?.pressureMb.toString()
                            )
                        }

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp, 0.dp, 0.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Precipitation in mm",
                                style = TextStyle(fontWeight = FontWeight.Bold),
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = weatherDataState?.current?.precipMm.toString()
                            )
                        }

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp, 0.dp, 0.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(text = "Cloud", style = TextStyle(fontWeight = FontWeight.Bold))
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = weatherDataState?.current?.cloud.toString()
                            )
                        }

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp, 0.dp, 0.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Velocity in km",
                                style = TextStyle(fontWeight = FontWeight.Bold),
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = weatherDataState?.current?.visKm.toString()
                            )
                        }

                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 8.dp, 0.dp, 0.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Gust in mph",
                                style = TextStyle(fontWeight = FontWeight.Bold),
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = weatherDataState?.current?.gustMph.toString()
                            )
                        }

                    }
                }
            }
            LogOutBtn()
        }
    }

    @Composable
    fun ImageFromCDN(url: String) {
        if (url.isNotEmpty()) {
            val painter = rememberImagePainter(
                data = url,
                builder = {
                    transformations(CircleCropTransformation())
                }
            )

            Image(
                painter = painter,
                contentDescription = null, // Provide a meaningful description if required
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
        } else {
            Text(text = "Image not available")
        }
    }

    @Composable
    fun TabView(tabBarItems: List<TabBarItem>, navController: NavController) {
        var selectedTabIndex by rememberSaveable {
            mutableStateOf(0)
        }

        NavigationBar {
            tabBarItems.forEachIndexed { index, tabBarItem ->
                NavigationBarItem(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        navController.navigate(tabBarItem.title)
                    },
                    icon = {
                        TabBarIconView(
                            isSelected = selectedTabIndex == index,
                            selectedIcon = tabBarItem.selectedIcon,
                            unselectedIcon = tabBarItem.unselectedIcon,
                            title = tabBarItem.title,
                            badgeAmount = tabBarItem.badgeAmount
                        )
                    },
                    label = { Text(tabBarItem.title) })
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TabBarIconView(
        isSelected: Boolean,
        selectedIcon: ImageVector,
        unselectedIcon: ImageVector,
        title: String,
        badgeAmount: Int? = null
    ) {
        BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
            Icon(
                imageVector = if (isSelected) selectedIcon else unselectedIcon,
                contentDescription = title
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TabBarBadgeView(count: Int? = null) {
        if (count != null) {
            Badge {
                Text(count.toString())
            }
        }
    }

    @Composable
    private fun MoreView() {
        var forecastWeatherDataState by remember { mutableStateOf<ForecastWeatherResponse?>(null) }
        lifecycleScope.launchWhenStarted {
            weatherViewModel.forecastWeatherDataStateFlow.collectLatest {
                it?.let { it ->
                    if (it != null) {
                        forecastWeatherDataState = it
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 32.dp, 16.dp, 64.dp)
        ) {
             if (forecastWeatherDataState != null) {
                var forecastDayList: List<ForecastdayItem?> =
                    forecastWeatherDataState?.forecast?.forecastday ?: emptyList()
                Text(
                    text = forecastWeatherDataState?.location?.name.toString() + ", " + forecastWeatherDataState?.location?.country,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    fontSize = 32.sp
                )
                Text(
                    text = forecastWeatherDataState?.location?.lat.toString() + ", " + forecastWeatherDataState?.location?.lon,
                    style = TextStyle(fontWeight = FontWeight.Light)
                )

                    LazyColumn(modifier = Modifier.padding(0.dp,32.dp)) {
            /*            item{
                            LogOutBtn()
                        }*/
                        items(forecastDayList) { forecastDay ->
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 8.dp)
                            ) {
                                Column(Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(0.dp, 8.dp, 0.dp, 0.dp),
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f),
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Text(
                                                text = forecastDay?.date.toString(),
                                                style = TextStyle(fontWeight = FontWeight.Bold)
                                            )
                                            Text(
                                                text = forecastDay?.day?.avgtempF.toString() + "°",
                                                style = TextStyle(fontWeight = FontWeight.Bold),
                                                fontSize = 64.sp
                                            )
                                            Text(text = "Humidity: " + forecastDay?.day?.avghumidity + ", " + "Wind: " + forecastDay?.day?.maxwindMph)
                                        }
                                        Column(
                                            modifier = Modifier.weight(1f),
                                            horizontalAlignment = Alignment.End
                                        ) {
                                            Text(
                                                text = forecastDay?.day?.condition?.text.toString(),
                                                style = TextStyle(fontWeight = FontWeight.Bold),
                                                fontSize = 16.sp
                                            )
                                            ImageFromCDN(url = forecastDay?.day?.condition?.icon?.let { "https:$it" }
                                                ?: "")
                                            Text(text = "Feels like " + forecastDay?.day?.maxtempF)
                                        }
                                    }
                                }
                            }
                        }
                }

            }
        }
    }

    @Composable
    private fun LogOutBtn() {
        Button(
            onClick = {
                weatherViewModel.userLogOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Log out")
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        DigiLearnTheme {
            val homeTab = TabBarItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home
            )
            HomeTab(homeTab)
        }
    }
}
