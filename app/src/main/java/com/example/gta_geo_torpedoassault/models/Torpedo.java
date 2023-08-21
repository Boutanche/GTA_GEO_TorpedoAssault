package com.example.gta_geo_torpedoassault.models;

import com.google.android.gms.maps.model.LatLng;

public class Torpedo implements GameObject{

    private TorpedoType type;

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
     * @return le type de l'objet du jeu sous forme de chaîne de caractères.
     */
    @Override
    public String getType() {
        return this.type.toString();
    }

    /**
     * Méthode qui permet donner un type à une torpille.
     * @param type le type de la torpille.
     *             Les types de torpilles sont définis dans l'énumération TorpedoType.
     */
    public void setType(TorpedoType type) {
        this.type = type;
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
        // TODO
    }

    /**
     * @param v
     */
    @Override
    public void setXCarte(float v) {

    }

    /**
     * @param v
     */
    @Override
    public void setYCarte(float v) {

    }

    /**
     * @param v
     */
    @Override
    public void setDirection(float v) {

    }

    /**
     * @return
     */
    @Override
    public float getXCarte() {
        return 0;
    }

    /**
     * @return
     */
    @Override
    public float getYCarte() {
        return 0;
    }
}
