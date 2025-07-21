package org.example.prueba;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private TextField barraBusqueda;

    @FXML
    private void buscarLibro() {
        String textoBuscado = barraBusqueda.getText();
        if (textoBuscado.isEmpty()) {
            System.out.println("Introduce el nombre o palabra clave del libro.");
        } else {
            System.out.println("Buscando libro: " + textoBuscado);
            // Luego podrás mostrar resultados en otra vista
        }
    }

    @FXML
    private void abrirGestionLibros() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gestion-libros-view.fxml"));
            Scene escena = new Scene(loader.load(), 600, 400);

            Stage ventana = new Stage();
            ventana.setTitle("Gestión de Libros");
            ventana.setScene(escena);
            ventana.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirGestionUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gestion-usuarios-view.fxml"));
            Scene escena = new Scene(loader.load(), 600, 400);

            Stage ventana = new Stage();
            ventana.setTitle("Gestión de Usuarios");
            ventana.setScene(escena);
            ventana.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirGestionPrestamos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gestion-prestamos-view.fxml"));
            Scene escena = new Scene(loader.load(), 700, 400);

            Stage ventana = new Stage();
            ventana.setTitle("Gestión de Préstamos");
            ventana.setScene(escena);
            ventana.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void salir() {
        System.exit(0);
    }
}
