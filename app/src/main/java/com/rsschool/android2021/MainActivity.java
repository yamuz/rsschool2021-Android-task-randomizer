package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.rsschool.android2021.interfaces.RandomListener;

public class MainActivity extends AppCompatActivity implements RandomListener {

    private final String MIN_VALUE_KEY = "MIN_VALUE";
    private final String MAX_VALUE_KEY = "MAX_VALUE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState==null){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            openFirstFragment(0);
        }
    }

    void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment);
        // TODO: invoke function which apply changes of the transaction
        transaction.commit();
    }

    void openSecondFragment(int min, int max) {
        // TODO: implement it
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, secondFragment);
        transaction.commit();
    }

    @Override //impl interface
    public void onButtonPressed(int min, int max) {
        openSecondFragment(min, max);
    }
}
