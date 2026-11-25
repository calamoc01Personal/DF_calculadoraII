package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase JavaFX App que extiende de application
 * @author  Celia Alamo Calle
 */
public class App extends Application {

    private static Scene scene;
    /**
     *  metodo Start permite  iniciar  la ventana principal calculadora.fxml  y  a demas evita que se maximize
     * */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("calculadora"), 600, 316);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}