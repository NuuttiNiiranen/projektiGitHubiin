package com.github.nuuttiniiranen.projektigithubiin;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * @author Nuutti Niiranen
 * @version 1.0
 * 10.4.2024
 */

/**
 * Tyhmä main luokka, josta ei tehdä muuta kuin käynnistetään aivan liian pitkä UI luokka.
 */

public class Main extends Application {
    /**
     * käynnistetään ohjelma
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        UI alku = new UI();
        alku.start(primaryStage);
    }
}
