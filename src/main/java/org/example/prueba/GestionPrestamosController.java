package org.example.prueba;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class GestionPrestamosController {
    @FXML
    private DatePicker selectorFechaDevolucion;


    @FXML
    private ComboBox<Usuario> comboUsuarios;
    @FXML
    private ComboBox<Libro> comboLibros;

    @FXML
    private TableView<Prestamo> tablaPrestamos;
    @FXML
    private TableColumn<Prestamo, String> columnaLibro;
    @FXML
    private TableColumn<Prestamo, String> columnaUsuario;
    @FXML
    private TableColumn<Prestamo, String> columnaFechaPrestamo;
    @FXML
    private TableColumn<Prestamo, String> columnaFechaDevolucion;
    @FXML
    private TableColumn<Prestamo, String> columnaEstado;
    @FXML
    private TableColumn<Prestamo, String> columnaMulta;

    private final ObservableList<Prestamo> listaPrestamos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        comboUsuarios.setItems(GestionUsuariosController.listaUsuariosGlobal);
        comboLibros.setItems(GestionLibrosController.listaLibrosGlobal);

        // Mostrar nombres en ComboBox de usuarios
        comboUsuarios.setCellFactory(lv -> new ListCell<Usuario>() {
            @Override
            protected void updateItem(Usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
        comboUsuarios.setButtonCell(new ListCell<Usuario>() {
            @Override
            protected void updateItem(Usuario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });

        // Mostrar títulos en ComboBox de libros
        comboLibros.setCellFactory(lv -> new ListCell<Libro>() {
            @Override
            protected void updateItem(Libro item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTitulo());
            }
        });
        comboLibros.setButtonCell(new ListCell<Libro>() {
            @Override
            protected void updateItem(Libro item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTitulo());
            }
        });

        columnaLibro.setCellValueFactory(data -> data.getValue().libroProperty());
        columnaUsuario.setCellValueFactory(data -> data.getValue().usuarioProperty());
        columnaFechaPrestamo.setCellValueFactory(data -> data.getValue().fechaPrestamoProperty());
        columnaFechaDevolucion.setCellValueFactory(data -> data.getValue().fechaDevolucionProperty());
        columnaEstado.setCellValueFactory(data -> data.getValue().estadoProperty());
        columnaMulta.setCellValueFactory(data -> data.getValue().multaProperty());

        tablaPrestamos.setItems(listaPrestamos);
    }

    @FXML
    private void registrarPrestamo() {
        Usuario usuario = comboUsuarios.getSelectionModel().getSelectedItem();
        Libro libro = comboLibros.getSelectionModel().getSelectedItem();

        if (usuario == null || libro == null) {
            mostrarAlerta("Faltan datos", "Selecciona usuario y libro.");
            return;
        }

        if (usuario.tieneMulta()) {
            mostrarAlerta("Usuario bloqueado", "El usuario tiene multa activa.");
            return;
        }

        if (usuario.getPrestamosActivos() >= 3) {
            mostrarAlerta("Límite de préstamos", "El usuario ya tiene 3 libros prestados.");
            return;
        }

        int cantidadDisponible = Integer.parseInt(libro.getCantidad());
        if (cantidadDisponible <= 0) {
            mostrarAlerta("Libro no disponible", "No quedan ejemplares disponibles.");
            return;
        }

        // Registrar el préstamo
        String fechaPrestamo = LocalDate.now().toString();
        //String fechaDevolucion = LocalDate.now().plusDays(30).toString();
       // String fechaDevolucion = LocalDate.now().minusDays(5).toString();
        LocalDate fechaDevolucionSeleccionada = selectorFechaDevolucion.getValue();
        String fechaDevolucion;

        if (fechaDevolucionSeleccionada != null) {
            fechaDevolucion = fechaDevolucionSeleccionada.toString();
        } else {
            fechaDevolucion = LocalDate.now().plusDays(30).toString();
        }

        Prestamo nuevoPrestamo = new Prestamo(
                libro.getTitulo(),
                usuario.getNombre(),
                fechaPrestamo,
                fechaDevolucion,
                "En préstamo",
                "No"
        );

        listaPrestamos.add(nuevoPrestamo);

        // Descontar cantidad del libro
        libro.setCantidad(String.valueOf(cantidadDisponible - 1));

        // Aumentar conteo del usuario
        usuario.aumentarPrestamos();
        tablaPrestamos.refresh();
    }

    @FXML
    private void devolverLibro() {
        Prestamo seleccionado = tablaPrestamos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Selecciona un préstamo", "Primero selecciona un préstamo para devolver.");
            return;
        }

        if (seleccionado.getEstado().equals("Devuelto")) {
            mostrarAlerta("Préstamo finalizado", "Ese libro ya ha sido devuelto.");
            return;
        }

        seleccionado.setEstado("Devuelto");

        // Verificar si hay retraso
        LocalDate fechaDevolucionReal = LocalDate.now();
        LocalDate fechaDevolucionEsperada = LocalDate.parse(seleccionado.getFechaDevolucion());

        if (fechaDevolucionReal.isAfter(fechaDevolucionEsperada)) {
            long diasRetraso = java.time.temporal.ChronoUnit.DAYS.between(fechaDevolucionEsperada, fechaDevolucionReal);
            int diasMulta = (int) diasRetraso * 2;

            seleccionado.setMulta("Sí (" + diasMulta + " días)");
            for (Usuario usuario : GestionUsuariosController.listaUsuariosGlobal) {
                if (usuario.getNombre().equals(seleccionado.getUsuario())) {
                    usuario.activarMulta(diasMulta);
                    break;
                }
            }
        } else {
            seleccionado.setMulta("No");
        }

        // Aumentar cantidad del libro
        for (Libro libro : GestionLibrosController.listaLibrosGlobal) {
            if (libro.getTitulo().equals(seleccionado.getLibro())) {
                int cantidad = Integer.parseInt(libro.getCantidad());
                libro.setCantidad(String.valueOf(cantidad + 1));
                break;
            }
        }

        // Reducir préstamos activos del usuario
        for (Usuario usuario : GestionUsuariosController.listaUsuariosGlobal) {
            if (usuario.getNombre().equals(seleccionado.getUsuario())) {
                usuario.disminuirPrestamos();
                break;
            }
        }

        tablaPrestamos.refresh();



    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
