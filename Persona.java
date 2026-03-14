
//Clase base que representa a una persona con nombre y edad.
//Sirve como superclase para la jerarquía de estudiantes.

public class Persona {

    private String nombre;
    private int edad;

    /**
     * Constructor de Persona.
     * @param nombre Nombre completo de la persona.
     * @param edad   Edad de la persona.
     */
    public Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad   = edad;
    }

    // ── Getters
    // Retorna el nombre de la persona.
    public String getNombre() { return nombre; }

    // Retorna la edad de la persona.
    public int getEdad() { return edad; }

    // ── Setters
    // Establece el nombre de la persona.
    public void setNombre(String nombre) { this.nombre = nombre; }

    // Establece la edad de la persona.
    public void setEdad(int edad) { this.edad = edad; }

    /**
     * Representación en cadena de la persona.
     * @return Cadena con nombre y edad.
     */
    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Edad: " + edad;
    }
}
