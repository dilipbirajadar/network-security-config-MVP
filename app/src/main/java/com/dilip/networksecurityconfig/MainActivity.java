package com.dilip.networksecurityconfig;

import android.os.Bundle;

import com.dilip.networksecurityconfig.data.DataSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirstFragment mainFragment =
                (FirstFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (mainFragment == null) {
            mainFragment = FirstFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentFrame, mainFragment)
                    .commit();
        }

        DataSource data = Injection.provideDataSource(this);
        MainContract.Presenter presenter = new MainPresenter(data, mainFragment);
        mainFragment.setPresenter(presenter);
    }
}