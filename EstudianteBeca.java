/**
 * EstudianteBeca — subclase de Estudiante.
 * Representa a un estudiante con beca académica.
 */
public class EstudianteBeca extends Estudiante {

    /**
     * Constructor de EstudianteBeca.
     * @param nombre Nombre del estudiante.
     * @param edad   Edad del estudiante.
     * @param nota   Calificación entre 0.0 y 10.0.
     */
    public EstudianteBeca(String nombre, int edad, double nota) {
        super(nombre, edad, nota);
    }

    /**
     * Retorna el tipo de estudiante como "Becado".
     * @return Cadena "Becado".
     */
    @Override
    public String obtenerTipo() {
        return "Becado";
    }
}
