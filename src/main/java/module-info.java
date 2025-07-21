module org.example.prueba {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.prueba to javafx.fxml;
    exports org.example.prueba;
}