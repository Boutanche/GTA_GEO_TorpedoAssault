package com.example.gta_geo_torpedoassault.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gta_geo_torpedoassault.R;
import com.example.gta_geo_torpedoassault.adapters.ScoreAdapter;
import com.example.gta_geo_torpedoassault.dbHelper.ScoreDbHelper;
import com.example.gta_geo_torpedoassault.models.Score;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScoresActivity extends AppCompatActivity {
    private ListView scoresListView;
    private ScoreDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        dbHelper = new ScoreDbHelper(this);
        scoresListView = findViewById(R.id.scoresListView);

        List<Score> scores = getScores();

        ScoreAdapter scoreAdapter = new ScoreAdapter(this, scores);
        scoresListView.setAdapter(scoreAdapter);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    private List<Score> getScores() {
        return dbHelper.getAllScores();
    }
}