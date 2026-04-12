module com.consultorio.utez2dpacientesjavafxequipo { // Revisa que el nombre sea el que tienes
    requires javafx.controls;
    requires javafx.fxml;

    // Esta es la línea mágica que te falta:
    opens Controllers to javafx.fxml;
    opens Models to javafx.base, javafx.fxml;
    // También asegúrate de que el paquete principal esté abierto:
    opens com.consultorio.utez2dpacientesjavafxequipo09 to javafx.fxml;
    exports com.consultorio.utez2dpacientesjavafxequipo09;
}