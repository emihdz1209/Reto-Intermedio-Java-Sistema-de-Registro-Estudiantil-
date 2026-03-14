/**
 * EstudianteRegular — subclase de Estudiante.
 * Representa a un estudiante sin beca académica.
 */
public class EstudianteRegular extends Estudiante {

    // Constructor de EstudianteRegular.
    public EstudianteRegular(String nombre, int edad, double nota) {
        super(nombre, edad, nota);
    }

    // Sobrescribe el método obtenerTipo para indicar que es un estudiante regular.
    @Override
    public String obtenerTipo() {
        return "Regular";
    }
}
