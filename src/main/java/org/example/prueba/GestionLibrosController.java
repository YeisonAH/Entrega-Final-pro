package org.example.prueba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class GestionLibrosController {
    public static ObservableList<Libro> listaLibrosGlobal = FXCollections.observableArrayList();
    @FXML
    private Libro libroSeleccionado = null;
    @FXML
    private TextField campoTitulo;

    @FXML
    private TextField campoAutor;

    @FXML
    private TextField campoEditorial;
    @FXML
    private TextField campoBusqueda;
    @FXML
    private TextField campoCantidad;

    @FXML
    private TableView<Libro> tablaLibros;

    @FXML
    private TableColumn<Libro, String> columnaTitulo;

    @FXML
    private TableColumn<Libro, String> columnaAutor;

    @FXML
    private TableColumn<Libro, String> columnaEditorial;
    @FXML
    private TableColumn<Libro, String> columnaCantidad;  // ← Aquí

    @FXML
    private Label etiquetaCantidad;

    private final ObservableList<Libro> listaLibros = listaLibrosGlobal;



    @FXML
    public void initialize() {
        columnaTitulo.setCellValueFactory(data -> data.getValue().tituloProperty());
        columnaAutor.setCellValueFactory(data -> data.getValue().autorProperty());
        columnaEditorial.setCellValueFactory(data -> data.getValue().editorialProperty());
        columnaCantidad.setCellValueFactory(data -> data.getValue().cantidadProperty());

        // Libros de ejemplo
        listaLibros.add(new Libro("Cien Años de Soledad", " Gabriel García Márquez", "Sudamericana", "5"));
        listaLibros.add(new Libro("Don Quijote", "Miguel de Cervantes", "Francisco de Robles","2"));
        listaLibros.add(new Libro("El principito", "Anotine Saint-Exupéry", "Planeta","10"));
        listaLibros.add(new Libro("Rebelión en la granja", "George Orwell", "Panamericana","4"));
        listaLibros.add(new Libro("Divina comedia", "Dante Alighieri   ", "Alianza Editorial","0"));
        listaLibros.add(new Libro("Orgullo y prejuicio", "Jane Austen", "Planeta","1"));



        tablaLibros.setItems(listaLibros);
        tablaLibros.setOnMouseClicked(event -> mostrarCantidadSeleccionada());
    }

    @FXML
    private void mostrarCantidadSeleccionada() {
        libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            campoTitulo.setText(libroSeleccionado.getTitulo());
            campoAutor.setText(libroSeleccionado.getAutor());
            campoEditorial.setText(libroSeleccionado.getEditorial());
            campoCantidad.setText(libroSeleccionado.getCantidad());
            etiquetaCantidad.setText("Cantidad disponible: " + libroSeleccionado.getCantidad());
        }
    }
    @FXML
    private void buscarLibro() {
        String texto = campoBusqueda.getText().toLowerCase();
        if (texto.isEmpty()) {
            tablaLibros.setItems(listaLibros);
            return;
        }

        ObservableList<Libro> filtrados = FXCollections.observableArrayList();
        for (Libro libro : listaLibros) {
            if (libro.getTitulo().toLowerCase().contains(texto)) {
                filtrados.add(libro);
            }
        }
        tablaLibros.setItems(filtrados);
    }

    @FXML
    private void agregarLibro() {
        String titulo = campoTitulo.getText();
        String autor = campoAutor.getText();
        String editorial = campoEditorial.getText();
        String cantidad = campoCantidad.getText();

        if (titulo.isEmpty() || autor.isEmpty() || editorial.isEmpty()|| cantidad.isEmpty()) {
            System.out.println("Todos los campos son obligatorios.");
            return;
        }

        listaLibros.add(new Libro(titulo, autor, editorial,cantidad));

        // Limpiar los campos después de agregar
        campoTitulo.clear();
        campoAutor.clear();
        campoEditorial.clear();
    }

    @FXML
    private void eliminarLibro() {
        Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaLibros.remove(seleccionado);
        }
    }

    @FXML

    private void actualizarLibro() {
        if (libroSeleccionado == null) {
            mostrarAlerta("Selección requerida", "Primero selecciona un libro para actualizar.");
            return;
        }

        String titulo = campoTitulo.getText().trim();
        String autor = campoAutor.getText().trim();
        String editorial = campoEditorial.getText().trim();
        String cantidad = campoCantidad.getText().trim();

        if (titulo.isEmpty() || autor.isEmpty() || editorial.isEmpty() || cantidad.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor completa todos los campos.");
            return;
        }

        try {
            int cantidadInt = Integer.parseInt(cantidad);
            if (cantidadInt < 0) {
                mostrarAlerta("Cantidad no válida", "Introduce una cantidad positiva.");
                return;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Cantidad no válida", "Introduce un número válido.");
            return;
        }

        // Actualizar propiedades del libro
        libroSeleccionado.tituloProperty().set(titulo);
        libroSeleccionado.autorProperty().set(autor);
        libroSeleccionado.editorialProperty().set(editorial);
        libroSeleccionado.cantidadProperty().set(cantidad);

        tablaLibros.refresh();
        mostrarAlerta("Éxito", "Libro actualizado correctamente.");
        limpiarCampos();
    }
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        campoTitulo.clear();
        campoAutor.clear();
        campoEditorial.clear();
        campoCantidad.clear();
    }

}
