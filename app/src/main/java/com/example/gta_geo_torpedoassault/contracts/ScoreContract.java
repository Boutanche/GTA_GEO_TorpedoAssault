package com.example.gta_geo_torpedoassault.contracts;

import android.provider.BaseColumns;

/**
 * Classe qui définit la structure de la table des scores.
 */
public final class ScoreContract {

    // Pour prévenir qu'on instancie accidentellement la classe contract, on lui donne un constructeur vide.
    private ScoreContract() {}

    /**
     * Classe interne qui définit les constantes de la table
     */
    public static class ScoreEntry implements BaseColumns {

        /**
         * Nom de la table
         */
        public static final String TABLE_NAME = "Scores";

        /**
         * Nom de la colonne du score
         */
        public static final String COLUMN_NAME_SCORE = "score";

        /**
         * Nom de la colonne de la date
         */
        public static final String COLUMN_NAME_DATE = "date";
    }
}
