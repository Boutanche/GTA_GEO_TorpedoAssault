package com.example.gta_geo_torpedoassault.adapters;

import static com.example.gta_geo_torpedoassault.activities.PlayActivity.gameService;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gta_geo_torpedoassault.R;
import com.example.gta_geo_torpedoassault.models.Torpedo;

import java.util.List;

/**
 * Adapter pour la liste des torpilles.
 */
public class TorpedoAdapter extends ArrayAdapter<Torpedo> {

    /**
     * Contexte de l'application.
     */
    private Context context;

    /**
     * Liste des torpilles.
     */
    private List<Torpedo> torpedoes;

    /**
     * Constructeur de la classe.
     *
     * @param context    contexte de l'application.
     * @param torpedoes  liste des torpilles.
     */
    public TorpedoAdapter(Context context, List<Torpedo> torpedoes) {
        super(context, R.layout.list_item_torpedo, torpedoes);
        this.context = context;
        this.torpedoes = torpedoes;
    }

    /**
     * Méthode qui permet de mettre à jour la liste des torpilles.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int loadedTorpedosCount = gameService.getLoadedTorpedosCount();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_torpedo, parent, false);
        }

        TextView torpedoTypeTextView = convertView.findViewById(R.id.torpedoTypeTextView);
        Button loadTorpedoButton = convertView.findViewById(R.id.loadTorpedoButton);
        Torpedo currentTorpedo = torpedoes.get(position);
        torpedoTypeTextView.setText(currentTorpedo.getType());

        // Si la torpille est déjà chargée
        if (gameService.isTorpedoLoaded(currentTorpedo)) {
            loadTorpedoButton.setEnabled(false);
            loadTorpedoButton.setText("Chargée"); // Modifier le texte du bouton pour indiquer que la torpille est déjà chargée.
        }
        // Si on a atteint le maximum de torpilles chargées
        else if (loadedTorpedosCount >= 3) {
            loadTorpedoButton.setEnabled(false);
            loadTorpedoButton.setText("Max atteint");
        }
        // Si la torpille est disponible pour le chargement
        else {
            loadTorpedoButton.setEnabled(true);
            loadTorpedoButton.setText("Charger");
            loadTorpedoButton.setOnClickListener(v -> {
                gameService.incrementLoadedTorpedosCount();
                gameService.loadTorpedo(currentTorpedo);
                notifyDataSetChanged();
            });
        }
        return convertView;
    }
}