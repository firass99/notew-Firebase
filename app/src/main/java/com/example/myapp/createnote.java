package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.myapp.R;
import com.example.myapp.notesactivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createnote extends AppCompatActivity {

    // Déclaration des éléments d'interface utilisateur
    EditText mcreatetitleofnote, mcreatecontentofnote;
    FloatingActionButton msavenote;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    ProgressBar mprogressbarofcreatenote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);

        // Initialisation des éléments d'interface utilisateur
        msavenote = findViewById(R.id.savenote);
        mcreatecontentofnote = findViewById(R.id.createcontentofnote);
        mcreatetitleofnote = findViewById(R.id.createtitleofnote);

        mprogressbarofcreatenote = findViewById(R.id.progressbarofcreatenote);
        Toolbar toolbar = findViewById(R.id.toolbarofcreatenote);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialisation des instances Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Gestionnaire de clic pour le bouton d'enregistrement de la note
        msavenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNote();
            }
        });
    }

    // Méthode pour créer une nouvelle note
    private void createNote() {
        // Récupérer le titre et le contenu de la note depuis les champs d'édition
        String title = mcreatetitleofnote.getText().toString().trim();
        String content = mcreatecontentofnote.getText().toString().trim();
        if (title.isEmpty() || content.isEmpty()) {
            // Afficher un message d'erreur si les champs sont vides
            Toast.makeText(getApplicationContext(), "Les deux champs sont requis", Toast.LENGTH_SHORT).show();
        } else {
            // Afficher la barre de progression pendant l'enregistrement de la note
            mprogressbarofcreatenote.setVisibility(View.VISIBLE);

            // Référence du document pour la nouvelle note dans Firestore
            DocumentReference documentReference = firebaseFirestore
                    .collection("notes")
                    .document(firebaseUser.getUid())
                    .collection("myNotes")
                    .document();

            // Créer une carte (Map) avec les données de la note
            Map<String, Object> note = new HashMap<>();
            note.put("title", title);
            note.put("content", content);

            // Enregistrer la note dans Firestore
            documentReference.set(note)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Afficher un message de succès et passer à l'activité Notes
                            Toast.makeText(getApplicationContext(), "Note créée avec succès", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(createnote.this, notesactivity.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Afficher un message d'échec en cas d'erreur
                            Toast.makeText(getApplicationContext(), "Échec de la création de la note", Toast.LENGTH_SHORT).show();
                            mprogressbarofcreatenote.setVisibility(View.INVISIBLE);
                        }
                    });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Gérer le retour arrière dans la barre d'action
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
