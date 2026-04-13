package Services;
import Models.Paciente;
import Repositories.PersonFileRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonService{
    private final PersonFileRepository repository = new PersonFileRepository(); // Traemos el repositorio para usar sus metodos

    public void addPaciente(Paciente p) throws IOException {
        validarPaciente(p);
        List<Paciente> actuales = repository.findAll();
        actuales.add(p);
        repository.saveAll(actuales);
    }

    public void deletePaciente(int index) throws IOException {
        List<Paciente> actuales = repository.findAll();

        if (index >= 0 && index < actuales.size()) {
            // Obtenemos el paciente de esa posicion y lo desactivamos en lugar de eliminar la linea del archivo
            actuales.get(index).setActive(false);

            // Guardamos la lista con el cambio hecho
            repository.saveAll(actuales);
        }
    }

    private void validarPaciente(Paciente p) throws IOException {
validarFormato(p);

        List<Paciente> actuales = repository.findAll();
        boolean existe = actuales.stream().anyMatch(existente -> existente.getCurp()
                .equalsIgnoreCase(p.getCurp()));
        if (existe){
            throw new IllegalArgumentException("El curp ya existe");
        }
    }
    public List<Paciente> getAllPacientes() throws IOException {
        // Le pedimos al repo todos los que existan en el archivo
        return repository.findAll();
    }

    public void update(int index, Paciente actualizado)throws IOException {
   List<Paciente> actuales = repository.findAll();

   if(index >= 0 && index < actuales.size()){
       validarFormato(actualizado);

       String curpOriginal = actuales.get(index).getCurp();
       if (!curpOriginal.equalsIgnoreCase(actualizado.getCurp())){
           boolean existe = actuales.stream().anyMatch(e -> e.getCurp()
                   .equalsIgnoreCase(actualizado.getCurp()));
           if (existe) throw new IllegalArgumentException("El nuevo curp ya existe");
       }
       actuales.set(index,actualizado);
       repository.saveAll(actuales);
       }
    }


    private void validarFormato(Paciente p){
        if (p.getNombre().isBlank() || p.getNombre().length() < 5){
            throw new IllegalArgumentException("Nombre muy corto");
        }
        if (p.getTelefono() == null || p.getTelefono().trim().length() != 10){
            throw new IllegalArgumentException("El telefono debe ser de 10 digitos");
        }
        try {
            int edadNum = Integer.parseInt(p.getEdad().trim());
            if (edadNum < 0 || edadNum > 120) throw new IllegalArgumentException("Edad fuera de rango");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La edad debe ser número");
        }
    }
}


