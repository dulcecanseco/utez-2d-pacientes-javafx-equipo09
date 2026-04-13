package Repositories;
import Models.Paciente;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class PersonFileRepository {

    private final Path filePath = Paths.get("data","pacientes.txt");  //esto es donde se van a guardar los datos

    private void ensureFileExist() throws IOException { //Revisa si existen la carpeta y el archivo. Es para que no truene el programa al iniciar
        if(Files.notExists(filePath.getParent())){
            Files.createDirectories(filePath.getParent());
        }
        if(Files.notExists(filePath)){
            Files.createFile(filePath);
        }
    }

    public List<Paciente> findAll() throws IOException { //Este metodo hace que se llene la tabla de pacientes
        ensureFileExist();
        List<String> lineas = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        List<Paciente> listaPacientes = new ArrayList<>();
        for (String linea : lineas) {
            String[] datos = linea.split(",");
            if(datos.length >= 6){
                Paciente p = new Paciente(datos[0], datos[1], datos[2], datos[3], datos[4], Boolean.parseBoolean(datos[5] ));
                listaPacientes.add(p);
            }
        }
        return listaPacientes;
    }

    // Este metodo recibe la lista de pacientes y la guarda, se activa cuando agregas, editas o eliminas
    public void saveAll(List<Paciente> pacientes) throws IOException {  //recibe pacientes los convierte a texto con el toString y los guarda.
        List<String> lineas = new ArrayList<>();
        for (Paciente p : pacientes) {
            lineas.add(p.toString()); // Usamos el toString que creamos en el paciente.java
        }

        Files.write(filePath, lineas, StandardCharsets.UTF_8,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.CREATE);
    }
}