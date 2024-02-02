package com.example.jpmc_weather.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jpmc_weather.viewmodel.WeatherViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SearchScreen(
    weatherViewModel: WeatherViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Header
        Text(
            text = "Weather Search",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        val citySearch = remember { mutableStateOf(TextFieldValue(weatherViewModel.selectedCity ?: "")) }

        // City Search TextField
        OutlinedTextField(
            value = citySearch.value,
            onValueChange = { txt ->
                citySearch.value = txt
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (citySearch.value.text.isNotEmpty()) {
                        weatherViewModel.selectedCity = citySearch.value.text
                        weatherViewModel.getWeather()
                        navController.navigate("weather")
                    }
                }) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = null
                    )
                }
            },
            label = { Text(text = "Enter City Name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Text
            ),
            textStyle = TextStyle(
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Search Button
        Button(
            onClick = {
                if (citySearch.value.text.isNotEmpty()) {
                    weatherViewModel.selectedCity = citySearch.value.text
                    weatherViewModel.getWeather()
                    navController.navigate("weather")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Search")
        }
    }
}
