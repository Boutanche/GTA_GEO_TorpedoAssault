package com.example.gta_geo_torpedoassault.models;

public class Score {

    private int score;
    private long date;

    // Constructeur
    public Score(int score, long date) {
        this.score = score;
        this.date = date;
    }

    // Getter pour le score
    public int getScore() {
        return score;
    }

    // Getter pour la date
    public long getDate() {
        return date;
    }

    // Setter pour le score
    public void setScore(int score) {
        this.score = score;
    }

    // Setter pour la date
    public void setDate(long date) {
        this.date = date;
    }
}