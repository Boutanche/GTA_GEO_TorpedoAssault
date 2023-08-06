package com.example.gta_geo_torpedoassault.activities.player_mode;

import android.animation.ValueAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gta_geo_torpedoassault.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

public class HunterActivity extends AppCompatActivity {
    Button managerButton;
    TextView titlePaysage;
    TextView titlePortrait;
    TextView azimutView;

    SensorManager mSensorManager;
    Sensor mAccelerometer;
    Sensor mMagnetometer;

    Float azimut;
    String direction;

    private static final int SMOOTHING_WINDOW_SIZE = 50;

    private final Queue<Float> mSmoothingQueue = new LinkedList<>();

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
    }
    private final SensorEventListener mSensorListener = new SensorEventListener() {
        float[] mGravity;
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
                float[] R = new float[9];
                // Récupérer l'angle d'inclinaison par rapport à l'horizontale
                float[] I = new float[9];
                boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);

                // Si la matrice de rotation a été calculée avec succès
                if (success) {
                    float[] orientation = new float[3];
                    SensorManager.getOrientation(R, orientation);
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

                    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US); // Use US locale to ensure dot is used as decimal separator
                    DecimalFormat format = new DecimalFormat("#.##", symbols); // create a DecimalFormat
                    String formattedAzimut = format.format(azimut); // format the azimut

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

    private void setupLandScapeUI() {
        titlePaysage = findViewById(R.id.textView_title_paysage);
        titlePaysage.setText(R.string.passerelle_d_observation);
    }

    private void setupPortraitUI() {
        titlePortrait = findViewById(R.id.textView_title_portrait);
        titlePortrait.setText(R.string.poste_de_tir);
        azimutView = findViewById(R.id.azimut_view);
        if (azimut != null) {
            DecimalFormat format = new DecimalFormat("#.##"); // create a DecimalFormat
            String formattedAzimut = format.format(azimut); // format the azimut
            updateAzimutView(formattedAzimut);
        } else {
            azimutView.setText(String.format("Azimut: 0, Direction: Nord"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(mSensorListener, mMagnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorListener);
    }
}