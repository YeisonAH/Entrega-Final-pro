package org.example.prueba;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Libro {
    private final StringProperty titulo;
    private final StringProperty autor;
    private final StringProperty editorial;
    private final StringProperty cantidad;

    public Libro(String titulo, String autor, String editorial, String cantidad) {
        this.titulo = new SimpleStringProperty(titulo);
        this.autor = new SimpleStringProperty(autor);
        this.editorial = new SimpleStringProperty(editorial);
        this.cantidad = new SimpleStringProperty(cantidad);
    }

    public String getTitulo() { return titulo.get(); }
    public String getAutor() { return autor.get(); }
    public String getEditorial() { return editorial.get(); }
    public String getCantidad() { return cantidad.get(); }


    public StringProperty tituloProperty() { return titulo; }
    public StringProperty autorProperty() { return autor; }
    public StringProperty editorialProperty() { return editorial; }
    public StringProperty cantidadProperty() { return cantidad; }

    public void setCantidad(String cantidad) { this.cantidad.set(cantidad); }

}
