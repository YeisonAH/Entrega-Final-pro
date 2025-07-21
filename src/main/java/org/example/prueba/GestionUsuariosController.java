package org.example.prueba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;

public class GestionUsuariosController {
    public static ObservableList<Usuario> listaUsuariosGlobal = FXCollections.observableArrayList();
    @FXML
    private Usuario usuarioSeleccionado = null;

    @FXML
    private TextField campoTelefono;
    @FXML
    private TextField campoIdentificacion;
    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoCorreo;

    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<Usuario, String> columnaTelefono;
    @FXML
    private TableColumn<Usuario, String> columnaIdentificacion;
    @FXML
    private TableColumn<Usuario, String> columnaNombre;
    @FXML
    private TableColumn<Usuario, String> columnaCorreo;

    @FXML
    private TableColumn<Usuario, String> columnaPrestamos;
    @FXML
    private TableColumn<Usuario, String> columnaMulta;

    private final ObservableList<Usuario> listaUsuarios = listaUsuariosGlobal;


    @FXML
    public void initialize() {
        columnaNombre.setCellValueFactory(data -> data.getValue().nombreProperty());
        columnaCorreo.setCellValueFactory(data -> data.getValue().correoProperty());
        columnaTelefono.setCellValueFactory(data -> data.getValue().telefonoProperty());
        columnaIdentificacion.setCellValueFactory(data -> data.getValue().identificacionProperty());
        columnaPrestamos.setCellValueFactory(data ->
                new SimpleStringProperty(String.valueOf(data.getValue().getPrestamosActivos()))
        );

        columnaMulta.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().tieneMulta() ?
                        "Sí (" + data.getValue().getDiasDeMulta() + " días)" :
                        "No")
        );

        tablaUsuarios.setItems(listaUsuarios);
        tablaUsuarios.setOnMouseClicked(event -> mostrarDatosSeleccionado());
    }

    @FXML
    private void agregarUsuario() {
        String nombre = campoNombre.getText().trim();
        String correo = campoCorreo.getText().trim();
        String telefono = campoTelefono.getText().trim();
        String identificacion = campoIdentificacion.getText().trim();

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || identificacion.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor completa todos los campos.");
            return;
        }

        listaUsuarios.add(new Usuario(nombre, correo, telefono, identificacion));
        limpiarCampos();
    }

    @FXML
    private void eliminarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            listaUsuarios.remove(seleccionado);
        }
    }

    @FXML
    private void mostrarDatosSeleccionado() {
        usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            campoNombre.setText(usuarioSeleccionado.getNombre());
            campoCorreo.setText(usuarioSeleccionado.getCorreo());
            campoTelefono.setText(usuarioSeleccionado.getTelefono());
            campoIdentificacion.setText(usuarioSeleccionado.getIdentificacion());
        }
    }

    @FXML
    private void actualizarUsuario() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta("Selección requerida", "Primero selecciona un usuario para actualizar.");
            return;
        }

        String nombre = campoNombre.getText().trim();
        String correo = campoCorreo.getText().trim();
        String telefono = campoTelefono.getText().trim();
        String identificacion = campoIdentificacion.getText().trim();

        if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || identificacion.isEmpty()) {
            mostrarAlerta("Campos incompletos", "Por favor completa todos los campos.");
            return;
        }

        usuarioSeleccionado.nombreProperty().set(nombre);
        usuarioSeleccionado.correoProperty().set(correo);
        usuarioSeleccionado.telefonoProperty().set(telefono);
        usuarioSeleccionado.identificacionProperty().set(identificacion);

        tablaUsuarios.refresh();
        mostrarAlerta("Éxito", "Usuario actualizado correctamente.");
        limpiarCampos();
    }
    @FXML
    private void desbloquearUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            seleccionado.desactivarMulta();
            tablaUsuarios.refresh();
        } else {
            mostrarAlerta("Selección requerida", "Selecciona un usuario para desbloquear.");
        }
    }
    @FXML
    private void reducirMultas() {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getDiasDeMulta() > 0) {
                usuario.reducirDiasDeMulta();
                if (usuario.getDiasDeMulta() == 0) {
                    usuario.desactivarMulta();
                }
            }
        }
        tablaUsuarios.refresh();
    }

    private void limpiarCampos() {
            campoNombre.clear();
            campoCorreo.clear();
            campoTelefono.clear();
            campoIdentificacion.clear();
        }





    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
