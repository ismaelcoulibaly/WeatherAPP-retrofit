package com.example.weatherappretrofit.interfaces;

import com.example.weatherappretrofit.models.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherInterface {

    @GET("weather")
    Call<Weather> getTemperature(@Query("q") String city_name,
                                 @Query("appid") String apikey);
}
