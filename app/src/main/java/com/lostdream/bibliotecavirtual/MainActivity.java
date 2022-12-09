package com.lostdream.bibliotecavirtual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    //Variables de objetos de interfaz llamados
    TextInputEditText Password;
    EditText Email;
    Button login, register;

    //Variables privadas para el inicio de sesion con google
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    // Variable privarada para verificacion con Firebase
    private FirebaseAuth mAuth;

    // Variable privada
    private GoogleSignInClient mGoogleSignInClient;

    // Variables
    String email, password;
    //String url = "https://cbnknhhy.lucusvirtual.es/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Token Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Google singin
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Llamar objetos de interfaz
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        ImageButton google = (ImageButton)findViewById(R.id.google);


        // Boton Login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validar correo
                correoValido();
            }
        });

        // Boton Google
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        // Boton Registrar
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cambiar a actividad Registro
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    // Comprobar inicio de google al iniciar
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    // Validar inicio de sesion con boton google
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    // Inicio de sesion con boton Google
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    // Metodo para iniciar sesion
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Metodo para saber si el usuario ya ha iniciado sesion anteriormente
    private void updateUI(FirebaseUser user) {
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        if (user1 != null){
            Intent inicio = new Intent(MainActivity.this, Inicio.class);
            inicio.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(inicio);
        }
    }

    // Validar que el correo introducido sea un correo
    private void correoValido(){
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();

        Pattern patternEmail = Pattern
                .compile("^[_A-Za-zñÑ0-9\\+]+(\\.[_A-Za-zñÑ0-9]+)*@"
                        + "[a-z0-9-]+(\\.[a-z0-9]+)*(\\.[a-z]{3,})$");
        Matcher matcherEmail = patternEmail.matcher(email);


        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Ingrese un correo", Toast.LENGTH_SHORT).show();
        }
        else if (!matcherEmail.find()){
            Toast.makeText(this, "Ingrese un correo valido", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Ingresa una contraseña", Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 8){
            Toast.makeText(this, "La contraseña ingresada es muy corta", Toast.LENGTH_SHORT).show();
        }
        else {
            Logins();
        }
    }

    // Metodo para iniciar con email y password
    public void Logins(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                            Intent inicio = new Intent(MainActivity.this, Inicio.class);
                            inicio.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(inicio);
                        } else {
                            Toast.makeText(MainActivity.this, "Email or Password Incorret", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}