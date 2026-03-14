/**
 * EstudianteRegular — subclase de Estudiante.
 * Representa a un estudiante sin beca académica.
 */
public class EstudianteRegular extends Estudiante {

    /**
     * Constructor de EstudianteRegular.
     * @param nombre Nombre del estudiante.
     * @param edad   Edad del estudiante.
     * @param nota   Calificación entre 0.0 y 10.0.
     */
    public EstudianteRegular(String nombre, int edad, double nota) {
        super(nombre, edad, nota);
    }

    /**
     * Retorna el tipo de estudiante como "Regular".
     * @return Cadena "Regular".
     */
    @Override
    public String obtenerTipo() {
        return "Regular";
    }
}
