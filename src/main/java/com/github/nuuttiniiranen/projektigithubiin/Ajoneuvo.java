package com.github.nuuttiniiranen.projektigithubiin;

/**
 * Rajapinta erilaisia ajoneuvoja varten
 */
public interface Ajoneuvo {
    /**
     * Asettaa ajoneuvon kilometrit
     * @param kilometrit arvo, joka asetetaan
     */
    void setKilometrit(int kilometrit);

    /**
     * resetoi öljyn huollontarpeen
     */
    void resetoiOljyHuolto();

    /**
     * Asettaa auton ei ajettavaksi
     */
    void poistaAutoAjosta();

    /**
     * Asettaa auton ajettavaksi
     */
    void laitaAutoAjoon();

    /**
     * tarkistaa, onko auton huolto tulossa pian
     * @return palauttaa true jos huolto alle 1000km päässä, false muuten
     */
    boolean tarkistaHuollonTarve();

}
