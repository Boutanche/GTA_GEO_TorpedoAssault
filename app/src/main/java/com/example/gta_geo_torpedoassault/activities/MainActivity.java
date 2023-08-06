package com.example.gta_geo_torpedoassault.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.gta_geo_torpedoassault.R;

/**
 * MainActivity is the main menu of the game.
 * It contains buttons to play the game, view the high scores, view the game info, and configure the game.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * onCreate is called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.
     *     <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Les boutons du menu principal sont déclarés ici
        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnLocate = findViewById(R.id.btnLocate);
        Button btnScores = findViewById(R.id.btnScores);
        Button btnInfo = findViewById(R.id.btnInfo);
        Button btnConfig = findViewById(R.id.btnConfig);

        // Les boutons du menu principal sont initialisés ici
        btnPlay.setText(R.string.btnPlay);
        btnLocate.setText(R.string.btnLocate);
        btnScores.setText(R.string.btnScores);
        btnInfo.setText(R.string.btnInfo);
        btnConfig.setText(R.string.btnConfig);

        // OnClickListeners pour les boutons du menu principal
        // Chaque bouton lance une nouvelle activité
        // Lorsque le bouton Play est cliqué, l'activité PlayActivity est lancée
        btnPlay.setOnClickListener(v -> {
            Log.d(getString(R.string.mainActivity_debug), "Play button clicked");
            Intent intent = new Intent(MainActivity.this, PlayActivity.class);
            startActivity(intent);
        });
        // Lorsque le bouton Locate est cliqué, l'activité LocateActivity est lancée
        btnLocate.setOnClickListener(v -> {
            Log.d(getString(R.string.mainActivity_debug), "Locate button clicked");
            Intent intent = new Intent(MainActivity.this, LocateActivity.class);
            startActivity(intent);
        });

        // Lorsque le bouton Scores est cliqué, l'activité ScoresActivity est lancée
        btnScores.setOnClickListener(v -> {
            Log.d(getString(R.string.mainActivity_debug), "Scores button clicked");
            Intent intent = new Intent(MainActivity.this, ScoresActivity.class);
            startActivity(intent);
        });

        // Lorsque le bouton Info est cliqué, l'activité InfoActivity est lancée
        btnInfo.setOnClickListener(v -> {
            Log.d(getString(R.string.mainActivity_debug), "Info button clicked");
            Intent intent = new Intent(MainActivity.this, InfoActivity.class);
            startActivity(intent);
        });

        // Lorsque le bouton Config est cliqué, l'activité ConfigActivity est lancée
        btnConfig.setOnClickListener(v -> {
            Log.d(getString(R.string.mainActivity_debug), "Config button clicked");
            Intent intent = new Intent(MainActivity.this, ConfigActivity.class);
            startActivity(intent);
        });
    }
}