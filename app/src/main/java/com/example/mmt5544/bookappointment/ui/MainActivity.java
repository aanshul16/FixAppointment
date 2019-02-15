package com.example.mmt5544.bookappointment.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.mmt5544.bookappointment.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        FirstScreenFragment firstScreenFragment = FirstScreenFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, firstScreenFragment, FirstScreenFragment.TAG).commit();
    }

}
