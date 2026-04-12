package Controllers;

import Models.Paciente;
import Services.PersonService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Locale;


public class AppController {
    @FXML
    private TableView<Paciente> tblPacientes;
    @FXML
    private TableColumn<Paciente, String> colNombre;
    @FXML
    private TableColumn<Paciente, String> colEdad;
    @FXML
    private TableColumn<Paciente, String> colTelefono;
    @FXML
    private TableColumn<Paciente, String> colCurp;
    @FXML
    private TableColumn<Paciente, String> colAlergias;

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
    private final ObservableList<Paciente> data = FXCollections.observableArrayList();
    private Services.PersonService service = new PersonService();

    @FXML
    public void initialize() throws IOException {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCurp.setCellValueFactory(new PropertyValueFactory<>("curp"));
        colAlergias.setCellValueFactory(new PropertyValueFactory<>("alergias"));

        tblPacientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtNombre.setText(newSelection.getNombre());
                txtEdad.setText(newSelection.getEdad());
                txtTelefono.setText(newSelection.getTelefono());
                txtCurp.setText(newSelection.getCurp());
                txtAlergias.setText(newSelection.getAlergias());
            }
        });

        try {
            loadFromFile(); // Llenamos la lista
            tblPacientes.setItems(data);
            System.out.println("Items en la tabla: " + tblPacientes.getItems().size());

        } catch (Exception e) {
            System.out.println("Error al cargar: " + e.getMessage());
        }

    } //se ejecuta  el inicio en cuanto se cargue el  controller

    @FXML
    public void onAgregar() {
        try {
            String Nombre = txtNombre.getText();
            String Edad = txtEdad.getText();
            String Telefono = txtTelefono.getText();
            String Curp = txtCurp.getText();
            String alergias = txtAlergias.getText();
            boolean active = true;
            Paciente nuevo = new Paciente(Nombre, Edad, Telefono, Curp, alergias, active);
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
    public void onEliminar() {


    }


    @FXML
    public void onActualizar() {
        try {

            Paciente seleccionado = tblPacientes.getSelectionModel().getSelectedItem();
            if (seleccionado == null){
                lblMsg.setText("Selecciona un paciente primero");
                return;
            }

            int index = tblPacientes.getSelectionModel().getSelectedIndex();
            String Nombre = txtNombre.getText();
            String Edad = txtEdad.getText();
            String Telefono = txtTelefono.getText();
            String Curp = txtCurp.getText();
            String alergias = txtAlergias.getText();

            Paciente actualizado = new Paciente(Nombre, Edad, Telefono, Curp, alergias, true);

            service.update(index, actualizado);
            lblMsg.setText("Paciente actualzido correcto");
            lblMsg.setStyle("-fx-text-fill: green");

            loadFromFile();

            txtNombre.clear();
            txtEdad.clear();
            txtTelefono.clear();
            txtCurp.clear();
            txtAlergias.clear();

        } catch (IllegalArgumentException ex) {
            lblMsg.setText(ex.getMessage());
            lblMsg.setStyle("-fx-text-fill: red");
        }catch (IOException e){
            lblMsg.setText("Hubo un error con el archivo");
        }
    }


    private void loadFromFile() {
        try {
            data.clear(); //limpia la lista para que no se dupliquen los datos
            List<Paciente> pacientesFromService = service.getAllPacientes(); //se pide la lista de los pacientes al service
            data.addAll(pacientesFromService); //agregamos todos los pacientes a la lista observable
            updateResumen();
            lblMsg.setText("Datos cargados");
        } catch (IOException e) {
            lblMsg.setText("Error al leer el archivo");
        }
        System.out.println("Lista tiene: " + data.size() + " elementos");
for (Paciente p : data){
    System.out.println("cargado: " + p.getNombre());
}
    }

    private void updateResumen(){
        int total = data.size();
        int activos = 0;
        int inactivos = 0;

        for(Paciente p : data){
            if (p.isActive()){
                activos++;
            }else {
                inactivos++;
            }
        }
        lblTotal.setText("Total: " + total);
        lblActivos.setText("Activos: " + activos);
        lblInactivos.setText("Inactivos " + inactivos);
    }
}
