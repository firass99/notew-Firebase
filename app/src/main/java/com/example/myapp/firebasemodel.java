package com.example.myapp;
// Classe représentant le modèle de données pour une note dans Firebase
public class firebasemodel {

    // Attributs privés représentant le titre et le contenu de la note
    private String title;
    private String content;

    // Constructeur par défaut requis par Firebase
    public firebasemodel() {
    }

    // Constructeur paramétré pour initialiser le titre et le contenu de la note
    public  firebasemodel (String title, String content){
        this.title = title;
        this.content = content;
    }

    // Méthode pour récupérer le titre de la note
    public String getTitle() {
        return title;
    }

    // Méthode pour définir le titre de la note
    public void setTitle(String title) {
        this.title = title;
    }

    // Méthode pour récupérer le contenu de la note
    public String getContent() {
        return content;
    }

    // Méthode pour définir le contenu de la note
    public void setContent(String content) {
        this.content = content;
    }
}
