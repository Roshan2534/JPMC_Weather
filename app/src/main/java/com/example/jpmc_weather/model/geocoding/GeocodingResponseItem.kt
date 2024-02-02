package com.example.jpmc_weather.model.geocoding


import com.google.gson.annotations.SerializedName

data class GeocodingResponseItem(
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val long: Double,
    @SerializedName("name")
    val cityName: String,
    @SerializedName("state")
    val stateName: String,
    @SerializedName("local_names")
    val localNames: LocalNames,
)