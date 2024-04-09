package com.github.nuuttiniiranen.projektigithubiin;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

/**
 * Aivan liian pitkä luokka, joka händlää käyttöliittymän luomisen, tietojen muokkauksen, tallentamisen, näyttämisen ja paljon muuta
 */

public class UI extends Application implements Serializable {

    File autoTiedosto = new File("Autot.dat");
    String valittuKayttovoima="";
    Button muokkaaTietoja = new Button("Muokkaa auton tietoja");
    Button vastaanotaAutot = new Button("Vastaanota auto");
    Button autonLisays = new Button("Lisää uusi auto");
    Button autoHaku = new Button("Etsi sopiva auto");
    Button naytaAutot = new Button("Näytä autot");
    Button koti = new Button("Koti");
    Button poistu = new Button("Sulje ohjelma");
    private ArrayList<Auto> autoLista = new ArrayList<>();
    private ArrayList<String> rekkariLista = new ArrayList<>();

    /**
     * Aloittaa ohjelman
     * @param primaryStage Stage, jossa käyttöliittymä pyärii
     */
    @Override
    public void start(Stage primaryStage) {
        aloitaOhjelma(primaryStage);
    }

    /**
     * Metodi, joka luo graafisen käyttöliittymän ja aloittaa ohjelman
     * @param primaryStage Stage, jossa käyttöliittymä pyörii
     */
    public void aloitaOhjelma(Stage primaryStage) {
        BorderPane paneeli = new BorderPane();
        if (autoLista.isEmpty()) {
            autoLista = lueTiedot();
        }

        VBox paneeliAlkuvalikolle = luoAlkuValikko(primaryStage);
        paneeli.setBackground(Background.fill(Color.BLUEVIOLET));
        paneeli.setCenter(paneeliAlkuvalikolle);
        paneeli.setLeft(koti);
        Scene scene = new Scene(paneeli, 500, 500);
        primaryStage.setTitle("Auto-ohjelma");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * lukee tiedot tiedostosta lukuTiedosto
     * @return palauttaa luetut tiedot ArrayListinä
     */
    public ArrayList<Auto> lueTiedot() {
        ArrayList<Auto> autoLista = new ArrayList<>();
        if (autoTiedosto.exists()) {
            try (ObjectInputStream lukuTiedosto = new ObjectInputStream(new FileInputStream(autoTiedosto))) {
                while (true) {
                    try {
                        autoLista.add((Auto) lukuTiedosto.readObject());
                    } catch (EOFException e) {
                        break;
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return autoLista;
    }

    /**
     * Palauttaa ohjelman alkutilanteeseen
     * @param primaryStage Stage joka halutaan näyttää.
     */
    public void kotiToiminto(Stage primaryStage){
        aloitaOhjelma(primaryStage);
    }

    /**
     *  Metodi luo alkuvalikon, jota käytetään ohjelman home-screenissä
     * @param primaryStage Stage, jota muokataan kun nappeja painetaan
     * @return palauttaa VBoxin, jossa on napit ohjelman käyttämistä varten.
     */
    public VBox luoAlkuValikko(Stage primaryStage) {
        muokkaaTietoja.setOnAction(e ->{
            tiedonMuokkaus(primaryStage);
        });
        autonLisays.setOnAction(e->{
            autonLisaaminen(primaryStage);
        });
        naytaAutot.setOnAction(e->{
            naytaAutot(primaryStage, autoLista);
        });
        vastaanotaAutot.setOnAction(e->{
            palautaAuto(primaryStage);
        });
        autoHaku.setOnAction(e->{
            etsiSopivaAuto(primaryStage);
        });
        poistu.setOnAction(e->{
            tallennusMetodi();
            System.exit(0);
        });
        VBox paneeliAlkuvalikolle = new VBox(10);
        paneeliAlkuvalikolle.getChildren().addAll(muokkaaTietoja, vastaanotaAutot, autonLisays, autoHaku, naytaAutot, poistu);
        paneeliAlkuvalikolle.setAlignment(Pos.CENTER);

        return paneeliAlkuvalikolle;
    }

    /**
     * Metodilla muokataan olemassa olevan auton tietoja
     * @param primaryStage Stage, johon muokkausikkuna tehdään
     */
    public void tiedonMuokkaus(Stage primaryStage){
        BorderPane paneeliTietojenMuokkaukselle = new BorderPane();

        VBox paneeliAutonTiedoille = new VBox(10);
        paneeliAutonTiedoille.setAlignment(Pos.CENTER);
        Text palautettavaRekkari = new Text("Syötä muokattavan ajoneuvon rekisteritunnus");
        TextField rekkariTF = new TextField();
        rekkariTF.setBackground(Background.fill(Color.WHITE));
        Text uudetKilometrit = new Text("Syötä ajoneuvon mittarilukema");
        TextField kilometriTF = new TextField();
        kilometriTF.setBackground(Background.fill(Color.WHITE));
        Text vuokrausKunto = new Text("Onko auto vuokrattavissa?");
        RadioButton onhanNe = new RadioButton("On");
        RadioButton eiOle = new RadioButton("Ei ole");
        ToggleGroup vuokrausRyhma = new ToggleGroup();
        onhanNe.setToggleGroup(vuokrausRyhma);
        eiOle.setToggleGroup(vuokrausRyhma);
        HBox paneeliRadioButtoneille = new HBox(30);
        paneeliRadioButtoneille.setAlignment(Pos.CENTER);
        paneeliRadioButtoneille.getChildren().addAll(onhanNe, eiOle);
        Button tietojenTallennus = new Button("Tallenna tiedot");
        Text huoltovaliTeksti = new Text("Syötä ajoneuvon huoltoväli");
        TextField huoltovaliTF = new TextField();
        huoltovaliTF.setBackground(Background.fill(Color.WHITE));
        Text edellinenHuoltoTeksti = new Text("Syötä ajoneuvon edellisen huollon kilometrit");
        TextField edellinenHuoltoTF = new TextField();
        edellinenHuoltoTF.setBackground(Background.fill(Color.WHITE));
        Text kayttovoimaTeksti = new Text("Syötä ajoneuvon uusi käyttövoima");
        RadioButton bensiini = new RadioButton("Bensiini");
        RadioButton diesel = new RadioButton("Diesel");
        RadioButton sahko = new RadioButton("Sähkö");
        ToggleGroup kayttovoimaTG = new ToggleGroup();
        bensiini.setToggleGroup(kayttovoimaTG);
        diesel.setToggleGroup(kayttovoimaTG);
        sahko.setToggleGroup(kayttovoimaTG);
        HBox paneeliKayttoVoimalle = new HBox(10);
        paneeliKayttoVoimalle.getChildren().addAll(bensiini, diesel, sahko);
        paneeliKayttoVoimalle.setAlignment(Pos.CENTER);
        rekkariTF.setOnKeyTyped(e->{
            rekkariTF.setBackground(Background.fill(Color.WHITE));
        });
        rekkariTF.setOnMouseClicked(e->{
            rekkariTF.setBackground(Background.fill(Color.WHITE));
        });
        tietojenTallennus.setOnAction(e->{
            try {
                String etsittava = rekkariTF.getText();
                Auto valittu = null;
                int paivitettava=0;
                for (int i=0; i<autoLista.size(); i++){
                    if (etsittava.equals(autoLista.get(i).rekisterinumero)){
                        valittu=autoLista.get(i);
                        paivitettava=i;
                    }
                }
                if (valittu==null){
                    ErrorPopup(rekkariTF, "Autoa ei löytynyt");
                } else {
                    if (eiOle.isSelected()) {
                        ErrorPopup("Auto asetettu ei vuokrattavaksi");
                        this.autoLista.get(paivitettava).poistaAutoAjosta();
                    } else if (onhanNe.isSelected()) {
                        valittu.autoVuokrattavissa = true;
                    }
                    if (!kilometriTF.getText().isEmpty()) {
                        valittu.setKilometrit(Integer.parseInt(kilometriTF.getText()));
                    }
                    if (!huoltovaliTF.getText().isEmpty()) {
                        valittu.huoltovali = Integer.parseInt(huoltovaliTF.getText());
                    }
                    if (!edellinenHuoltoTF.getText().isEmpty()) {
                        valittu.edellinenHuolto=Integer.parseInt(edellinenHuoltoTF.getText());
                    }
                    if (bensiini.isSelected()){
                        valittu.kayttoVoima="Bensiini";
                    } else if (diesel.isSelected()) {
                        valittu.kayttoVoima="Diesel";
                    } else if (sahko.isSelected()) {
                        valittu.kayttoVoima="Sähkö";
                    }
                    kotiToiminto(primaryStage);
                    this.autoLista.set(paivitettava, valittu);
                }
            } catch (NumberFormatException ex) {
                ErrorPopup("Tarkista kenttien syötteet!\nTietoja ei tallennettu");
            }
        });

        paneeliAutonTiedoille.getChildren().addAll(palautettavaRekkari, rekkariTF, uudetKilometrit,
                kilometriTF, vuokrausKunto, paneeliRadioButtoneille,
                huoltovaliTeksti, huoltovaliTF, edellinenHuoltoTeksti, edellinenHuoltoTF,
                kayttovoimaTeksti, paneeliKayttoVoimalle, tietojenTallennus);
        paneeliTietojenMuokkaukselle.setCenter(paneeliAutonTiedoille);
        paneeliTietojenMuokkaukselle.setLeft(koti);
        koti.setOnAction(e->{
            kotiToiminto(primaryStage);
        });
        Scene scene = new Scene(paneeliTietojenMuokkaukselle, 500, 500);
        primaryStage.setScene(scene);
    }

    /**
     * Metodi luo käyttöliittymän auton etsimiselle, etsii kriteerejä vastaavat autot ja näyttää tulokset
     * @param primaryStage Stage, jossa käyttöliittymä pyörii
     */
    public void etsiSopivaAuto(Stage primaryStage){
        for (int i=0; i<autoLista.size(); i++){
            System.out.println(autoLista.get(i).rekisterinumero + " " + autoLista.get(i).mittarilukema);
        }
        BorderPane paneeliAutonEtsinnalle = new BorderPane();
        VBox autoKriteerit = new VBox(10);
        autoKriteerit.setAlignment(Pos.CENTER);
        Text millasta = new Text("Millaisen auton haluaisit?");
        HBox onkoPaku = new HBox(30);
        onkoPaku.setAlignment(Pos.CENTER);
        RadioButton onPaku = new RadioButton("Pakettiauto");
        RadioButton onHenkiloauto = new RadioButton("Henkilöauto");
        ToggleGroup valinta = new ToggleGroup();
        onPaku.setToggleGroup(valinta);
        onHenkiloauto.setToggleGroup(valinta);
        onkoPaku.getChildren().addAll(onHenkiloauto, onPaku);

        Text muuttuvaTeksti = new Text("Paljonko autossa tulisi olla heppoja?");
        TextField muuttuvaTF = new TextField();
        Text montakoPenkkia = new Text("Montako henkeä autoon tulisi mahtua?");
        TextField penkkiTF = new TextField();
        Text merkki = new Text("Minkä merkkisen ajoneuvon haluaisit?");
        TextField merkkiTF = new TextField();
        Text kayttovoima = new Text("Millä käyttövoimalla varustetun auton haluat?");
        CheckBox diesel = new CheckBox("Diesel");
        CheckBox bensa = new CheckBox("Bensiini");
        CheckBox sahko = new CheckBox("sähkö");
        VBox paneeliKayttovoimalle = new VBox(10);
        paneeliKayttovoimalle.getChildren().addAll(diesel, bensa, sahko);
        paneeliKayttovoimalle.setAlignment(Pos.CENTER);
        Button etsi = new Button("Etsi");

        onPaku.setOnAction(e->{
            if (onPaku.isSelected()){
                muuttuvaTeksti.setText("Kuinka suuren tavaratilan tarvitset?");
            }
        });
        onHenkiloauto.setOnAction(e->{
            if (onHenkiloauto.isSelected()){
                muuttuvaTeksti.setText("Paljonko autossa tulisi olla heppoja?");
            }
        });

        etsi.setOnAction(e->{
            ArrayList<Auto> henkilomaaraOikein = new ArrayList<>();
            ArrayList<Auto> merkkiOikein = new ArrayList<>();
            ArrayList<Auto> tarpeeksiTehokas = new ArrayList<>();
            ArrayList<String> kayttovoimaLista = new ArrayList<>();
            ArrayList<Auto> kayttovoimaOikein = new ArrayList<>();
            if (bensa.isSelected()){
                kayttovoimaLista.add("Bensiini");
            }
            if (diesel.isSelected()) {
                kayttovoimaLista.add("Diesel");
            }
            if (sahko.isSelected()) {
                kayttovoimaLista.add("Sähkö");
            }

            try {
                for (int i = 0; i < autoLista.size(); i++) {
                    if (!penkkiTF.getText().isEmpty()){
                        if (autoLista.get(i).matkustajienLukumaara>=Integer.parseInt(penkkiTF.getText())){
                            henkilomaaraOikein.add(autoLista.get(i));
                        }
                    } else henkilomaaraOikein.add(autoLista.get(i));
                }
                for (int i = 0; i < henkilomaaraOikein.size(); i++){
                    if (!merkkiTF.getText().isEmpty()){
                        if (henkilomaaraOikein.get(i).merkki.equals(merkkiTF.getText())){
                            merkkiOikein.add(henkilomaaraOikein.get(i));
                        }
                    } else merkkiOikein.add(henkilomaaraOikein.get(i));
                }
                for (int i = 0; i<merkkiOikein.size(); i++){
                    if (!muuttuvaTF.getText().isEmpty()){
                        if (merkkiOikein.get(i).maksimiteho>=Integer.parseInt(muuttuvaTF.getText()))
                            tarpeeksiTehokas.add(merkkiOikein.get(i));
                    } else tarpeeksiTehokas.add(merkkiOikein.get(i));
                }
                for (int i = 0; i < tarpeeksiTehokas.size(); i++){
                    if (!kayttovoimaLista.isEmpty()){
                        for (int j=0; j < kayttovoimaLista.size(); j++){
                            if (tarpeeksiTehokas.get(i).kayttoVoima.equals(kayttovoimaLista.get(j))){
                                kayttovoimaOikein.add(tarpeeksiTehokas.get(i));
                            }
                        }
                    } else kayttovoimaOikein.add(tarpeeksiTehokas.get(i));
                }

                naytaAutot(primaryStage, kayttovoimaOikein);
            } catch (NumberFormatException ex) {
                ErrorPopup("Tarkista kenttien syötteet!");
            }
        });

        autoKriteerit.getChildren().addAll(millasta, onkoPaku, muuttuvaTeksti, muuttuvaTF, montakoPenkkia, penkkiTF, merkki, merkkiTF, kayttovoima, paneeliKayttovoimalle, etsi);
        paneeliAutonEtsinnalle.setCenter(autoKriteerit);
        paneeliAutonEtsinnalle.setLeft(koti);
        koti.setOnAction(e->{
            kotiToiminto(primaryStage);
        });
        Scene scene = new Scene(paneeliAutonEtsinnalle, 500, 500);
        primaryStage.setScene(scene);
    }

    /**
     * Metodi muokkaa tiettyjä auton tietoja
     * Metodia tarkoitus käyttää, kun asiakas palauttaa auton
     * @param primaryStage Stage, jossa käyttöliittymä pyörii
     */
    public void palautaAuto(Stage primaryStage){
        BorderPane paneeliAutonVastaanottamiselle = new BorderPane();

        VBox paneeliAutonTiedoille = new VBox(10);
        paneeliAutonTiedoille.setAlignment(Pos.CENTER);
        Text palautettavaRekkari = new Text("Syötä palautettavan ajoneuvon rekisteritunnus");
        TextField rekkariTF = new TextField();
        rekkariTF.setBackground(Background.fill(Color.WHITE));
        Text uudetKilometrit = new Text("Syötä ajoneuvon mittarilukema");
        TextField kilometriTF = new TextField();
        kilometriTF.setBackground(Background.fill(Color.WHITE));
        Text rengasKunto = new Text("Ovatko renkaat samat kuin aiemmin ja hyvässä kunnossa?");
        RadioButton onhanNe = new RadioButton("Renkaat tarkistettu ja kaikki ok");
        RadioButton eiOle = new RadioButton("Renkaat ei kunnossa tai ei tarkistettu");
        ToggleGroup rengasryhma = new ToggleGroup();
        onhanNe.setToggleGroup(rengasryhma);
        eiOle.setToggleGroup(rengasryhma);
        HBox paneeliRadioButtoneille = new HBox(30);
        paneeliRadioButtoneille.getChildren().addAll(onhanNe, eiOle);
        Button tietojenTallennus = new Button("Tallenna tiedot");
        kilometriTF.setOnMouseClicked(e->{
            kilometriTF.setBackground(Background.fill(Color.WHITE));
        });
        kilometriTF.setOnKeyTyped(e->{
            kilometriTF.setBackground(Background.fill(Color.WHITE));
        });
        rekkariTF.setOnKeyTyped(e->{
            rekkariTF.setBackground(Background.fill(Color.WHITE));
        });
        rekkariTF.setOnMouseClicked(e->{
            rekkariTF.setBackground(Background.fill(Color.WHITE));
        });
        tietojenTallennus.setOnAction(e->{
            boolean kilsatOK=false;
            boolean rekkariOK = false;
            boolean renkaatOK = false;
            String etsittava = rekkariTF.getText();
            Auto valittu = null;
            int paivitettava=0;
            for (int i=0; i<autoLista.size(); i++){
                if (etsittava.equals(autoLista.get(i).rekisterinumero)){
                    valittu=this.autoLista.get(i);
                    rekkariOK=true;
                    paivitettava=i;
                }
            }
            try {
                if (valittu==null){
                    ErrorPopup(rekkariTF, "Autoa ei löytynyt");
                } else if (valittu.mittarilukema<(Integer.parseInt(kilometriTF.getText()))) {
                    kilsatOK=true;
                } else {
                    ErrorPopup(kilometriTF, "kilometrit eivät voi olla vähemmän kuin aiemmin");
                }
            } catch (NumberFormatException ex) {
                ErrorPopup("Kilometrien oltava kokonaisluku");
            }
            if (onhanNe.isSelected()){
                renkaatOK=true;
            } else if (eiOle.isSelected()) {
                valittu.autoVuokrattavissa=false;
                ErrorPopup("Auto asetettu ei vuokrattavaksi");
                renkaatOK=true;
            } else ErrorPopup("Tarkista auton renkaiden kunto");

            if (kilsatOK&&rekkariOK&&renkaatOK){
                try {
                    valittu.setKilometrit(Integer.parseInt(kilometriTF.getText()));
                } catch (NumberFormatException ex) {
                    ErrorPopup("Kilometrien oltava kokonaisluku\nKilometrejä ei päivitetty.");
                }
                valittu.autoVuokrattavissa=true;
                if (onhanNe.isSelected()){
                    valittu.laitaAutoAjoon();
                } else if (eiOle.isSelected()) {
                    valittu.poistaAutoAjosta();
                }
                kotiToiminto(primaryStage);
            }
            System.out.println(valittu);
            autoLista.set(paivitettava, valittu);
        });

        paneeliAutonTiedoille.getChildren().addAll(palautettavaRekkari, rekkariTF, uudetKilometrit, kilometriTF, rengasKunto, paneeliRadioButtoneille, tietojenTallennus);
        paneeliAutonVastaanottamiselle.setCenter(paneeliAutonTiedoille);
        paneeliAutonVastaanottamiselle.setLeft(koti);
        koti.setOnAction(e->{
            kotiToiminto(primaryStage);
        });
        Scene scene = new Scene(paneeliAutonVastaanottamiselle, 500, 500);
        primaryStage.setScene(scene);
    }

    /**
     * Metodi luo popup ikkunan jossa jokin teksti ja muokkaa virheen aiheuttaman textfieldin taustavärin punaiseksi
     * @param errorTF textField, jonka taustaväri muutetaan punaiseksi
     * @param virhe teksti, joka kuvaa tapahtunutta virhettä
     */

    public void ErrorPopup(TextField errorTF, String virhe) {
        errorTF.setBackground(Background.fill(Color.RED));
        BorderPane virhePaneeli = new BorderPane();
        virhePaneeli.setPadding(new Insets(10, 10, 10, 10));
        Text virheTeksti = new Text(virhe);
        virheTeksti.setTextAlignment(TextAlignment.CENTER);
        virhePaneeli.setCenter(virheTeksti);
        Stage virheilmoitus = new Stage();
        Scene virheScene = new Scene(virhePaneeli, 300, 300);
        virheilmoitus.setScene(virheScene);
        virheilmoitus.setTitle("Virhe!");
        virheilmoitus.show();
    }

    /**
     * Luo popup ikkunan virhettä varten, mutta ei muokkaa mitään textFieldiä
     * @param virhe teksti, jolla kuvataan mikä virhe on tapahtunut
     */
    public void ErrorPopup(String virhe) {
        TextField textField = new TextField();
        ErrorPopup(textField, virhe);
    }

    /**
     * Näyttää listan kaikista autoista, jotka on autoLista arrayListissa
     * @param primaryStage Stage, jossa käyttöliittymä pyörii
     * @param autoLista ArrayList, jossa näytettävät ajoneuvot
     */
    public void naytaAutot(Stage primaryStage, ArrayList<Auto> autoLista){
        BorderPane paneeliAutoValikolle = new BorderPane();
        TextArea alueTarkemmilleTiedoille = new TextArea();
        alueTarkemmilleTiedoille.setPrefSize(400, 500);

        rekkariLista.clear();
        for (int i = 0; i<autoLista.size(); i++){
            rekkariLista.add(autoLista.get(i).rekisterinumero);
        }
        for (int i = 0; i<autoLista.size(); i++){
            System.out.println(autoLista.get(i) + "\n\n\n");
        }

        ListView<String> listaAutoille = new ListView<>();
        listaAutoille.setItems(FXCollections.observableArrayList(rekkariLista));
        listaAutoille.getSelectionModel().selectedItemProperty().addListener(ov->{
            alueTarkemmilleTiedoille.setText(
                    autoLista.get(listaAutoille.getSelectionModel().getSelectedIndex()).toString());
        });
        paneeliAutoValikolle.setCenter(listaAutoille);
        paneeliAutoValikolle.setRight(alueTarkemmilleTiedoille);
        paneeliAutoValikolle.setLeft(koti);
        koti.setOnAction(e->{
            kotiToiminto(primaryStage);
        });
        Scene scene = new Scene(paneeliAutoValikolle, 800, 500);
        primaryStage.setScene(scene);
    }

    /**
     * Luo käyttöliittymän uuden aujoneuvon lisäämiselle ja muokkaa autoListaa. Ei tallenna tietoja vielä.
     * @param primaryStage Stage, jossa käyttöliittymä  pyörii
     */
    public void autonLisaaminen(Stage primaryStage){
        BorderPane autonLisaysPaneeli = new BorderPane();
        VBox paneeliAutonTiedoille = new VBox(10);
        Text rekkariTeksti = new Text("Syötä ajoneuvon rekisterinumero");
        TextField rekkariTF = new TextField();
        Text mittarilukemaTeksti = new Text("Syötä ajoneuvon mittarilukema");
        TextField mittarilukemaTF = new TextField();
        Text vuosimalliTeksti = new Text("Syötä ajoneuvon vuosimalli");
        TextField vuosimalliTF = new TextField();
        Text merkkiTeksti = new Text("Syötä ajoneuvon merkki");
        TextField merkkiTF = new TextField();
        Text malliTeksti = new Text("Syötä ajoneuvon malli");
        TextField malliTF = new TextField();
        Text huoltovaliTeksti = new Text("Syötä ajoneuvon huoltoväli");
        TextField huoltovaliTF = new TextField();
        Text edellinenHuoltoTeksti = new Text("Mikä on viimeinen mittarilukema huoltokirjassa?");
        TextField edellinenHuoltoTF = new TextField();
        Text kayttovoimaTeksti = new Text("Syötä ajoneuvon käyttövoima");
        VBox paneeliKayttovoimalle = new VBox(5);
        RadioButton sahko = new RadioButton("Sähkö");
        RadioButton bensa = new RadioButton("Bensiini");
        RadioButton diesel = new RadioButton("Diesel");
        ToggleGroup kayttovoimaTG = new ToggleGroup();
        sahko.setToggleGroup(kayttovoimaTG);
        bensa.setToggleGroup(kayttovoimaTG);
        diesel.setToggleGroup(kayttovoimaTG);
        paneeliKayttovoimalle.getChildren().addAll(sahko, bensa, diesel);
        paneeliKayttovoimalle.setAlignment(Pos.CENTER);

        Text matkustajat = new Text("Syötä autoon mahtuvien matkustajien lukumäärä");
        TextField matkustajaTF = new TextField();
        Text pakuTeksti = new Text("Onko ajoneuvo pakettiauto?");
        CheckBox onkoPaku = new CheckBox();
        Text tilavuusTeksti = new Text("Syötä pakettiauton tavaratilan tilavuus");
        TextField tilavuusTF = new TextField();
        Text tehoTeksti = new Text("Syötä auton maksimiteho");
        TextField tehoTF = new TextField();
        Button tallennusNappi = new Button("Tallenna");
        onkoPaku.setOnAction(e->{
            if (onkoPaku.isSelected()){
                paneeliAutonTiedoille.getChildren().clear();
                paneeliAutonTiedoille.getChildren().addAll(rekkariTeksti, rekkariTF, mittarilukemaTeksti, mittarilukemaTF,
                        vuosimalliTeksti, vuosimalliTF, merkkiTeksti, merkkiTF, malliTeksti, malliTF,
                        huoltovaliTeksti, huoltovaliTF, edellinenHuoltoTeksti, edellinenHuoltoTF, kayttovoimaTeksti,
                        paneeliKayttovoimalle, matkustajat, matkustajaTF, pakuTeksti, onkoPaku, tilavuusTeksti, tilavuusTF,
                        tallennusNappi);
            }
            if (!onkoPaku.isSelected()){
                paneeliAutonTiedoille.getChildren().clear();
                paneeliAutonTiedoille.getChildren().addAll(rekkariTeksti, rekkariTF, mittarilukemaTeksti, mittarilukemaTF,
                        vuosimalliTeksti, vuosimalliTF, merkkiTeksti, merkkiTF, malliTeksti, malliTF,
                        huoltovaliTeksti, huoltovaliTF, edellinenHuoltoTeksti, edellinenHuoltoTF, kayttovoimaTeksti,
                        paneeliKayttovoimalle, matkustajat, matkustajaTF, pakuTeksti, onkoPaku, tehoTeksti, tehoTF, tallennusNappi);
            }
        });

        tallennusNappi.setOnAction(e->{
            try {
                if (sahko.isSelected()) {
                    valittuKayttovoima="Sähkö";
                } else if (bensa.isSelected()) {
                    valittuKayttovoima="Bensiini";
                } else if (diesel.isSelected()) {
                    valittuKayttovoima="Diesel";
                } else ErrorPopup("Valitse käyttövoima");
                Auto lisattavaAuto = null;
                if (!onkoPaku.isSelected()) {
                    lisattavaAuto = new Auto(
                            rekkariTF.getText(),
                            Integer.parseInt(mittarilukemaTF.getText()),
                            Integer.parseInt(vuosimalliTF.getText()),
                            merkkiTF.getText(),
                            malliTF.getText(),
                            Integer.parseInt(huoltovaliTF.getText()),
                            Integer.parseInt(edellinenHuoltoTF.getText()),
                            valittuKayttovoima,
                            Integer.parseInt(matkustajaTF.getText()),
                            Integer.parseInt(tehoTF.getText())
                    );
                } else if (onkoPaku.isSelected()) {
                    lisattavaAuto=new Paku(
                            rekkariTF.getText(),
                            Integer.parseInt(mittarilukemaTF.getText()),
                            Integer.parseInt(vuosimalliTF.getText()),
                            merkkiTF.getText(),
                            malliTF.getText(),
                            Integer.parseInt(huoltovaliTF.getText()),
                            Integer.parseInt(edellinenHuoltoTF.getText()),
                            valittuKayttovoima,
                            Integer.parseInt(tilavuusTF.getText()),
                            Integer.parseInt(matkustajaTF.getText())

                    );
                }
                autoLista.add(lisattavaAuto);
                kotiToiminto(primaryStage);
            } catch (NumberFormatException ex) {
                ErrorPopup("Tarkista kenttien syötteet!");
            }
        });

        paneeliAutonTiedoille.getChildren().addAll(rekkariTeksti, rekkariTF, mittarilukemaTeksti, mittarilukemaTF,
                vuosimalliTeksti, vuosimalliTF, merkkiTeksti, merkkiTF, malliTeksti, malliTF,
                huoltovaliTeksti, huoltovaliTF, edellinenHuoltoTeksti, edellinenHuoltoTF, kayttovoimaTeksti,
                paneeliKayttovoimalle, matkustajat, matkustajaTF, pakuTeksti, onkoPaku, tehoTeksti, tehoTF, tallennusNappi);
        paneeliAutonTiedoille.setAlignment(Pos.CENTER);
        autonLisaysPaneeli.setCenter(paneeliAutonTiedoille);
        autonLisaysPaneeli.setLeft(koti);
        koti.setOnAction(e->{
            kotiToiminto(primaryStage);
        });
        Scene tietojenSyotto = new Scene(autonLisaysPaneeli, 400, 800);
        primaryStage.setTitle("Lisää auto");
        primaryStage.setScene(tietojenSyotto);
        primaryStage.show();
    }

    /**
     * Tallentaa autoListan ajoneuvot tiedostoon autoTiedosto
     */

    public void tallennusMetodi() {
        try {
            autoTiedosto.delete();
            ObjectOutputStream uusiTiedosto = new ObjectOutputStream(new FileOutputStream(autoTiedosto));
            for (int i=0; i<this.autoLista.size(); i++)
                uusiTiedosto.writeObject(this.autoLista.get(i));
            System.out.println("tiedot tallennettu");
        }catch (Exception g){
            throw new RuntimeException(g);
        }
    }
}

