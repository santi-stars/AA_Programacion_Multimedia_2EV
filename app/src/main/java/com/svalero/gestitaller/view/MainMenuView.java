package com.svalero.gestitaller.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.svalero.gestitaller.R;

public class MainMenuView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void addBike(View view) {
        Intent intent = new Intent(this, AddBikeView.class);
        startActivity(intent);
    }

    public void addClient (View view){
        Intent intent = new Intent(this, AddClientView.class);
        startActivity(intent);
    }

    public void addOrder (View view){
        Intent intent = new Intent(this, AddOrderView.class);
        startActivity(intent);
    }

    public void viewBike (View view){
        Intent intent = new Intent(this, BikeListView.class);
        startActivity(intent);
    }

    public void viewClient (View view){
        Intent intent = new Intent(this, ClientListView.class);
        startActivity(intent);
    }

    public void viewOrder (View view){
        Intent intent = new Intent(this, OrderListView.class);
        startActivity(intent);
    }

}