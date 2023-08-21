package com.example.gta_geo_torpedoassault.activities.player_mode;

import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gta_geo_torpedoassault.R;
import com.example.gta_geo_torpedoassault.activities.PlayActivity;
import com.example.gta_geo_torpedoassault.adapters.TorpedoAdapter;
import com.example.gta_geo_torpedoassault.models.Torpedo;
import com.example.gta_geo_torpedoassault.services.GameService;

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

    /**
     * Bouton Chasse.
     */
    Button hunterButton;

    /**
     * TextView du titre en mode paysage.
     */
    TextView titlePaysage;

    /**
     * TextView du titre en mode portrait.
     */
    TextView titlePortrait;

    /**
     * Service de jeu.
     */
    GameService gameService;

    /**
     * Méthode appelée lors de la création de l'activité.
     * @param savedInstanceState Etat de l'instance.
     */
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Récupérer l'instance de GameService
        gameService = PlayActivity.gameService;

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

    /**
     * Méthode appelée lors de la reprise de l'activité.
     */
    @Override
    protected void onResume() {
        super.onResume();
        updateTorpedoList();
    }

    /**
     * Méthode qui permet de mettre à jour l'interface en mode paysage.
     */
    private void setupLandScapeUI() {
        titlePaysage = findViewById(R.id.textView_title_manager_paysage);
        titlePaysage.setText(R.string.pilotage);
    }

    /**
     * Méthode qui permet de mettre à jour l'interface en mode portrait.
     */
    private void setupPortraitUI() {
        titlePortrait = findViewById(R.id.textView_title_manager_portrait);
        titlePortrait.setText(R.string.chambre);
        updateTorpedoList();
    }

    /**
     * Méthode qui permet de mettre à jour la liste des torpilles.
     */
    private void updateTorpedoList(){
        // Récupérer la liste des torpilles
        ListView torpedoesListView = findViewById(R.id.torpedoesListView);

        if(gameService == null) {
            Log.e(getString(R.string.managerActivity_debug), "gameService is null!");
            return;
        }

        java.util.List<com.example.gta_geo_torpedoassault.models.Torpedo> torpedos = gameService.getRetrievedTorpedos();
        Log.d(getString(R.string.managerActivity_debug), "Torpedoes list size: " + (torpedos != null ? torpedos.size() : "null"));

        if (torpedoesListView != null) {
            if(torpedos != null) {
                TorpedoAdapter adapter = new TorpedoAdapter(this, torpedos);
                torpedoesListView.setAdapter(adapter);
            }
        } else {
            Log.e(getString(R.string.managerActivity_debug), "torpedoesListView is null!");
        }
    }
}
