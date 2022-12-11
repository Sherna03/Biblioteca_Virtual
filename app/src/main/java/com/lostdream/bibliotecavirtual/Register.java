package com.lostdream.bibliotecavirtual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    // Variables de objetos en interfaz
    EditText Username, Nombre, Apellido, Telefono, Email;
    TextInputEditText Password, VerifyPassword;
    Button register;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //Instanciar la funcion para conectarse con Firebase
        mAuth = FirebaseAuth.getInstance();

        // Asignar variables a la lectura de casillas

        Username = findViewById(R.id.Username);
        Nombre = findViewById(R.id.Nombre);
        Apellido = findViewById(R.id.Apellido);
        Telefono = findViewById(R.id.Telefono);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        VerifyPassword = findViewById(R.id.VerifyPassword);
        register = findViewById(R.id.register);

        // Boton registrar
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lamar la funcion para ingresar datos
                //registroFirebase();
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
        String verifypassword = VerifyPassword.getText().toString().trim();

        //Permitir unicamente un correo valido
        Pattern patternEmail = Pattern
                .compile("^[_A-Za-zñÑ0-9\\+]+(\\.[_A-Za-zñÑ0-9]+)*@"
                        + "[a-z0-9-]+(\\.[a-z0-9]+)*(\\.[a-z]{3,})$");
        Matcher matcherEmail = patternEmail.matcher(email);

        //Permitir unicamente texto del abecedario y tres numeros
        Pattern patternText = Pattern
                .compile("^([0-9]?+[_A-Za-zñÑáéíóúÁÉÍÓÚ]+[0-9]?+[0-9]?){2,12}$");
        Matcher matcherUsuario = patternText.matcher(username);

        //Permitir unicamente texto del abecedario español y un espacio opcional
        Pattern patternNombreCompleto = Pattern
                .compile("^([A-Za-zñÑáéíóúÁÉÍÓÚ]+[ ]?){2,12}$");
        Matcher matcherNombre = patternNombreCompleto.matcher(nombre);
        Matcher matcherApellido = patternNombreCompleto.matcher(apellido);

        //Permitir unicamente numeros de un maximo y minimo de 10
        Pattern patternTelefono = Pattern
                .compile("^[0-9]{10,10}$");
        Matcher matcherTelefono = patternTelefono.matcher(telefono);


        // Comprobar que las casillas no se encuentren vacias y cumplan con los requisitos

        if(username.isEmpty() | username.length() <= 1){
            Username.setError("Rellene los Datos");
        } else if (!matcherUsuario.find()){
            Username.setError("Usuario no valido");
        } else if (nombre.isEmpty() | nombre.length() <= 1){
            Nombre.setError("Rellene los Datos");
        } else if (!matcherNombre.find()){
            Nombre.setError("Nombre no valido");
        } else if (apellido.isEmpty() | apellido.length() <= 1){
            Apellido.setError("Rellene los Datos");
        } else if (!matcherApellido.find()){
            Apellido.setError("Apellido no valido");
        } else if (telefono.isEmpty()){
            Telefono.setError("Rellene los Datos");
        } else if (!matcherTelefono.find()){
            Telefono.setError("Telefono no valido");
        } else if (TextUtils.isEmpty(email)){
            Email.setError("Rellene los Datos");
        } else if (!matcherEmail.find()){
            Toast.makeText(this, "Ingrese un correo valido", Toast.LENGTH_SHORT).show();
            Email.setError("Invalid Email");
        } else if (TextUtils.isEmpty(password)){
            Password.setError("Rellene los Datos");
        } else if (password.length() < 8){
            Toast.makeText(this, "La contraseña ingresada es muy corta", Toast.LENGTH_SHORT).show();
            Password.setError("Password short");
        } else if (!password.equals(verifypassword)){
            VerifyPassword.setError("La contraseña no coincide");
        } else {
            // Inicar metodo
            registroDatos();
        }
    }

    // Metodo para registrar los datos
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

        //Hasmap para guardar datos del usuario registrado y enviarlos a la base de datos
        Map<String,String> params= new HashMap<>();
        params.put("username", username);
        params.put("nombre", nombre);
        params.put("apellido", apellido);
        params.put("telefono", telefono);
        params.put("email", email);



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String id = mAuth.getCurrentUser().getUid();
                            FirebaseDatabase.getInstance().getReference().child("data").child(id)
                                    .setValue(params)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            updateUI(user);
                                            progressDialog.dismiss();
                                            Toast.makeText(Register.this, "Registro Exitoso", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(Register.this, "No se pudo insertar los datos", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(Register.this, "Email ya registrado con Google", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }

    // Metodo para indicar que el usuario ya se registro y cambia de actividad
    private void updateUI(FirebaseUser user) {
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        if (user1 != null){
            Intent inicio = new Intent(Register.this, Inicio.class);
            inicio.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(inicio);
        }
    }
    // Metodo para limpiar las casillas en caso de que el usuario vuelva al login
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}