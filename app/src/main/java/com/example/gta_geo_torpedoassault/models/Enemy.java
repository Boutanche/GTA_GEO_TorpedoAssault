package com.example.gta_geo_torpedoassault.models;

import com.google.android.gms.maps.model.LatLng;

public class Enemy implements GameObject{

    /**
     * La position géographique de l'ennemi.
     */
    private LatLng positionPGS;

    /**
     * La latitude de l'ennemi sur la carte de jeu.
     */
    private double latitudeCarte;

    /**
     * La longitude de l'ennemi sur la carte de jeu.
     */
    private double longitudeCarte;

    /**
     * La position x de l'ennemi sur la carte de jeu.
     */
    private float xCarte;

    /**
     * La position y de l'ennemi sur la carte de jeu.
     */
    private float yCarte;

    /**
     * La distance entre le joueur et l'ennemi.
     */
    private float distance;

    /**
     * La direction de l'ennemi.
     */
    private float direction;

    /**
     * La vitesse de l'ennemi.
     */
    private int vitesse;

    /**
     * Méthode qui permet de récupérer la position d'un objet du jeu.
     *
     * @return la position de l'objet du jeu.
     */
    @Override
    public LatLng getPosition() {
        return new LatLng(latitudeCarte, longitudeCarte);
    }

    /**
     * Méthode qui permet de récupérer la vitesse d'un objet du jeu.
     *
     * @return la vitesse de l'objet du jeu.
     */
    @Override
    public double getSpeed() {

        return vitesse;
    }

    /**
     * Méthode qui permet de récupérer la direction d'un objet du jeu.
     *
     * @return la direction de l'objet du jeu.
     */
    @Override
    public float getDirection() {

        return direction;
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
        this.latitudeCarte = (float) latitude;
        this.longitudeCarte = (float) longitude;
    }

    /**
     * Méthode qui permet de récupérer la position en x de l'ennemi.
     * @return x la position en x de l'ennemi.
     */
    public float getXCarte() {

        return this.xCarte;
    }

    /**
     * Méthode qui permet de donner la position en y de l'ennemi.
     * @param xCarte la position en x de l'ennemi.
     */
    public void setXCarte(float xCarte) {

        this.xCarte = xCarte;
    }

    /**
     * Méthode qui permet de récupérer la position en y de l'ennemi.
     * @return la position en y de l'ennemi.
     */
    public float getYCarte() {

        return this.yCarte;
    }

    /**
     * Méthode qui permet de donner la position en y de l'ennemi.
     * @param yCarte la position en y de l'ennemi.
     */
    public void setYCarte(float yCarte) {

        this.yCarte = yCarte;
    }

    /**
     * Méthode qui permet de récupérer la distance d'un objet du jeu.
     *
     * @return la distance de l'objet du jeu.
     */
    @Override
    public double getDistance() {

        return distance;
    }

    /**
     * Méthode qui permet de récupérer la distance entre le joueur et l'ennemi.
     * @param distance la distance entre le joueur et l'ennemi.
     */
    public void setDistance(float distance) {

        this.distance = distance;
    }

    /**
     * Méthode qui permet de récupérer la direction de l'ennemi.
     * @return la direction de l'ennemi.
     */
    public void setDirection(float direction) {
        this.direction = direction;
    }

    public void setSpeed(int vitesse) {
        this.vitesse = vitesse;
    }
}
