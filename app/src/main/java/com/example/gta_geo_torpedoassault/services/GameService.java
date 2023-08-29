package com.example.gta_geo_torpedoassault.services;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gta_geo_torpedoassault.dbHelper.ScoreDbHelper;
import com.example.gta_geo_torpedoassault.models.BonusItem;
import com.example.gta_geo_torpedoassault.models.Enemy;
import com.example.gta_geo_torpedoassault.models.GameObject;
import com.example.gta_geo_torpedoassault.models.Player;
import com.example.gta_geo_torpedoassault.contracts.ScoreContract;
import com.example.gta_geo_torpedoassault.models.Torpedo;
import com.example.gta_geo_torpedoassault.models.TorpedoType;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe contient la logique du jeu.
 * Elle est appelée par le moteur de jeu à chaque tick.
 */
public class GameService {

    private static final String TAG = "Game Service Error :";
    /**
     * Liste des objets du jeu.
     */
    private List<GameObject> gameObjects;

    /**
     * Liste des ennemis du jeu.
     */
    private List<Enemy> enemies;

    /**
     * Liste des torpilles du jeu.
     */
    private List<Torpedo> torpedos;

    /**
     * Liste des torpilles dans le sous marin.
     */
    private List<Torpedo> retrievedTorpedos;

    /**
     * Liste des torpilles chargées dans les tubes.
     */
    private List<Torpedo> loadedTorpedos;

    /**
     * Liste des bonus du jeu.
     */
    private List<BonusItem> bonuses;

    /**
     * Joueur.
     */
    private GameObject player;

    /**
     * Nombre de torpilles chargées.
     */
    private Integer loadedTorpedosCount;

    /**
     * Instance de la classe.
     */
    private static GameService instance;

    public Integer score;
    public Long dateTime;
    private static final String PREFS_NAME = "GTA_GEO_prefFile";
    private static final String SCORE_KEY = "Score";
    private static final String DATE_KEY = "Date";
    private Context context;

    /**
     * Constructeur de la classe.
     */
    private GameService(Context context) {
        player = new Player();
        gameObjects = new ArrayList<>();
        torpedos = new ArrayList<>();
        retrievedTorpedos = new ArrayList<>();
        loadedTorpedos = new ArrayList<>();
        enemies = new ArrayList<>();
        loadedTorpedosCount = 0;
        score = 0;
        this.context = context;
        initializeGameObjects();
    }

    /**
     * Méthode qui permet de récupérer l'instance de la classe.
     * @return L'instance de la classe.
     */
    public static synchronized GameService getInstance(Context applicationContext) {
        if (instance == null) {
            instance = new GameService(applicationContext);
        }
        return instance;
    }

    /**
     * Méthode qui permet d'ajouter un objet au jeu.'
     */
    public void addObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    /**
     * Méthode qui permet de supprimer un objet du jeu.
     */
    public void removeObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    /**
     * Méthode qui permet de déplacer un objet du jeu.
     * @param gameObject L'objet à déplacer.
     * @param direction L'azimut de l'objet.
     * @param speed La vitesse de l'objet.
     */
    public void moveObject(GameObject gameObject, float direction, double speed) {
        // Implémentez la logique pour déplacer l'objet ici.
    }

    /**
     * Méthode qui permet de lancer une torpille.
     * @param torpedo La torpille à lancer.
     */
    public void launchTorpedo(Torpedo torpedo) {
        // Ajoutez la logique pour lancer une torpille ici.
    }

    /**
     * Méthode qui permet de faire la mise à jour de la position des ennemis.
     */
    public void updateEnemyPositions() {
        // Parcourez la liste des objets de jeu, et pour chaque ennemi, mettez à jour sa position.
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Enemy) {
                // Mettez à jour la position de l'ennemi en fonction de son azimut et de sa vitesse.
                moveObject(gameObject, gameObject.getDirection(), gameObject.getSpeed());
            }
        }
    }

    /**
     * Méthode qui permet de faire la mise à jour de la position des objets de jeu.
     */
    public void updateGameObjects() {
        // Cette méthode est appelée à chaque tick du jeu pour mettre à jour tous les objets de jeu.
        // Vous pouvez ajouter d'autres méthodes de mise à jour ici, par exemple pour les torpilles, les bonus, etc.
        updateEnemyPositions();
        updatePlayerPosition();
    }

    private void updatePlayerPosition() {
        // Mettez à jour la position du joueur en fonction de son azimut et de sa vitesse.
        moveObject(player, player.getDirection(), player.getSpeed());
    }

    /**
     * Méthode qui permet de vérifier les collisions entre les objets de jeu.
     */
    public void checkCollisions() {
        // Vérifiez ici si une torpille a touché un ennemi ou un autre objet de jeu.
        // Si c'est le cas, enlevez les objets touchés de la liste des objets de jeu.
    }

    /**
     * Méthode qui permet de mettre à jour le jeu.
     */
    public void tick() {
        // Cette méthode est appelée à chaque tick du jeu.
        // Ici, vous pouvez mettre à jour tous les objets de jeu et vérifier les collisions.
        updateGameObjects();
        checkCollisions();
    }

    /**
     * Méthode qui permet de démarrer le jeu.
     */
    private void firstEnemyInitialize() {
        // Cette méthode est appelée lorsque le jeu démarre.
        Enemy firstEnemy = new Enemy();
        // L'azimuth de l'ennemi est égal à celui du joueur.
        firstEnemy.setDirection(player.getDirection());
        // L'ennemi est à 100m devant le joueur sur la carte.
        firstEnemy.setXCarte(player.getXCarte());
        firstEnemy.setYCarte(player.getYCarte()+100);
        // L'ennemi est immobile.
        firstEnemy.setSpeed(0);
        // Ajoutez l'ennemi à la liste des ennemis.
        enemies.add(firstEnemy);
        // Ajoutez l'ennemi à la liste des objets de jeu.
        addObject(firstEnemy);
    }
    
    /**
     * Méthode qui permet d'initialiser les objets de jeu.
     */
    private void initializeGameObjects() {
        // Ajoutez ici les objets de jeu.
        // Par exemple, vous pouvez ajouter un ennemi, un bonus, etc.
        playerInitialize();
        firstEnemyInitialize();
        initializeTorpedos();
    }

    /**
     * Méthode qui permet d'initialiser les torpilles.
     */
    public void initializeTorpedos() {
        // Ajoutez ici les torpilles.
        // Par exemple, vous pouvez ajouter une torpille de type BASIC, FAST, SLOW, BIG, SMALL, etc.
        for (int i = 0; i < 5; i++) {
            Torpedo basicTorpedo = new Torpedo();
            basicTorpedo.setType(TorpedoType.BASIC);
            retrievedTorpedos.add(basicTorpedo);
            setScore(1);
        }
        Torpedo otherTorpedo = new Torpedo();
        otherTorpedo.setType(TorpedoType.FAST);
        retrievedTorpedos.add(otherTorpedo);
    }

    /**
     * Méthode qui permet de récupérer la liste des torpilles.
     * @return
     */
    public List<Torpedo> getRetrievedTorpedos() {
        return retrievedTorpedos;
    }

    /**
     * Méthode pour initialiser le joueur.
     */
    private void playerInitialize() {
        // TODO Utiliser les coordonnées GPS :
        // Récupérer la latitude et longitude du joueur.
        player.setPosition(0, 0);
        // Donner une position au centre de la carte.
        player.setXCarte(0.0f);
        player.setYCarte(0.0f);
        player.setDirection(0.0f);
        gameObjects.add(player);

    }

    /**
     * Méthode qui permet de récupérer la liste des objets de jeu.
     * @return La liste des objets de jeu.
     */
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    /**
     * Méthode qui permet de charger une torpille.
     * @param torpedo La torpille à charger.
     */
    public void loadTorpedo(Torpedo torpedo) {
        if (!loadedTorpedos.contains(torpedo) && retrievedTorpedos.contains(torpedo)) {
            loadedTorpedos.add(torpedo);
        }
    }

    /**
     * Méthode qui permet de savoir si une torpille est chargée.
     * @param torpedo La torpille à vérifier.
     * @return true si la torpille est chargée, false sinon.
     */
    public boolean isTorpedoLoaded(Torpedo torpedo) {
        return loadedTorpedos.contains(torpedo);
    }

    /**
     * Méthode qui permet de récupérer le nombre de torpilles chargées.
     * @return Le nombre de torpilles chargées.
     */
    public Integer getLoadedTorpedosCount() {
        return loadedTorpedosCount;
    }

    /**
     * Méthode qui permet d'incrémenter le nombre de torpilles chargées.
     */
    public void incrementLoadedTorpedosCount() {
        loadedTorpedosCount++;
    }

    /**
     * Méthode qui permet de décrémenter le nombre de torpilles chargées.
     */
    public void decrementLoadedTorpedosCount() {
        loadedTorpedosCount--;
    }

    /**
     * Méthode qui permet de retirer la torpille tirée des listes de torpilles.
     */
    public void removeFiredTorpedo() {
        loadedTorpedos.remove(0);
        retrievedTorpedos.remove(0);
    }

    /**
     * Méthode qui permet de réinitialiser le nombre de torpilles chargées.
     */
    public void resetLoadedTorpedosCount() {
        loadedTorpedosCount = 0;
    }

    /**
     * Méthode qui permet de récupérer la prochaine torpille chargée.
     * @return La prochaine torpille chargée.
     */
    public Torpedo getNextLoadedTorpedo() {
        if (!this.loadedTorpedos.isEmpty()) {
            return loadedTorpedos.get(0);
        }
        return null;
    }

    /**
     * Méthode pour calculer le score de la partie
     * @param score int nombre de points à ajouter au score précédent.
     */
    public void setScore(int score){
        this.score += score;
    }

    /**
     * Méthode de fin de partie
     */
    public void endGame(Activity context) {
        if (this.context == null) {
            Log.e(TAG, "endGame: Context is null, cannot end game");
            return;
        }
        int finalScore = getFinalScore();

        // Obtenez une instance de ScoreDbHelper
        ScoreDbHelper dbHelper = new ScoreDbHelper(this.context);

        // Obtenez une base de données en écriture
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Créez une nouvelle map de valeurs, où les noms de colonnes sont les clés
        ContentValues values = new ContentValues();
        values.put(ScoreContract.ScoreEntry.COLUMN_NAME_SCORE, finalScore);
        values.put(ScoreContract.ScoreEntry.COLUMN_NAME_DATE, System.currentTimeMillis());

        // Insérez la nouvelle ligne, retournant l'ID de la nouvelle ligne
        long newRowId = db.insert(ScoreContract.ScoreEntry.TABLE_NAME, null, values);
    }

    /**
     * Méthode pour enregistrer le score
     * @param finalScore le score a enregistrer
     */
    private void saveScore(int finalScore) {
        SharedPreferences sharedPreferences = null;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SCORE_KEY, finalScore);
        editor.putLong(DATE_KEY, System.currentTimeMillis());
    }

    /**
     * Méthode de calcul du score final.
     * @return Integer le score final
     */
    private int getFinalScore() {
        return this.score;
    }

}
