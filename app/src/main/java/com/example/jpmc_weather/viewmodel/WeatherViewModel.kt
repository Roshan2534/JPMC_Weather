package com.example.jpmc_weather.viewmodel

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jpmc_weather.model.WeatherResponse
import com.example.jpmc_weather.network.WeatherRepository
import com.example.jpmc_weather.utils.Resource
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val ioDispatcher: CoroutineDispatcher,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : ViewModel() {

    private val _weather: MutableState<Resource<WeatherResponse>> = mutableStateOf(Resource.LOADING)
    private val _isCitySelected: MutableState<Boolean?> = mutableStateOf(null)

    val weather: State<Resource<WeatherResponse>> get() = _weather


    var selectedCity: String? = null


    private val _isLocation: MutableState<Boolean?> = mutableStateOf(null)
    val isLocation: State<Boolean?> get() = _isLocation

    fun getWeather() {
        viewModelScope.launch(ioDispatcher) {
            selectedCity?.let { city ->
                weatherRepository.getWeather(city).collect {
                    _weather.value = it
                }
            } ?: let {
                _weather.value = Resource.ERROR(Exception("City not selected"))
            }
        }
    }

    private fun getCityName(location: Location) {
        viewModelScope.launch {
            selectedCity = weatherRepository.getCityName(location.latitude, location.longitude)
            _isCitySelected.value = true
            getWeather()
        }
    }

    @SuppressLint("MissingPermission") // NOTE: Shortcut for convenience of sample project.
    fun getLocation() {
        val locationTask = fusedLocationProviderClient.lastLocation

        locationTask.addOnSuccessListener {location: Location? ->
            if (location != null) {
                getCityName(location)
            }
        }
        locationTask.addOnFailureListener { _isCitySelected.value = false }
    }

    companion object {
        private const val CITY_KEY = "CITY_KEY"
    }
}