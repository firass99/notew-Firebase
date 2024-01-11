package com.example.myapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // Déclaration des éléments d'interface utilisateur
    private EditText mloginemail, mloginpassword;
    private RelativeLayout mlogin, mgotosignup;
    private TextView mgotoforgotpassword;

    // Instance de FirebaseAuth pour gérer l'authentification
    private FirebaseAuth firebaseAuth;

    // Barre de progression pour l'activité principale
    ProgressBar mprogressbarofmainactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Masquer la barre d'action de l'activité
        getSupportActionBar().hide();

        // Initialisation des éléments d'interface utilisateur
        mloginemail = findViewById(R.id.loginemail);
        mloginpassword = findViewById(R.id.loginpassword);
        mlogin = findViewById(R.id.login);
        mgotoforgotpassword = findViewById(R.id.gotoforgotpassword);
        mgotosignup = findViewById(R.id.gotosignup);
        mprogressbarofmainactivity = findViewById(R.id.progressbarofmainactivity);

        // Initialisation de l'instance de FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Vérifier si l'utilisateur est déjà connecté
        if (firebaseUser != null) {
            finish();
            startActivity(new Intent(MainActivity.this, notesactivity.class));
        }

        // Gestionnaire d'événements pour le clic sur le lien de redirection vers l'écran d'inscription
        mgotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, signup.class));
            }
        });

        // Gestionnaire d'événements pour le clic sur le lien de redirection vers l'écran de récupération de mot de passe
        mgotoforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, fogotpassword.class));
            }
        });

        // Gestionnaire d'événements pour le clic sur le bouton de connexion
        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = mloginemail.getText().toString().trim();
                String password = mloginpassword.getText().toString().trim();

                if (mail.isEmpty() || password.isEmpty()) {
                    // Affichage d'un message si tous les champs sont requis
                    Toast.makeText(getApplicationContext(), "Tous les champs sont requis", Toast.LENGTH_SHORT).show();

                } else {
                    // Connexion de l'utilisateur
                    mprogressbarofmainactivity.setVisibility(View.VISIBLE);

                    firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Vérification de l'e-mail après la connexion
                                checkmailverfication();
                            } else {
                                // Affichage d'un message si le compte n'existe pas
                                Toast.makeText(getApplicationContext(), "Le compte n'existe pas", Toast.LENGTH_SHORT).show();
                                mprogressbarofmainactivity.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
            }
        });
    }

    // Méthode pour vérifier si l'e-mail de l'utilisateur a été vérifié
    private void checkmailverfication() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser.isEmailVerified() == true) {
            // Affichage d'un message si la connexion est réussie
            Toast.makeText(getApplicationContext(), "Connecté", Toast.LENGTH_SHORT).show();
            // Fermeture de l'activité courante et redirection vers l'écran des notes
            finish();
            startActivity(new Intent(MainActivity.this, notesactivity.class));
        } else {
            // Affichage d'un message si l'e-mail n'a pas été vérifié
            mprogressbarofmainactivity.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Vérifiez votre e-mail d'abord", Toast.LENGTH_SHORT).show();
            // Déconnexion de l'utilisateur
            firebaseAuth.signOut();
        }
    }
}
