package com.example.gta_geo_torpedoassault.contracts;

import android.provider.BaseColumns;

public final class ScoreContract {

    // Pour prévenir qu'on instancie accidentellement la classe contract, on lui donne un constructeur vide.
    private ScoreContract() {}

    /* Classe interne qui définit les constantes de la table */
    public static class ScoreEntry implements BaseColumns {
        public static final String TABLE_NAME = "Scores";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
