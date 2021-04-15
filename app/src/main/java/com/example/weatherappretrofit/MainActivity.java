package com.example.weatherappretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherappretrofit.interfaces.WeatherInterface;
import com.example.weatherappretrofit.models.Weather;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String City = "London";
    private final String nameServer = "https://api.openweathermap.org/data/2.5/";
    private final String KEY = "4e2908b291e3062a8bd8774aac219ccb";
    private final String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";

    private TextView cityTxt;
    private TextView temp;
    private TextView tempFeelslike;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityTxt = findViewById(R.id.city_name);
        tempFeelslike = findViewById(R.id.txtFeelLike);
        temp=findViewById(R.id.txtValue);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(v->{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(nameServer)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WeatherInterface myAPI = retrofit.create(WeatherInterface.class);
            Call<Weather> temperatureCall = myAPI.getTemperature(temp.getText().toString().trim(), KEY);

            temperatureCall.enqueue(new Callback<Weather>() {

                @Override
                public void onResponse(Call<Weather> call, Response<Weather> response) {
                    if (response.code() == 404) {
                        Snackbar.make(v, "Enter a valid city", 5000)
                                .setAction("Okay", d -> {
                                }).setActionTextColor(Color.parseColor("#FFB0D9B9")).show();
                        return;
                    } else if (!response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Code : " + response.code(), Toast.LENGTH_LONG).show();
                    }




                    Weather weather = response.body();
                     Double temp = weather.getTemp();
                     Double tempFeelslike = weather.getFeelsLike();
                }

                @Override
                public void onFailure(Call<Weather> call, Throwable t) {

                    // En cas d'echec
                    Toast.makeText(MainActivity.this, "Erreur : " + t.getMessage(), Toast.LENGTH_LONG).show();
                }


            });

        });



    }
}