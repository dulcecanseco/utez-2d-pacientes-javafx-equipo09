package com.consultorio.utez2dpacientesjavafxequipo09;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("¡Intentando abrir la ventana!");

        // 1. Buscamos el archivo de forma segura
        var resource = HelloApplication.class.getResource("hello-view.fxml");

        if (resource == null) {
            System.out.println("ERROR: No encontré el archivo hello-view.fxml");
            System.out.println("Asegúrate de que esté en: src/main/resources/com/consultorio/utez2dpacientesjavafxequipo09/");
            return;
        }

        // 2. Si lo encuentra, lo cargamos
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Consultorio UTEZ");
        stage.setScene(scene);
        stage.show();

        System.out.println(">>> ¡Ventana desplegada con éxito! <<<");
    }
}
