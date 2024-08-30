package com.example.unitcoverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etInputValue;
    private Spinner spinnerCategory, spinnerFromUnit, spinnerToUnit;
    private Button btnConvert;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInputValue = findViewById(R.id.etInputValue);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit);
        spinnerToUnit = findViewById(R.id.spinnerToUnit);
        btnConvert = findViewById(R.id.btnConvert);
        tvResult = findViewById(R.id.tvResult);

        String[] categories = {"Length", "Time", "Volume"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUnitsBasedOnCategory(categories[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputValueStr = etInputValue.getText().toString();
                if (inputValueStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                    return;
                }

                double inputValue = Double.parseDouble(inputValueStr);
                String fromUnit = spinnerFromUnit.getSelectedItem().toString();
                String toUnit = spinnerToUnit.getSelectedItem().toString();
                String category = spinnerCategory.getSelectedItem().toString();

                double result = convertUnits(inputValue, fromUnit, toUnit, category);
                tvResult.setText("Result: " + result);
            }
        });
    }

    private void updateUnitsBasedOnCategory(String category) {
        String[] units;
        switch (category) {
            case "Time":
                units = new String[]{"Seconds", "Minutes", "Hours"};
                break;
            case "Volume":
                units = new String[]{"Liters", "Milliliters", "Cubic Meters"};
                break;
            case "Length":
            default:
                units = new String[]{"Meters", "Kilometers", "Centimeters", "Inches", "Feet", "Yards", "Miles"};
                break;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromUnit.setAdapter(adapter);
        spinnerToUnit.setAdapter(adapter);
    }

    private double convertUnits(double value, String fromUnit, String toUnit, String category) {
        double result = 0;

        switch (category) {
            case "Length":
                result = convertLengthUnits(value, fromUnit, toUnit);
                break;
            case "Time":
                result = convertTimeUnits(value, fromUnit, toUnit);
                break;
            case "Volume":
                result = convertVolumeUnits(value, fromUnit, toUnit);
                break;
        }

        return result;
    }

    private double convertLengthUnits(double value, String fromUnit, String toUnit) {
        double meters = 0;

        // Convert from input unit to meters
        switch (fromUnit) {
            case "Meters":
                meters = value;
                break;
            case "Kilometers":
                meters = value * 1000;
                break;
            case "Centimeters":
                meters = value / 100;
                break;
            case "Inches":
                meters = value * 0.0254;
                break;
            case "Feet":
                meters = value * 0.3048;
                break;
            case "Yards":
                meters = value * 0.9144;
                break;
            case "Miles":
                meters = value * 1609.34;
                break;
        }

        // Convert from meters to output unit
        switch (toUnit) {
            case "Meters":
                return meters;
            case "Kilometers":
                return meters / 1000;
            case "Centimeters":
                return meters * 100;
            case "Inches":
                return meters / 0.0254;
            case "Feet":
                return meters / 0.3048;
            case "Yards":
                return meters / 0.9144;
            case "Miles":
                return meters / 1609.34;
        }

        return 0;
    }

    private double convertTimeUnits(double value, String fromUnit, String toUnit) {
        double seconds = 0;

        // Convert from input unit to seconds
        switch (fromUnit) {
            case "Seconds":
                seconds = value;
                break;
            case "Minutes":
                seconds = value * 60;
                break;
            case "Hours":
                seconds = value * 3600;
                break;
        }

        // Convert from seconds to output unit
        switch (toUnit) {
            case "Seconds":
                return seconds;
            case "Minutes":
                return seconds / 60;
            case "Hours":
                return seconds / 3600;
        }

        return 0;
    }

    private double convertVolumeUnits(double value, String fromUnit, String toUnit) {
        double liters = 0;

        // Convert from input unit to liters
        switch (fromUnit) {
            case "Liters":
                liters = value;
                break;
            case "Milliliters":
                liters = value / 1000;
                break;
            case "Cubic Meters":
                liters = value * 1000;
                break;
        }

        // Convert from liters to output unit
        switch (toUnit) {
            case "Liters":
                return liters;
            case "Milliliters":
                return liters * 1000;
            case "Cubic Meters":
                return liters / 1000;
        }

        return 0;
    }
}
