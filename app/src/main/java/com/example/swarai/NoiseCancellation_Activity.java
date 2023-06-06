package com.example.swarai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NoiseCancellation_Activity extends AppCompatActivity {

    String[] spinnerFrequency = {"Frequency Threshold", "1.0", "1.3", "1.5", "1.8", "2.0"};

    Spinner noiceSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noise_cancellation);

        noiceSpinner = findViewById(R.id.noiceSpinner);

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerFrequency);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noiceSpinner.setAdapter(ad);

    }
}