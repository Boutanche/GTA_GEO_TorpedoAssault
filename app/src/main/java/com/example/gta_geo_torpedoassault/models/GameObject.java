package com.example.gta_geo_torpedoassault.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Interface utilisée pour les objets du jeu
 * Les objets du jeu sont les ennemis, les torpilles, les mines, les bonus, les malus.
 */
public interface GameObject {

    /**
     * Méthode qui permet de récupérer la position d'un objet du jeu.
     * @return la position de l'objet du jeu.
     */
    LatLng getPosition();

    /**
     * Méthode qui permet de récupérer la position x du joueur sur la carte de jeu.
     * @return la position du joueur sur la carte de jeu.
     */
    float getXCarte();

    /**
     * Méthode qui permet de récupérer la position y du joueur sur la carte de jeu.
     * @return la position du joueur sur la carte de jeu.
     */
    float getYCarte();

    /**
     * Méthode qui permet de récupérer la vitesse d'un objet du jeu.
     * @return la vitesse de l'objet du jeu.
     */
    double getSpeed();

    /**
     * Méthode qui permet de récupérer la direction d'un objet du jeu.
     * @return la direction de l'objet du jeu.
     */
    float getDirection();

    /**
     * Méthode qui permet de récupérer la distance d'un objet du jeu.
     * @return la distance de l'objet du jeu.
     */
    double getDistance();

    /**
     * Méthode qui permet de récupérer la taille d'un objet du jeu.
     * @return la taille de l'objet du jeu.
     */
    float getSize();

    /**
     * Méthode qui permet de récupérer la vie d'un objet du jeu.
     * @return la vie de l'objet du jeu.
     */
    int getLife();

    /**
     * Méthode qui permet de récupérer le score d'un objet du jeu.
     * @return le score de l'objet du jeu.
     */
    int getScore();

    /**
     * Méthode qui permet de récupérer le type d'un objet du jeu.
     * @return le type de l'objet du jeu.
     */
    String getType();

    /**
     * Méthode qui permet de récupérer le nom d'un objet du jeu.
     * @return le nom de l'objet du jeu.
     */
    String getName();

    /**
     * Méthode qui permet de positionner un objet en jeu.
     * @param latitude la latitude de l'objet du jeu.
     * @param longitude la longitude de l'objet du jeu.
     */
    void setPosition(double latitude, double longitude);

    /**
     * Méthode qui permet de positionner un objet en jeu.
     * @param xCarte la position x de l'objet du jeu sur la carte.
     */
    void setXCarte(float xCarte);

    /**
     * Méthode qui permet de positionner un objet en jeu.
     * @param yCarte la position y de l'objet du jeu sur la carte.
     */
    void setYCarte(float yCarte);

    /**
     * Méthode qui permet de positionner un objet en jeu.
     * @param azimut la vitesse de l'objet du jeu.
     */
    void setDirection(float azimut);

}
