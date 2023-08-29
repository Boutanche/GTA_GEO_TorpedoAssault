package com.example.gta_geo_torpedoassault.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gta_geo_torpedoassault.R;
import com.example.gta_geo_torpedoassault.models.Score;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScoreAdapter extends ArrayAdapter<Score> {

    private List<Score> scores;
    private Context context;

    public ScoreAdapter(Context context, List<Score> scores) {
        super(context, -1, scores);
        this.context = context;
        this.scores = scores;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_score, parent, false);
        }
        Score currentScore = scores.get(position);

        TextView scoreTextView = convertView.findViewById(R.id.scoreTextView);
        scoreTextView.setText(String.valueOf(currentScore.getScore()));

        TextView dateTextView = convertView.findViewById(R.id.dateTextView);
        // converti la date en un format plus lisible
        String dateString = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(currentScore.getDate()));
        dateTextView.setText(dateString);

        return convertView;
    }
}
