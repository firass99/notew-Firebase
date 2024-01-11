package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.MainActivity;
import com.example.myapp.R;
import com.google.android.gms.tasks.*;
import com.google.firebase.auth.FirebaseAuth;

public class fogotpassword extends AppCompatActivity {

    // Déclaration des éléments d'interface utilisateur
    private EditText mforgotpassword;
    private Button mpasswordrecoverbutton;
    private TextView mgobacktologin;

    // Instance de FirebaseAuth pour gérer l'authentification
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogotpassword);

        // Masquer la barre d'action de l'activité
        getSupportActionBar().hide();

        // Initialisation des éléments d'interface utilisateur
        mforgotpassword=findViewById(R.id.forgotpassword);
        mpasswordrecoverbutton=findViewById(R.id.passwordrecoverbutton);
        mgobacktologin=findViewById(R.id.gobacktologin);

        // Initialisation de l'instance de FirebaseAuth
        firebaseAuth= FirebaseAuth.getInstance();

        // Gestionnaire d'événements pour le clic sur le lien de retour à l'écran de connexion
        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirection vers l'écran de connexion
                Intent intent=new Intent(fogotpassword.this,MainActivity.class);
                startActivity(intent);
            }
        });

        // Gestionnaire d'événements pour le clic sur le bouton de récupération de mot de passe
        mpasswordrecoverbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération de l'adresse e-mail saisie
                String mail=mforgotpassword.getText().toString().trim();
                if(mail.isEmpty())
                {
                    // Affichage d'un message si l'adresse e-mail est vide
                    Toast.makeText(getApplicationContext(),"Entrez votre adresse e-mail d'abord",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Envoi de l'e-mail de récupération du mot de passe à l'adresse spécifiée
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                // Affichage d'un message si l'e-mail a été envoyé avec succès
                                Toast.makeText(getApplicationContext(),"E-mail envoyé. Vous pouvez récupérer votre mot de passe via l'e-mail",Toast.LENGTH_SHORT).show();
                                // Fermeture de l'activité courante et redirection vers l'écran de connexion
                                finish();
                                startActivity(new Intent(fogotpassword.this, MainActivity.class));
                            }
                            else
                            {
                                // Affichage d'un message si l'adresse e-mail est incorrecte ou si le compte n'existe pas
                                Toast.makeText(getApplicationContext(),"L'e-mail est incorrect ou le compte n'existe pas",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
