package com.lostdream.bibliotecavirtual;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText Username, Nombre, Apellido, Telefono, Email, Password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Asignar variables a la lectura de casillas

        Username = findViewById(R.id.Username);
        Nombre = findViewById(R.id.Nombre);
        Apellido = findViewById(R.id.Apellido);
        Telefono = findViewById(R.id.Telefono);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        register = findViewById(R.id.register);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lamar la funcion para ingresar datos

                emailRegistrado();
                //registroDatos();
            }
        });
    }

    private void emailRegistrado(){
        String email = Email.getText().toString().trim();
        String username = Username.getText().toString().trim();
        String nombre = Nombre.getText().toString().trim();
        String apellido = Apellido.getText().toString().trim();
        String telefono = Telefono.getText().toString().trim();
        String password = Password.getText().toString().trim();

        // Comprobar que las casillas no se encuentren vacias

         if(username.isEmpty()){
            Username.setError("Rellene los Datos");
            return;
        } else if (nombre.isEmpty()){
            Nombre.setError("Rellene los Datos");
            return;
        } else if (apellido.isEmpty()){
            Apellido.setError("Rellene los Datos");
            return;
        } else if (telefono.isEmpty()){
            Telefono.setError("Rellene los Datos");
            return;
        } else if (email.isEmpty()) {
            Email.setError("Rellene los Datos");
            return;
        } else if (password.isEmpty()){
            Password.setError("Rellene los Datos");
            return;
        }

        else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Comprobando");
            progressDialog.show();

            StringRequest request = new StringRequest(Request.Method.POST, "https://cbnknhhy.lucusvirtual.es/TestEmail.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if (response.equalsIgnoreCase("Email ya registrado")) {
                        Email.setError("Email ya registrado");

                    } else {
                        Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                        registroDatos();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Register.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
            requestQueue.add(request);
        }
    }



    private void registroDatos() {

        // Pasar texto ingresado a String

        final String username = Username.getText().toString().trim();
        final String nombre = Nombre.getText().toString().trim();
        final String apellido = Apellido.getText().toString().trim();
        final String telefono = Telefono.getText().toString().trim();
        final String email = Email.getText().toString().trim();
        final String password = Password.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando");


        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, "https://cbnknhhy.lucusvirtual.es/insertar.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Registro Exitoso")) {
                    Toast.makeText(Register.this, "Datos insertados", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    Intent intent = new Intent(Register.this, Inicio.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Register.this, response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Toast.makeText(Register.this, "No se pudo insertar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Register.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>params= new HashMap<String,String>();
                params.put("username", username);
                params.put("nombre", nombre);
                params.put("apellido", apellido);
                params.put("telefono", telefono);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}