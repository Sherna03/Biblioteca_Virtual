package com.lostdream.bibliotecavirtual;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModel;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    TextInputEditText Password;
    EditText Email;
    Button login, register;

    String email, password;
    String url = "https://cbnknhhy.lucusvirtual.es/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        ImageButton google = (ImageButton)findViewById(R.id.google);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);


        validarSesion();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logins();
                correoValido();
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "En construcción, lamento los inconvenientes...", Toast.LENGTH_SHORT).show();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void validarSesion(){
        String email_usuario = preferences.getString("email_usuario", null);
        String password_usuario = preferences.getString("password_usuario", null);

        if (email_usuario != null && password_usuario != null){
            irInicio();
        }
    }

    private void irInicio(){
        Intent inicio = new Intent(this, Inicio.class);
        //inicio.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(inicio);
        finish();
    }

    private void correoValido(){
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();

        Pattern patternEmail = Pattern
                .compile("^[_A-Za-z0-9\\+]+(\\.[_A-Za-z0-9]+)*@"
                        + "[_A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3,})$");
        Matcher matcherEmail = patternEmail.matcher(email);


        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Ingrese un correo", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!matcherEmail.find()){
            Toast.makeText(this, "Ingrese un correo valido", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Ingresa una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (password.length() < 8){
            Toast.makeText(this, "La contraseña ingresada es muy corta", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Logins();
        }
    }

    public void Logins(){

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            email = Email.getText().toString().trim();
            password = Password.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if (response.equalsIgnoreCase("Login success")) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("email_usuario", email);
                        editor.putString("password_usuario", password);
                        editor.commit();

                        Email.setText("");
                        Password.setText("");

                        Intent inicio = new Intent(MainActivity.this, Inicio.class);
                        inicio.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(inicio);
                        //startActivity(new Intent(getApplicationContext(), Inicio.class));
                    } else {
                        Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(request);

    }

}