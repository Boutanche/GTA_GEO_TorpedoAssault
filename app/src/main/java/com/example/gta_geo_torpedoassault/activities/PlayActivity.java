package com.example.gta_geo_torpedoassault.activities;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gta_geo_torpedoassault.R;
import com.example.gta_geo_torpedoassault.activities.player_mode.HunterActivity;
import com.example.gta_geo_torpedoassault.activities.player_mode.ManagerActivity;

/**
 * Activité de jeu.
 * Ecran de lancement de la partie.
 */
public class PlayActivity extends AppCompatActivity {

        /**
        * Méthode appelée lors de la création de l'activité.
        * @param savedInstanceState Etat de l'instance.
        */
        @Override
        protected void onCreate(android.os.Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_play);

            // Les boutons du menu jeu sont déclarés ici
            Button btnHunter = findViewById(R.id.btnHunter);
            Button btnManager = findViewById(R.id.btnManager);
            // Les boutons du menu jeu sont initialisés ici
            btnHunter.setText(R.string.mode_chasse);
            btnManager.setText(R.string.mode_gestion);
            // OnClickListeners pour les boutons du menu jeu
            // Chaque bouton lance une nouvelle activité
            // Lorsque le bouton Chasse est cliqué, l'activité HunterActivity est lancée
            btnHunter.setOnClickListener(v -> {
                android.util.Log.d(getString(R.string.playActivity_debug), "Hunter button clicked");
                android.content.Intent intent = new android.content.Intent(PlayActivity.this, HunterActivity.class);
                startActivity(intent);
            });
            // Lorsque le bouton Gestion est cliqué, l'activité ManagerActivity est lancée
            btnManager.setOnClickListener(v -> {
                android.util.Log.d(getString(R.string.playActivity_debug), "Manager button clicked");
                android.content.Intent intent = new android.content.Intent(PlayActivity.this, ManagerActivity.class);
                startActivity(intent);
            });
        }
}
