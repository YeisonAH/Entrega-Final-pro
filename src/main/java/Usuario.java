package org.example.prueba;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Usuario {

    private final StringProperty nombre;
    private final StringProperty correo;
    private final StringProperty Telefono;
    private final StringProperty identificacion;
    private int prestamosActivos;
    private boolean multaActiva;
    private int diasDeMulta;

    public Usuario(String nombre, String correo, String Telefono, String identificacion) {
        this.nombre = new SimpleStringProperty(nombre);
        this.correo = new SimpleStringProperty(correo);
        this.Telefono = new SimpleStringProperty(Telefono);
        this.identificacion = new SimpleStringProperty(identificacion);
        this.prestamosActivos = 0;
        this.multaActiva = false;
        this.diasDeMulta = 0;
    }

    public String getNombre() { return nombre.get(); }
    public String getCorreo() { return correo.get(); }
    public String getTelefono() { return Telefono.get(); }
    public String getIdentificacion() { return identificacion.get(); }

    public int getPrestamosActivos() { return prestamosActivos; }
    public boolean tieneMulta() { return multaActiva; }
    public int getDiasDeMulta() { return diasDeMulta; }

    public void aumentarPrestamos() { prestamosActivos++; }
    public void disminuirPrestamos() { if (prestamosActivos > 0) prestamosActivos--; }
    public void activarMulta(int dias) {
        multaActiva = true;
        diasDeMulta = dias;
    }
    public void desactivarMulta() {
        multaActiva = false;
        diasDeMulta = 0;
    }
    public void reducirDiasDeMulta() {
        if (diasDeMulta > 0) {
            diasDeMulta--;
            if (diasDeMulta == 0) {
                desactivarMulta();
            }
        }
    }

    public StringProperty nombreProperty() { return nombre; }
    public StringProperty correoProperty() { return correo; }
    public StringProperty telefonoProperty() { return Telefono; }
    public StringProperty identificacionProperty() { return identificacion; }
}

