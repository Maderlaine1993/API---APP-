package com.example.apilaravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void inicio(View view){

        Intent inicio = new Intent(this, MainActivity.class);
        startActivity(inicio);

    }

    public void registroLogin(View view) {
        Intent registroLogin = new Intent(this, RegistroLogin.class);
        startActivity(registroLogin);
    }

}
