package com.github.nuuttiniiranen.projektigithubiin;

import java.io.Serializable;

/**
 * Luokka, josta luodaan Auto-olioita
 */

public class Auto implements Ajoneuvo, Serializable {
    protected String rekisterinumero;
    protected int mittarilukema;
    protected int vuosimalli;
    protected String merkki;
    protected String malli;
    protected int huoltovali;
    protected int edellinenHuolto;
    protected String kayttoVoima;
    protected int matkustajienLukumaara;
    protected int maksimiteho;
    protected boolean autoVuokrattavissa=true;
    protected int tilavuusLitroina;

    /**
     * asettaa ajetut kokonaiskilometrit
     * @param kilometrit arvo, joka asetetaan
     */
    public void setKilometrit(int kilometrit){
        this.mittarilukema=kilometrit;
    }

    /**
     * resetoi huollonilmaisimen
     */
    public void resetoiOljyHuolto(){
        this.edellinenHuolto=this.mittarilukema;
    }

    /**
     * asettaa auton ei vuokrattavaksi
     */
    public void poistaAutoAjosta(){
        this.autoVuokrattavissa=false;
    }

    /**
     * asettaa auton vuokrattavaksi
     */
    public void laitaAutoAjoon(){
        this.autoVuokrattavissa=true;
    }

    /**
     * tarkistaa auton huollontarpeen
     * @return palauttaa true jos seuraava huolto alle 1000km päässä, false muuten
     */
    public boolean tarkistaHuollonTarve(){
        if (mittarilukema-edellinenHuolto>huoltovali-1000){
            this.autoVuokrattavissa=false;
            return true;
        } else return false;
    }

    /**
     * palauttaa auton tiedot järkevänä tekstinä
     * @return teksti, jossa on auton tiedot
     */
    public String toString(){
        return ("Auton rekisterinumero on: " + this.rekisterinumero + "\nAuton mittarilukema on: " + this.mittarilukema +
                " km\nAuton vuosimalli on: " + this.vuosimalli + "\nAuton merkki ja malli on: " + this.merkki + " " + this.malli +
                "\nAuton huoltoväli on: " + this.huoltovali + " km\nJa edellinen huolto tehty kun mittarilukema oli: " + this.edellinenHuolto +
                " km\nJoten seuraavaan huoltoon on " + ((edellinenHuolto+huoltovali) - mittarilukema) + " km"
                + "\nAuton käyttövoima on: " + this.kayttoVoima + "\nAutoon mahtuu " + this.matkustajienLukumaara + " matkustajaa" +
                "\nAuton maksimiteho on: " + maksimiteho + " hv" + "\nauto on vuokrattavissa: " + autoVuokrattavissa);
    }

    /**
     * Auton alustaja
     * @param rekisterinumero uuden ajoneuvon rekkari
     * @param mittarilukema uuden ajoneuvon mittarilukema
     * @param vuosimalli uuden ajoneuvon vuosimalli
     * @param merkki uuden ajoneuvon merkki
     * @param malli uuden ajoneuvon malli
     * @param huoltovali uuden ajoneuvon huoltoväli
     * @param edellinenHuolto uuden ajoneuvon edellisen huollon mittarilukema
     * @param kayttoVoima uuden ajoneuvon käyttövoima
     * @param matkustajienLukumaara uuteen ajoneuvoon mahtuvien matkustajien lkm mukaanlukien kuljettaja
     * @param maksimiteho uuden ajoneuvon maksimiteho
     */
    public Auto(String rekisterinumero, int mittarilukema, int vuosimalli, String merkki, String malli, int huoltovali,
                int edellinenHuolto, String kayttoVoima, int matkustajienLukumaara, int maksimiteho) {
        this.rekisterinumero=rekisterinumero;
        this.mittarilukema = mittarilukema;
        this.vuosimalli = vuosimalli;
        this.merkki = merkki;
        this.malli=malli;
        this.huoltovali = huoltovali;
        this.edellinenHuolto = edellinenHuolto;
        this.kayttoVoima=kayttoVoima;
        this.matkustajienLukumaara=matkustajienLukumaara;
        this.maksimiteho=maksimiteho;
    }

    /**
     * Alustaja autolle jonka tehoa ei tarvita, tätä käytetään pakettiauto luokassa
     * @param rekisterinumero uuden ajoneuvon rekkari
     * @param mittarilukema uuden ajoneuvon mittarilukema
     * @param vuosimalli uuden ajoneuvon vuosimalli
     * @param merkki uuden ajoneuvon merkki
     * @param malli uuden ajoneuvon malli
     * @param huoltovali uuden ajoneuvon huoltoväli
     * @param edellinenHuolto uuden ajoneuvon edellisen huollon mittarilukema
     * @param kayttoVoima uuden ajoneuvon käyttövoima
     * @param matkustajienLukumaara uuteen ajoneuvoon mahtuvien matkustajien lkm mukaanlukien kuljettaja
     */
    public Auto(String rekisterinumero, int mittarilukema, int vuosimalli, String merkki, String malli, int huoltovali,
                int edellinenHuolto, String kayttoVoima, int matkustajienLukumaara) {
        this.rekisterinumero=rekisterinumero;
        this.mittarilukema = mittarilukema;
        this.vuosimalli = vuosimalli;
        this.merkki = merkki;
        this.malli=malli;
        this.huoltovali = huoltovali;
        this.edellinenHuolto = edellinenHuolto;
        this.kayttoVoima=kayttoVoima;
        this.matkustajienLukumaara=matkustajienLukumaara;
    }

    /**
     * Alustaja, jolle annetaan pelkkä rekisterinumero mutta ei muita tietoja
     * @param rekisterinumero uuden ajoneuvon rekisterinumero
     */
    public Auto(String rekisterinumero){
        this.rekisterinumero=rekisterinumero;
    }

    /**
     * Parametriton alustaja autolle. Stringit alustettu johonkin arvoon, jottei ohjelma kaadu
     */
    public Auto(){
        this.rekisterinumero="Ei asetettu";
        this.merkki="Ei asetettu";
        this.malli="Ei asetettu";
        this.kayttoVoima="Ei asetettu";
    }
}
