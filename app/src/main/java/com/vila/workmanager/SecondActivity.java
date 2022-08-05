package com.vila.workmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.vila.workmanager.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity
{
    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        ExtensionUtilsKt.setOnDoubleClickListenerCustom(binding.btCustom, this::useFunction);


    }

    private void useFunction(){
        Log.d("mcontrol","hola....");
    }


}