package Controllers;

import Models.Paciente;
import Services.PersonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Utils.DataHolder;

import java.io.IOException;
import java.util.List;

public class AppController {

    @FXML
    private Label lblMsg;

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEdad;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtCurp;
    @FXML
    private TextField txtAlergias;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblActivos;
    @FXML
    private Label lblInactivos;
    @FXML
    private Consulta consultaController;

    private final ObservableList<Paciente> data = FXCollections.observableArrayList();
    private PersonService service = new PersonService();

    @FXML
    public void initialize() {
        // Ya no usamos tabla aquí
    }

    @FXML
    public void onAgregar() {
        try {
            String Nombre = txtNombre.getText();
            String Edad = txtEdad.getText();
            String Telefono = txtTelefono.getText();
            String Curp = txtCurp.getText();
            String alergias = txtAlergias.getText();

            Paciente nuevo = new Paciente(Nombre, Edad, Telefono, Curp, alergias, true);
            service.addPaciente(nuevo);

            lblMsg.setText("Paciente agregado");

            txtNombre.clear();
            txtEdad.clear();
            txtTelefono.clear();
            txtCurp.clear();
            txtAlergias.clear();

            loadFromFile();

        } catch (IOException e) {
            lblMsg.setText("Hubo un error");
        } catch (IllegalArgumentException e) {
            lblMsg.setText(e.getMessage());
        }
    }

    @FXML
    public void onActualizar() {
        try {

            if (DataHolder.pacienteSeleccionado == null) {
                lblMsg.setText("Selecciona un paciente en la tabla primero");
                return;
            }


            int index = DataHolder.indexSeleccionado;

            String Nombre = txtNombre.getText();
            String Edad = txtEdad.getText();
            String Telefono = txtTelefono.getText();
            String Curp = txtCurp.getText();
            String alergias = txtAlergias.getText();

            Paciente actualizado = new Paciente(Nombre, Edad, Telefono, Curp, alergias, true);

            service.update(index, actualizado);

            lblMsg.setText("Paciente actualizado correctamente");

            txtNombre.clear();
            txtEdad.clear();
            txtTelefono.clear();
            txtCurp.clear();
            txtAlergias.clear();

            loadFromFile();

        } catch (Exception e) {
            lblMsg.setText("Error al actualizar");
        }
        DataHolder.pacienteSeleccionado = null;
        DataHolder.indexSeleccionado = -1;
    }

    @FXML
    public void onEliminar() {
        try {

            // 🔥 ahora usamos DataHolder en vez de la tabla
            if (DataHolder.pacienteSeleccionado == null) {
                lblMsg.setText("Selecciona un paciente primero");
                return;
            }

            int index = DataHolder.indexSeleccionado;
            Paciente seleccionado = DataHolder.pacienteSeleccionado;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Inactivacion");
            alert.setHeaderText("Confirmacion para inactivar a: " + seleccionado.getNombre() + "?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                service.deletePaciente(index);

                loadFromFile();
                lblMsg.setText("Paciente inactivado correctamente");
            }

            txtNombre.clear();
            txtEdad.clear();
            txtTelefono.clear();
            txtCurp.clear();
            txtAlergias.clear();

            loadFromFile();

            // 🔥 limpiar selección
            DataHolder.pacienteSeleccionado = null;
            DataHolder.indexSeleccionado = -1;

        } catch (IOException e) {
            lblMsg.setText("Error al guardar los cambios");
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        try {
            data.clear();
            List<Paciente> pacientesFromService = service.getAllPacientes();
            data.addAll(pacientesFromService);
            updateResumen();
            lblMsg.setText("Datos cargados");
        } catch (IOException e) {
            lblMsg.setText("Error al leer el archivo");
        }
    }

    private void updateResumen() {
        int total = data.size();
        int activos = 0;
        int inactivos = 0;

        for (Paciente p : data) {
            if (p.isActive()) {
                activos++;
            } else {
                inactivos++;
            }
        }

        lblTotal.setText("Total: " + total);
        lblActivos.setText("Activos: " + activos);
        lblInactivos.setText("Inactivos " + inactivos);
    }

    @FXML
    public void abrirTabla() {
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(
                    getClass().getResource("/com/consultorio/utez2dpacientesjavafxequipo09/consulta.fxml")
            );

            Parent root = loader.load();
            Consulta controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Pacientes");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
        consultaController = loader.getController();

    }

    @FXML
    public void cargarSeleccion() {

        if (Utils.DataHolder.pacienteSeleccionado != null) {

            Paciente p = Utils.DataHolder.pacienteSeleccionado;

            txtNombre.setText(p.getNombre());
            txtEdad.setText(p.getEdad());
            txtTelefono.setText(p.getTelefono());
            txtCurp.setText(p.getCurp());
            txtAlergias.setText(p.getAlergias());

            lblMsg.setText("Paciente cargado para editar");
        }
    }
}