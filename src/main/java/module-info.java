module com.consultorio.utez2dpacientesjavafxequipo09 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.consultorio.utez2dpacientesjavafxequipo09 to javafx.fxml;
    exports com.consultorio.utez2dpacientesjavafxequipo09;
}