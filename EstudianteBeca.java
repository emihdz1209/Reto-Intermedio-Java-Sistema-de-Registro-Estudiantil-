/**
 * EstudianteBeca — subclase de Estudiante.
 * Representa a un estudiante con beca académica.
 */
public class EstudianteBeca extends Estudiante {


    public EstudianteBeca(String nombre, int edad, double nota) {
        super(nombre, edad, nota);
    }

    // Sobrescribe el método obtenerTipo para indicar que es un estudiante becado.
    @Override
    public String obtenerTipo() {
        return "Becado";
    }
}
