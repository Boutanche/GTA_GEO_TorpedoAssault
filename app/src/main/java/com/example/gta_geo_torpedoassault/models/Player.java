package com.example.gta_geo_torpedoassault.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Le joueur.
 */
public class Player implements GameObject {

    /**
     * La position du joueur.
     */
    private LatLng positionPGS;

    /**
     * La latitude du joueur sur la carte de jeu.
     */
    private double latitudeCarte;

    /**
     * La longitude du joueur sur la carte de jeu.
     */
    private double longitudeCarte;

    /**
     * La position x du joueur sur la carte de jeu.
     */
    private float xCarte;

    /**
     * La position y du joueur sur la carte de jeu.
     */
    private float yCarte;

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
     * Méthode qui permet de récupérer la position du joueur sur la carte de jeu.
     *
     * @return la position du joueur sur la carte de jeu.
     */
    public float[] getPositionCarte() {
        return new float[]{xCarte, yCarte};
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
     * Méthode qui permet de récupérer la direction du joueur :
     * C'est à dire l'azimut du téléphone.
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
    }

    /**
     * Méthode qui permet de récupérer la position x du joueur sur la carte de jeu.
     *
     * @return la latitude du joueur sur la carte de jeu.
     */
    public float getXCarte() {
        return xCarte;
    }


    /**
     * Méthode qui permet de récupérer la position y du joueur sur la carte de jeu.
     *
     * @return la longitude du joueur sur la carte de jeu.
     */

    public float getYCarte() {
        return yCarte;
    }

    /**
     * Méthode qui permet de donner la position x du joueur sur la carte de jeu.
     *
     * @param xCarte la latitude du joueur sur la carte de jeu.
     */
    public void setXCarte(float xCarte) {
        this.xCarte = xCarte;
    }

    /**
     * Méthode qui permet de donner la position y du joueur sur la carte de jeu.
     *
     * @param yCarte la longitude du joueur sur la carte de jeu.
     */
    public void setYCarte(float yCarte) {
        this.yCarte = yCarte;
    }

    /**
     * @param v
     */
    @Override
    public void setDirection(float v) {

    }
}
