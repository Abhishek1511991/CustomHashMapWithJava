package com.example.customhashmapinjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.demo.custom_haspmap.MyCustomHashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyCustomHashMap customHashMap = new MyCustomHashMap<String, String>();
        customHashMap.put("Ab", "Abhishek");
        customHashMap.put("Ab", "Srivastava");
        customHashMap.put("Ac", "Srivastava");
        customHashMap.put("ABC", "123");
        customHashMap.put("1", "246");
        customHashMap.put("2", "2323");
        customHashMap.put("pa", "245");
        customHashMap.put("Ac", "Goldi");
    }
}