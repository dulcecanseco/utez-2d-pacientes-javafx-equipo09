package Models;

public class Paciente {
    private String nombre;
    private String curp;
    private String edad;
    private String telefono;
    private String alergias;
    private boolean active;

    public Paciente(String nombre, String edad, String telefono, String curp, String alergias, Boolean active){
        this.nombre = nombre;
        this.edad = edad;
        this.telefono = telefono;
        this.curp = curp;
        this.alergias = alergias;
        this.active = active;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return nombre + "," + edad + "," + telefono + "," + curp  + "," + alergias + "," + active;
    }
}
