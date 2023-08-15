package com.example.gta_geo_torpedoassault.activities.player_mode;

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gta_geo_torpedoassault.R;

/**
 * ## ManagerActivity ##
 * Activité qui permet de jouer en mode Gestion :
 * ### Portrait ###
 * - Chambre des torpilles
 * -- Permet de choisir la torpille à utiliser
 * -- Permet de choisir les bonus à activer
 * ### Paysage ###
 * - Poste de pilotage
 * --
 */
public class ManagerActivity extends AppCompatActivity {
    Button hunterButton;
    TextView titlePaysage;
    TextView titlePortrait;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(com.example.gta_geo_torpedoassault.R.layout.activity_manager_landscape);
            setupLandScapeUI();
        } else {
            setContentView(com.example.gta_geo_torpedoassault.R.layout.activity_manager_portrait);
            setupPortraitUI();
        }
        this.hunterButton = findViewById(com.example.gta_geo_torpedoassault.R.id.hunter_button);
        hunterButton.setOnClickListener(v -> {
            android.util.Log.d(getString(com.example.gta_geo_torpedoassault.R.string.managerActivity_debug), "Hunter button clicked");
            android.content.Intent intent = new android.content.Intent(ManagerActivity.this, HunterActivity.class);
            startActivity(intent);
        });
    }
    private void setupLandScapeUI() {
        titlePaysage = findViewById(R.id.textView_title_manager_paysage);
        titlePaysage.setText(R.string.pilotage);
    }

    private void setupPortraitUI() {
        titlePortrait = findViewById(R.id.textView_title_manager_portrait);
        titlePortrait.setText(R.string.chambre);
    }
}
