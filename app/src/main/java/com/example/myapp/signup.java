package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
// Activité pour gérer l'inscription d'un nouvel utilisateur
public class signup extends AppCompatActivity {

    // Champs d'entrée pour l'e-mail et le mot de passe de l'utilisateur
    private EditText msignupemail, msignuppassword;

    // Bouton d'inscription, lien vers l'écran de connexion
    private RelativeLayout msignup;
    private TextView mgotologin;

    // Objet pour gérer l'authentification Firebase
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Masquer la barre d'action
        getSupportActionBar().hide();

        // Initialisation des vues et de l'objet d'authentification Firebase
        msignupemail = findViewById(R.id.signupemail);
        msignuppassword = findViewById(R.id.signuppassword);
        msignup = findViewById(R.id.signup);
        mgotologin = findViewById(R.id.gotologin);
        firebaseAuth = FirebaseAuth.getInstance();

        // Gestionnaire de clic pour le lien vers l'écran de connexion
        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Gestionnaire de clic pour le bouton d'inscription
        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Récupération de l'e-mail et du mot de passe saisis par l'utilisateur
                String mail = msignupemail.getText().toString().trim();
                String password = msignuppassword.getText().toString().trim();

                // Vérification des champs vides
                if (mail.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 7) {
                    Toast.makeText(getApplicationContext(), "Le mot de passe doit contenir plus de 7 chiffres", Toast.LENGTH_SHORT).show();
                } else {
                    // Enregistrement de l'utilisateur dans Firebase avec e-mail et mot de passe
                    firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                // Affichage d'un message de réussite et envoi de la vérification par e-mail
                                Toast.makeText(getApplicationContext(), "Inscription réussie", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            } else {
                                // Affichage d'un message d'échec en cas d'erreur
                                Toast.makeText(getApplicationContext(), "Échec de linscription", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    // Envoi d'un e-mail de vérification à l'utilisateur
    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // Affichage d'un message et déconnexion après l'envoi de l'e-mail de vérification
                    Toast.makeText(getApplicationContext(), "Un e-mail de vérification a été envoyé. Veuillez vérifier votre boîte de réception et connectez-vous à nouveau.", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(signup.this, MainActivity.class));
                }
            });
        } else {
            // Affichage d'un message en cas d'échec de l'envoi de l'e-mail de vérification
            Toast.makeText(getApplicationContext(), "Échec de l'envoi de l'e-mail de vérification.", Toast.LENGTH_SHORT).show();
        }
    }
}
