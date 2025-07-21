package org.example.prueba;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Prestamo {
    private final StringProperty libro;
    private final StringProperty usuario;
    private final StringProperty fechaPrestamo;
    private final StringProperty fechaDevolucion;
    private final StringProperty estado;
    private final StringProperty multa;

    public Prestamo(String libro, String usuario, String fechaPrestamo, String fechaDevolucion, String estado, String multa) {
        this.libro = new SimpleStringProperty(libro);
        this.usuario = new SimpleStringProperty(usuario);
        this.fechaPrestamo = new SimpleStringProperty(fechaPrestamo);
        this.fechaDevolucion = new SimpleStringProperty(fechaDevolucion);
        this.estado = new SimpleStringProperty(estado);
        this.multa = new SimpleStringProperty(multa);
    }

    public String getLibro() { return libro.get(); }
    public String getUsuario() { return usuario.get(); }
    public String getFechaPrestamo() { return fechaPrestamo.get(); }
    public String getFechaDevolucion() { return fechaDevolucion.get(); }
    public String getEstado() { return estado.get(); }
    public String getMulta() { return multa.get(); }

    public void setEstado(String estado) { this.estado.set(estado); }
    public void setMulta(String multa) { this.multa.set(multa); }

    public StringProperty libroProperty() { return libro; }
    public StringProperty usuarioProperty() { return usuario; }
    public StringProperty fechaPrestamoProperty() { return fechaPrestamo; }
    public StringProperty fechaDevolucionProperty() { return fechaDevolucion; }
    public StringProperty estadoProperty() { return estado; }
    public StringProperty multaProperty() { return multa; }
}

