package com.example.gta_geo_torpedoassault.models;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;

/**
 * Le joueur.
 */
public class Player implements GameObject {

    /**
     * La position du joueur.
     */
    private LatLng positionPGS;

    /**
     * La position x du joueur sur la carte de jeu.
     */
    private double latitudeCarte;

    /**
     * La position y du joueur sur la carte de jeu.
     */
    private double longitudeCarte;

    /**
     * Méthode qui permet de récupérer la position d'un objet du jeu.
     *
     * @return la position de l'objet du jeu.
     */
    @Override
    public LatLng getPosition() {
        return null;
    }

    /**
     * Méthode qui permet de récupérer la vitesse d'un objet du jeu.
     *
     * @return la vitesse de l'objet du jeu.
     */
    @Override
    public double getSpeed() {
        return 0;
    }

    /**
     * Méthode qui permet de récupérer la direction d'un objet du jeu.
     *
     * @return la direction de l'objet du jeu.
     */
    @Override
    public float getDirection() {
        return 0;
    }

    /**
     * Méthode qui permet de récupérer la distance d'un objet du jeu.
     *
     * @return la distance de l'objet du jeu.
     */
    @Override
    public double getDistance() {
        return 0;
    }

    /**
     * Méthode qui permet de récupérer la taille d'un objet du jeu.
     *
     * @return la taille de l'objet du jeu.
     */
    @Override
    public float getSize() {
        return 0;
    }

    /**
     * Méthode qui permet de récupérer la vie d'un objet du jeu.
     *
     * @return la vie de l'objet du jeu.
     */
    @Override
    public int getLife() {
        return 0;
    }

    /**
     * Méthode qui permet de récupérer le score d'un objet du jeu.
     *
     * @return le score de l'objet du jeu.
     */
    @Override
    public int getScore() {
        return 0;
    }

    /**
     * Méthode qui permet de récupérer le type d'un objet du jeu.
     *
     * @return le type de l'objet du jeu.
     */
    @Override
    public String getType() {
        return null;
    }

    /**
     * Méthode qui permet de récupérer le nom d'un objet du jeu.
     *
     * @return le nom de l'objet du jeu.
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * Méthode qui permet de positionner un objet en jeu.
     *
     * @param latitude  la latitude de l'objet du jeu.
     * @param longitude la longitude de l'objet du jeu.
     */
    @Override
    public void setPosition(double latitude, double longitude) {
        this.latitudeCarte = latitude;
        this.longitudeCarte = longitude;
    }

    /**
     * Constructeur de la classe Player.
     */
    public Player() {
        // Centrer le joueur sur la carte
        setPosition(0, 0);
    }
}
