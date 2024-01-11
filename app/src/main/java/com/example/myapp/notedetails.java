package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

public class notedetails extends AppCompatActivity {

    // Déclaration des éléments d'interface utilisateur
    private TextView mtitleofnotedetail, mcontentofnotedetail;
    FloatingActionButton mgotoeditnote;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notedetails);

        // Initialisation des éléments d'interface utilisateur
        mtitleofnotedetail = findViewById(R.id.titleofnotedetail);
        mcontentofnotedetail = findViewById(R.id.contentofnotedetail);
        mgotoeditnote = findViewById(R.id.gotoeditnote);
        Toolbar toolbar = findViewById(R.id.toolbarofnotedetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialisation de l'image de fond aléatoire
        imageView = findViewById(R.id.backGround);
        imageView.setImageResource(getRandomImageBackground());

        // Récupération des données de l'intent
        Intent data = getIntent();

        // Gestionnaire d'événements pour le clic sur le bouton pour éditer la note
        mgotoeditnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Création d'une intention pour ouvrir l'activité d'édition de note
                Intent intent = new Intent(v.getContext(), editnoteactivity.class);
                // Transmission des données de la note à l'activité d'édition
                intent.putExtra("title", data.getStringExtra("title"));
                intent.putExtra("content", data.getStringExtra("content"));
                intent.putExtra("noteId", data.getStringExtra("noteId"));
                v.getContext().startActivity(intent);
            }
        });

        // Affichage du contenu de la note dans l'interface utilisateur
        mcontentofnotedetail.setText(data.getStringExtra("content"));
        mtitleofnotedetail.setText(data.getStringExtra("title"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Gestion de l'icône de retour
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    // Méthode pour obtenir une image de fond aléatoire
    private int getRandomImageBackground() {
        int[] imageViews = {R.drawable.img1, R.drawable.img2, R.drawable.img4, R.drawable.img5};

        Random random = new Random();
        int number = random.nextInt(imageViews.length);
        return imageViews[number];
    }
}
