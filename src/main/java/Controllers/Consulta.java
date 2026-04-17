package Controllers;

import Models.Paciente;
import Services.PersonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Utils.DataHolder;

import java.io.IOException;
import java.util.List;

public class Consulta { //Aqui se traen los campos de la tabla para mostrarlos

    @FXML private TableView<Paciente> tblPacientes;
    @FXML private TableColumn<Paciente, String> colNombre;
    @FXML private TableColumn<Paciente, String> colEdad;
    @FXML private TableColumn<Paciente, String> colTelefono;
    @FXML private TableColumn<Paciente, String> colCurp;
    @FXML private TableColumn<Paciente, String> colAlergias;
    @FXML private TableColumn<Paciente, Boolean> colEstatus;
    @FXML private Label lblMsg;

    private final ObservableList<Paciente> data = FXCollections.observableArrayList();
    private final PersonService service = new PersonService();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCurp.setCellValueFactory(new PropertyValueFactory<>("curp"));
        colAlergias.setCellValueFactory(new PropertyValueFactory<>("alergias"));
        colEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));
        colEstatus.setCellValueFactory(new PropertyValueFactory<>("active"));

        colEstatus.setCellFactory(column -> new TableCell<Paciente, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) { //Aqui se pasan los datos booleanos a texto para mostrar el status del paciente
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Activo" : "Inactivo");
                }
            }
        });

        loadFromFile();
        tblPacientes.setItems(data);

        tblPacientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                DataHolder.pacienteSeleccionado = newSel;
                DataHolder.indexSeleccionado = tblPacientes.getSelectionModel().getSelectedIndex();
            }
        });
    }

    private void loadFromFile() {
        try {
            data.clear();
            List<Paciente> lista = service.getAllPacientes();
            data.addAll(lista);
        } catch (IOException e) {
            lblMsg.setText("Error al cargar");
        }
    }
}