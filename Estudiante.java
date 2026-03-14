/**
 * Clase Estudiante — extiende Persona.
 * Agrega la nota académica y lógica de aprobación.
 * Demuestra sobrecarga de método (mostrarInfo).
 */
public class Estudiante extends Persona {

    private double nota;

    public Estudiante(String nombre, int edad, double nota) { //Constructor de Estudiante que recibe nombre, edad y nota.
        super(nombre, edad);
        this.nota = nota;
    }

    public double getNota() { return nota; }

    public void setNota(double nota) { this.nota = nota; }

    public String obtenerEstado() {
        return (nota >= 6.0) ? "Aprobado" : "Reprobado";
    }

    public String obtenerTipo() {
        return "Estudiante";
    }

    public void mostrarInfo() {
        System.out.printf("  %-20s | Nota: %.2f | Tipo: %-10s%n",
                getNombre(), nota, obtenerTipo());
    }

    public void mostrarInfo(boolean mostrarEstado) {
        if (mostrarEstado) {
            System.out.printf("  %-20s | Nota: %.2f | %-10s | Tipo: %-10s%n",
                    getNombre(), nota, obtenerEstado(), obtenerTipo());
        } else {
            mostrarInfo();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " | Nota: " + nota + " | " + obtenerEstado();
    }
}
