package com.github.nuuttiniiranen.projektigithubiin;

import java.io.Serializable;

/**
 * Luokka, josta luodaan paku-olioita. Erona autot olioon, pakulla on tavaratilan tilavuus kenttä
 */

public class Paku extends Auto implements Serializable {
    /**
     * Pakun toString metodi
     * @return palauttaa pakun tiedot luettavassa muodossa
     */
    public String toString(){
        return ("Auton rekisterinumero on: " + this.rekisterinumero + "\nAuton mittarilukema on: " + this.mittarilukema +
                " km\nAuton vuosimalli on: " + this.vuosimalli + "\nAuton merkki ja malli on: " + this.merkki + " " + this.malli +
                "\nAuton huoltoväli on: " + this.huoltovali + "\nJa kilometrejä seuraavaan huoltoon on: " + this.edellinenHuolto +
                "\nAuton käyttövoima on: " + this.kayttoVoima + "\nAutoon mahtuu " + this.matkustajienLukumaara + " matkustajaa" +
                "\nTavaratilan tilavuus on: " + this.tilavuusLitroina + " litraa" + "\nauto on vuokrattavissa: " + autoVuokrattavissa);
    }

    /**
     @param rekisterinumero uuden ajoneuvon rekkari
      * @param mittarilukema uuden ajoneuvon mittarilukema
     * @param vuosimalli uuden ajoneuvon vuosimalli
     * @param merkki uuden ajoneuvon merkki
     * @param malli uuden ajoneuvon malli
     * @param huoltovali uuden ajoneuvon huoltoväli
     * @param edellinenHuolto uuden ajoneuvon edellisen huollon mittarilukema
     * @param kayttoVoima uuden ajoneuvon käyttövoima
     * @param matkustajienLukumaara uuteen ajoneuvoon mahtuvien matkustajien lkm mukaanlukien kuljettaja
     * @param tilavuusLitroina pakettiauton tavaratilan litratilavuus
     */
    public Paku(String rekisterinumero, int mittarilukema, int vuosimalli, String merkki, String malli, int huoltovali,
                int edellinenHuolto, String kayttoVoima, int matkustajienLukumaara, int tilavuusLitroina) {
        super(rekisterinumero, mittarilukema, vuosimalli, merkki, malli, huoltovali, edellinenHuolto, kayttoVoima, matkustajienLukumaara);
        this.tilavuusLitroina = tilavuusLitroina;
    }

    /**
     * testiohjelma paku luokan toimivuutta varten, vain testauskäyttöön
     * @param args
     */
    public static void main(String[] args) {
        Paku paku = new Paku("5", 5, 5, "5", "5", 5, 5, "5", 5, 5);
        System.out.println(paku);
    }
}
