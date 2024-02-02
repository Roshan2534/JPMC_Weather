package com.example.jpmc_weather.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jpmc_weather.R
import com.example.jpmc_weather.model.WeatherResponse
import com.example.jpmc_weather.network.WeatherAPI
import com.example.jpmc_weather.utils.Resource
import com.example.jpmc_weather.viewmodel.WeatherViewModel

@Composable
fun WeatherDetails(
    weatherViewModel: WeatherViewModel
) {
    when (val state = weatherViewModel.weather.value) {
        is Resource.ERROR -> {
            // Show an error message or handle error state
            Text(text = "Error fetching weather data")
        }
        is Resource.LOADING -> {
            // Show a loading indicator
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }
        is Resource.SUCCESS -> {
            DetailsList(state.data)
        }
    }
}

@Composable
fun DetailsList(
    weathers: WeatherResponse? = null
) {
    LazyColumn {
        itemsIndexed(items = listOf(weathers)) { _, sat ->
            sat?.let {
                DetailItem(weather = it)
            } ?: Text(text = "No information available for specific city")
        }
    }
}

@Composable
fun DetailItem(weather: WeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "City: ${weather.name}",
            style = typography.headlineLarge // Use appropriate typography
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Temperature: ${weather.main.temp.toString()}°C",
            style = typography.bodyMedium
        )
        Text(
            text = "Max Temperature: ${weather.main.tempMax.toString()}°C",
            style = typography.bodyMedium
        )
        Text(
            text = "Min Temperature: ${weather.main.tempMin.toString()}°C",
            style = typography.bodyMedium
        )
        Text(
            text = "Temperature Feels Like: ${weather.main.feelsLike.toString()}°C",
            style = typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    WeatherAPI.BASE_IMAGE +
                            weather.weather.get(0).icon +
                            "@2x.png"
                )
                .crossfade(true)
                .build(),
            contentDescription = weather.weather.get(0).description,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp).clip(CircleShape)
        )
    }
}

