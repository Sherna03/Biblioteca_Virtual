package com.lostdream.bibliotecavirtual;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    EditText Username, Nombre, Apellido, Telefono, Email;
    TextInputEditText Password;
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

                emailAndPassword();
            }
        });
    }


    private void emailAndPassword(){

        // Llamar el texto en los cuadros

        String email = Email.getText().toString().trim();
        String username = Username.getText().toString().trim();
        String nombre = Nombre.getText().toString().trim();
        String apellido = Apellido.getText().toString().trim();
        String telefono = Telefono.getText().toString().trim();
        String password = Password.getText().toString().trim();

        //Permitir unicamente un correo valido
        Pattern patternEmail = Pattern
                .compile("^[_A-Za-zñÑ0-9\\+]+(\\.[_A-Za-zñÑ0-9]+)*@"
                        + "[a-z0-9-]+(\\.[a-z0-9]+)*(\\.[a-z]{3,})$");
        Matcher matcherEmail = patternEmail.matcher(email);

        //Permitir unicamente texto del abecedario
        Pattern patternText = Pattern
                .compile("^([0-9]?+[_A-Za-z]+[0-9]?+[0-9]?){2,12}$");
        Matcher matcherUsuario = patternText.matcher(username);

        //Permitir unicamente texto del abecedario español y un espacio opcional
        Pattern patternNombreCompleto = Pattern
                .compile("^([A-Za-zñÑáéíóúÁÉÍÓÚ]+[ ]?){2,12}$");
        Matcher matcherNombre = patternNombreCompleto.matcher(nombre);
        Matcher matcherApellido = patternNombreCompleto.matcher(apellido);

        //Permitir unicamente numeros
        Pattern patternTelefono = Pattern
                .compile("^[0-9]{10,10}$");
        Matcher matcherTelefono = patternTelefono.matcher(telefono);

        // Comprobar que las casillas no se encuentren vacias

        if(username.isEmpty() | username.length() <= 1){
            Username.setError("Rellene los Datos");
            return;
        } else if (!matcherUsuario.find()){
            Username.setError("Usuario no valido");
            return;
        } else if (nombre.isEmpty() | nombre.length() <= 1){
            Nombre.setError("Rellene los Datos");
            return;
        } else if (!matcherNombre.find()){
            Nombre.setError("Nombre no valido");
            return;
        } else if (apellido.isEmpty() | apellido.length() <= 1){
            Apellido.setError("Rellene los Datos");
            return;
        } else if (!matcherApellido.find()){
            Apellido.setError("Apellido no valido");
            return;
        } else if (telefono.isEmpty()){
            Telefono.setError("Rellene los Datos");
            return;
        } else if (!matcherTelefono.find()){
            Telefono.setError("Telefono no valido");
            return;
        } else if (TextUtils.isEmpty(email)){
            Email.setError("Rellene los Datos");
            return;
        } else if (!matcherEmail.find()){
            Toast.makeText(this, "Ingrese un correo valido", Toast.LENGTH_SHORT).show();
            Email.setError("Invalid Email");
            return;
        } else if (TextUtils.isEmpty(password)){
            Password.setError("Rellene los Datos");
            return;
        } else if (password.length() < 8){
            Toast.makeText(this, "La contraseña ingresada es muy corta", Toast.LENGTH_SHORT).show();
            Password.setError("Password short");
            return;
        } else {
            emailRegistrado();
        }
    }

    private void emailRegistrado(){

        String email = Email.getText().toString().trim();

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

                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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