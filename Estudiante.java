/**
 * Clase Estudiante — extiende Persona.
 * Agrega la nota académica y lógica de aprobación.
 * Demuestra sobrecarga de método (mostrarInfo).
 */
public class Estudiante extends Persona {

    private double nota;

    /**
     * Constructor de Estudiante.
     * @param nombre Nombre del estudiante.
     * @param edad   Edad del estudiante.
     * @param nota   Calificación entre 0.0 y 10.0.
     */
    public Estudiante(String nombre, int edad, double nota) {
        super(nombre, edad);
        this.nota = nota;
    }

    // ── Getter / Setter ───────────────────────────────────────────────────────

    /** Retorna la nota del estudiante. */
    public double getNota() { return nota; }

    /** Establece la nota del estudiante. */
    public void setNota(double nota) { this.nota = nota; }

    // ── Lógica académica ─────────────────────────────────────────────────────

    /**
     * Determina si el estudiante aprobó o reprobó según su nota.
     * @return "Aprobado" si la nota es >= 6.0, de lo contrario "Reprobado".
     */
    public String obtenerEstado() {
        return (nota >= 6.0) ? "Aprobado" : "Reprobado";
    }

    /**
     * Retorna el tipo de estudiante. Las subclases sobreescriben este método.
     * @return Tipo genérico de estudiante.
     */
    public String obtenerTipo() {
        return "Estudiante";
    }

    // ── Sobrecarga de mostrarInfo ─────────────────────────────────────────────

    /**
     * Muestra el nombre y la nota del estudiante (sin estado).
     */
    public void mostrarInfo() {
        System.out.printf("  %-20s | Nota: %.2f | Tipo: %-10s%n",
                getNombre(), nota, obtenerTipo());
    }

    /**
     * Muestra el nombre, la nota y, opcionalmente, el estado (Aprobado/Reprobado).
     * @param mostrarEstado Si es true, incluye el estado académico en la salida.
     */
    public void mostrarInfo(boolean mostrarEstado) {
        if (mostrarEstado) {
            System.out.printf("  %-20s | Nota: %.2f | %-10s | Tipo: %-10s%n",
                    getNombre(), nota, obtenerEstado(), obtenerTipo());
        } else {
            mostrarInfo();
        }
    }

    /**
     * Representación en cadena del estudiante.
     * @return Cadena con datos del estudiante.
     */
    @Override
    public String toString() {
        return super.toString() + " | Nota: " + nota + " | " + obtenerEstado();
    }
}
