package com.example.gta_geo_torpedoassault.activities.player_mode;

import android.animation.ValueAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gta_geo_torpedoassault.R;
import com.example.gta_geo_torpedoassault.activities.PlayActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

/**
 * ## HunterActivity ##
 * Activité qui permet de jouer en mode Chasse :
 * ### Portrait ###
 * - Poste de tir
 * -- Le joueur doit tirer sur les ennemis.
 * --- Il appuyant sur le button "Tirer".
 * --- On affiche un pourcentage de réussite : lié à la cible verrouillée.
 * ### Paysage ###
 * - Passerelle d'observation
 * - Le joueur peut collecter des informations sur les ennemis.
 * -- Type, Vitesse, Direction, Distance
 * -- Il peut aussi verrouiller une cible.
 * Un bouton permet de passer en mode Management.
 */
public class HunterActivity extends AppCompatActivity {

    /**
     * Bouton qui passe en mode management.
     */
    Button managerButton;

    /**
     * Bouton qui permet de tirer.
     */
    Button fireButton;

    /**
     * Titre de l'activité en mode paysage.
     */
    TextView titlePaysage;

    /**
     * Titre de l'activité en mode portrait.
     */
    TextView titlePortrait;

    /**
     * Texte qui affiche l'azimut.
     */
    TextView azimutView;

    /**
     * SensorManager qui permet de récupérer les valeurs des capteurs.
     */
    SensorManager mSensorManager;

    /**
     * Capteur accelerometer.
     */
    Sensor mAccelerometer;

    /**
     * Capteur magnetometer.
     */
    Sensor mMagnetometer;

    /**
     * Valeur de l'azimut.
     */
    Float azimut;

    /**
     * Texte qui affiche une direction.
     */
    String direction;

    /**
     * Lissage des valeurs pour la jouabilité.
     */
    private static final int SMOOTHING_WINDOW_SIZE = 50;

    /**
     * File qui permet de lisser les valeurs.
     */
    private final Queue<Float> mSmoothingQueue = new LinkedList<>();

    /**
     * Lorsque l'activité est créée,
     * @param savedInstanceState état de l'activité sauvegardée
     */
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (getResources().getConfiguration().orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(com.example.gta_geo_torpedoassault.R.layout.activity_hunter_landscape);
            setupLandScapeUI();
        } else {
            setContentView(com.example.gta_geo_torpedoassault.R.layout.activity_hunter_portrait);
            setupPortraitUI();
        }
        this.managerButton = findViewById(com.example.gta_geo_torpedoassault.R.id.manager_button);
        managerButton.setOnClickListener(v -> {
            android.util.Log.d(getString(com.example.gta_geo_torpedoassault.R.string.hunterActivity_debug), "Manager button clicked");
            android.content.Intent intent = new android.content.Intent(HunterActivity.this, ManagerActivity.class);
            startActivity(intent);
        });

        this.fireButton = findViewById(com.example.gta_geo_torpedoassault.R.id.fire_button);
        fireButton.setOnClickListener(v -> {
            Log.d(getString(R.string.hunterActivity_debug), "Fire button clicked");
            // Controller le nombre de torpilles chargées
            if (PlayActivity.gameService.getLoadedTorpedosCount() == 0) {
                android.widget.Toast.makeText(HunterActivity.this, "Plus de torpilles !", android.widget.Toast.LENGTH_SHORT).show();
                return;
            } else if (PlayActivity.gameService.getLoadedTorpedosCount() > 0) {
                android.widget.Toast.makeText(HunterActivity.this, "Tir !", android.widget.Toast.LENGTH_SHORT).show();
                PlayActivity.gameService.decrementLoadedTorpedosCount();
                PlayActivity.gameService.removeFiredTorpedo();
            } else {
                android.widget.Toast.makeText(HunterActivity.this, "Erreur !", android.widget.Toast.LENGTH_SHORT).show();
                return;
            }

            if (isPlayerPointingAtTarget()) {
                Log.d(getString(R.string.hunterActivity_debug), "Player is pointing at target");
                android.widget.Toast.makeText(HunterActivity.this, "Touché !", android.widget.Toast.LENGTH_SHORT).show();
                PlayActivity.gameService.setScore(100);
            } else {
                Log.d(getString(R.string.hunterActivity_debug), "Player is not pointing at target");
                android.widget.Toast.makeText(HunterActivity.this, "Dans le vide !", android.widget.Toast.LENGTH_SHORT).show();
                PlayActivity.gameService.setScore(-50);
            }
        });
    }

    /**
     * SensorEventListener qui permet de récupérer les valeurs des capteurs.
     */
    private final SensorEventListener mSensorListener = new SensorEventListener() {

        // Valeurs du capteur accelerometer
        float[] mGravity;

        // Valeurs du capteur magnetometer
        float[] mGeomagnetic;

        /**
         * Lorsque la précision du capteur change, on ne fait rien
         */
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Ne rien faire
        }
        /**
         * Lorsque les valeurs du capteur changent, on récupère les valeurs
         * et on les affiche dans les TextView
         */
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                mGravity = event.values;
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                mGeomagnetic = event.values;
            if (mGravity != null && mGeomagnetic != null) {
                // Récupérer l'azimut en radians
                float[] radian = new float[9];
                // Récupérer l'angle d'inclinaison par rapport à l'horizontale
                float[] inclinaison = new float[9];
                boolean success = SensorManager.getRotationMatrix(radian, inclinaison, mGravity, mGeomagnetic);

                // Si la matrice de rotation a été calculée avec succès
                if (success) {
                    float[] orientation = new float[3];
                    SensorManager.getOrientation(radian, orientation);
                    // Récupérer l'azimut en degrés
                    azimut = (float)Math.toDegrees(orientation[0]);

                    mSmoothingQueue.add(azimut);
                    if (mSmoothingQueue.size() > SMOOTHING_WINDOW_SIZE) {
                        mSmoothingQueue.remove();
                    }

                    float sum = 0;
                    for (float value : mSmoothingQueue) {
                        sum += value;
                    }
                    azimut = sum / mSmoothingQueue.size();

                    // Formatage de l'azimut
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                    DecimalFormat format = new DecimalFormat("#.##", symbols);
                    String formattedAzimut = format.format(azimut);

                    // Récupérer la direction
                    if (azimut >= -22.5 && azimut < 22.5) {
                        direction = "Nord";
                    } else if (azimut >= 22.5 && azimut < 67.5) {
                        direction = "Nord-Est";
                    } else if (azimut >= 67.5 && azimut < 112.5) {
                        direction = "Est";
                    } else if (azimut >= 112.5 && azimut < 157.5) {
                        direction = "Sud-Est";
                    } else if (azimut >= 157.5 || azimut < -157.5) {
                        direction = "Sud";
                    } else if (azimut >= -157.5 && azimut < -112.5) {
                        direction = "Sud-Ouest";
                    } else if (azimut >= -112.5 && azimut < -67.5) {
                        direction = "Ouest";
                    } else if (azimut >= -67.5 && azimut < -22.5) {
                        direction = "Nord-Ouest";
                    }
                    updateAzimutView(formattedAzimut);
                }
            }
        }
    };

    /**
     * Lorsque azimut est mis à jour, on met à jour la vue
     */
    private void updateAzimutView(String formattedAzimut) {
        if (azimutView != null) {
            String oldAzimut = azimutView.getText().toString();
            String[] parts = oldAzimut.split(",");
            oldAzimut = parts[0].replace("Azimut: ",""); // extract the numeric part

            // Create a new ValueAnimator that will animate from the old azimuth value to the new azimuth value
            ValueAnimator animator = ValueAnimator.ofFloat(Float.parseFloat(oldAzimut), Float.parseFloat(formattedAzimut));
            animator.setDuration(500); // Set the duration of the animation to 500ms

            // Set an update listener on the animator to update the azimuth view's text during the animation
            animator.addUpdateListener(animation -> {
                float animatedValue = (float) animation.getAnimatedValue();

                // Format the animated value to two decimal places and set it as the azimuth view's text
                azimutView.setText(String.format("Azimut: %.2f : Direction: %s", azimut, direction));
            });

            // Start the animation
            animator.start();
        }
    }

    /**
     * Met en place l'interface utilisateur en mode paysage
     */
    private void setupLandScapeUI() {
        titlePaysage = findViewById(R.id.textView_title_paysage);
        titlePaysage.setText(R.string.passerelle_d_observation);
        fireButton = findViewById(R.id.fire_button);
        fireButton.setText(R.string.fire_button_landscape);
        azimutView = findViewById(R.id.azimut_view);
        azimutView.setText(String.format("Azimut: 0, Direction: Nord"));
        if (azimut != null) {
            DecimalFormat format = new DecimalFormat("#.##"); // create a DecimalFormat
            String formattedAzimut = format.format(azimut); // format the azimut
            updateAzimutView(formattedAzimut);
        } else {
            azimutView.setText(String.format("Azimut: 0, Direction: Nord"));
        }
    }

    /**
     * Met en place l'interface utilisateur en mode portrait
     */
    private void setupPortraitUI() {
        fireButton = findViewById(R.id.fire_button);
        fireButton.setText(R.string.fire_button);
        titlePortrait = findViewById(R.id.textView_title_portrait);
        titlePortrait.setText(R.string.poste_de_tir);
        azimutView = findViewById(R.id.azimut_view);
        azimutView.setText(String.format("Azimut: 0, Direction: Nord"));
        if (azimut != null) {
            DecimalFormat format = new DecimalFormat("#.##"); // create a DecimalFormat
            String formattedAzimut = format.format(azimut); // format the azimut
            updateAzimutView(formattedAzimut);
        } else {
            azimutView.setText(String.format("Azimut: 0, Direction: Nord"));
        }
    }

    /**
     * Lorsque l'activité reprend, on réenregistre les listeners
     */
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(mSensorListener, mMagnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * Lorsque l'activité est mise en pause, on arrête les listeners
     */
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorListener);
    }

    /**
     * Lorsque l'activité est détruite, on arrête le service de localisation
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // stopService(new Intent(this, LocationService.class));
    }

    /**
     * Lorsque l'utilisateur clique sur le bouton "Tirer", on vérifie si le joueur pointe vers une cible
     * Si oui, on affiche un message de réussite, sinon, on affiche un message d'échec
     */
    private boolean isPlayerPointingAtTarget() {
        // Logique pour déterminer sir le joueur pointe vers une cible

        if (azimut == null) {
            return false;
        } else if (azimut >= 0 && azimut < 10) {
            return true;
        } else {
            return false;
        }
    }
}