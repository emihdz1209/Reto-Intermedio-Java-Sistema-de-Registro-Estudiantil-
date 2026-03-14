import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*

SISTEMA ESTUDIANTIL v1.0 — Main.java
Integra: menú, métodos, arreglos, colecciones y jerarquía

 
 Punto de entrada principal del sistema.
 Gestiona el menú y coordina todas las operaciones.

*/


public class Main {

// ── Colecciones globales del sistema ──

    // Lista de nombres de estudiantes registrados.
    static ArrayList<String> nombres = new ArrayList<>();

    // Arreglo paralelo de notas (se reconstruye al registrar).
    static double[] notas = new double[0];

    // Mapa nombre a nota para búsqueda rápida.
    static HashMap<String, Double> mapaEstudiantes = new HashMap<>();

    // Lista de objetos Estudiante para la jerarquía POO.
    static ArrayList<Estudiante> listaEstudiantes = new ArrayList<>();

    //  MÉTODO PRINCIPAL
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion;

        // Bucle principal. no termina hasta que el usuario elija Salir
        do {
            mostrarMenu();
            System.out.print("  Ingrese una opción: ");

            // Validar que la entrada sea un número
            while (!sc.hasNextInt()) {
                System.out.print("  ⚠ Opción inválida. Intente de nuevo: ");
                sc.next();
            }
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 -> registrarEstudiante(sc);
                case 2 -> verCalificaciones();
                case 3 -> verEstadisticas();
                case 4 -> buscarEstudiante(sc);
                case 5 -> System.out.println("\n Logging off...");
                default -> System.out.println("\n Entrada invalida. Elija entre 1 y 5.\n");
            }

        } while (opcion != 5);

        sc.close();
    }

    // ── PARTE 1 — Menú y registro básico ──

    //Imprime el menú principal con formato de tabla.

    static void mostrarMenu() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║     SISTEMA ESTUDIANTIL v1.0     ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║  1. Registrar estudiantes        ║");
        System.out.println("║  2. Ver calificaciones           ║");
        System.out.println("║  3. Ver estadísticas             ║");
        System.out.println("║  4. Buscar estudiante            ║");
        System.out.println("║  5. Salir                        ║");
        System.out.println("╚══════════════════════════════════╝");
    }

    /**
     * Registra un nuevo estudiante pidiendo nombre, edad y nota al usuario.
     * Valida que la nota esté entre 0.0 y 10.0.
     * Permite elegir si es Regular o Becado.
     *
     * @param sc Scanner activo para leer la entrada del usuario.
     */
    static void registrarEstudiante(Scanner sc) {
        System.out.println("\n── Registrar Estudiante ──────────────────");

        // Nombre
        System.out.print("  Nombre: ");
        String nombre = sc.nextLine().trim();

        // Edad
        System.out.print("  Edad  : ");
        while (!sc.hasNextInt()) {
            System.out.print("  ⚠ Ingrese un número entero para la edad: ");
            sc.next();
        }
        int edad = sc.nextInt();
        sc.nextLine();

        // Nota con validación if/else
        double nota = -1;
        while (nota < 0.0 || nota > 10.0) {
            System.out.print("  Nota (0.0 – 10.0): ");
            if (sc.hasNextDouble()) {
                nota = sc.nextDouble();
                sc.nextLine();
                if (nota < 0.0 || nota > 10.0) {
                    System.out.println("La nota debe estar entre 0.0 y 10.0.");
                }
            } else {
                System.out.println("Error. Ingrese un número decimal valido.");
                sc.nextLine();
            }
        }

        // Tipo de estudiante
        System.out.print("  ¿Tipo? (1) Regular  (2) Becado: ");
        while (!sc.hasNextInt()) { sc.next(); }
        int tipo = sc.nextInt();
        sc.nextLine();

        // Crear objeto según tipo usando polimorfismo
        Estudiante estudiante;
        if (tipo == 2) {
            estudiante = new EstudianteBeca(nombre, edad, nota);
        } else {
            estudiante = new EstudianteRegular(nombre, edad, nota);
        }

        // Guardar en las tres estructuras
        nombres.add(nombre);
        mapaEstudiantes.put(nombre, nota);
        listaEstudiantes.add(estudiante);

        // Reconstruir arreglo double[] notas
        notas = new double[nombres.size()];
        for (int i = 0; i < nombres.size(); i++) {
            notas[i] = mapaEstudiantes.get(nombres.get(i));
        }

        System.out.printf("%n  Estudiante \"%s\" registrado como %s con nota %.2f.%n%n",
                nombre, estudiante.obtenerTipo(), nota);
    }

    // ── OPCIÓN 2 — Ver calificaciones ──

    /**
     * Muestra la lista completa de estudiantes con nombre, nota y estado.
     * Usa un bucle for sobre listaEstudiantes y llama a mostrarInfo(boolean).
     */
    static void verCalificaciones() {
        System.out.println("\n── Lista de Calificaciones ───────────────");

        if (listaEstudiantes.isEmpty()) {
            System.out.println("  (No hay estudiantes registrados aún.)");
            return;
        }

        System.out.printf("  %-3s %-20s %-8s %-12s %-10s%n",
                "#", "Nombre", "Nota", "Estado", "Tipo");
        System.out.println("  " + "─".repeat(56));

        // for para recorrer la lista completa
        for (int i = 0; i < listaEstudiantes.size(); i++) {
            System.out.printf("  %-3d ", i + 1);
            listaEstudiantes.get(i).mostrarInfo(true);
        }
        System.out.println();
    }

    // ── OPCIÓN 3 — Ver estadísticas ──

    /**
     * Muestra estadísticas generales del grupo:
     * promedio, nota máxima, nota mínima, aprobados y reprobados.
     */
    static void verEstadisticas() {
        System.out.println("\n── Estadísticas del Grupo ────────────────");

        if (notas.length == 0) {
            System.out.println("  (No hay datos aún. Registre estudiantes primero.)");
            return;
        }

        double promedio   = calcularPromedio(notas);
        double maxima     = notaMasAlta(notas);
        double minima     = notaMasBaja(notas);
        int    aprobados  = contarAprobados(notas);
        int    reprobados = notas.length - aprobados;

        System.out.printf("  Total de estudiantes : %d%n",   notas.length);
        System.out.printf("  Promedio del grupo   : %.2f%n", promedio);
        System.out.printf("  Nota más alta        : %.2f%n", maxima);
        System.out.printf("  Nota más baja        : %.2f%n", minima);
        System.out.printf("  Aprobados            : %d%n",   aprobados);
        System.out.printf("  Reprobados           : %d%n",   reprobados);
        System.out.println();
    }

    // ── OPCIÓN 4 — Buscar estudiante ──

    /**
     * Busca un estudiante por nombre en el HashMap y muestra su nota y estado.
     *
     * @param sc Scanner activo para leer el nombre a buscar.
     */
    static void buscarEstudiante(Scanner sc) {
        System.out.println("\n── Buscar Estudiante ─────────────────────");

        if (mapaEstudiantes.isEmpty()) {
            System.out.println("  (No hay estudiantes registrados aún.)");
            return;
        }

        System.out.print("  Ingrese el nombre a buscar: ");
        String busqueda = sc.nextLine().trim();

        if (mapaEstudiantes.containsKey(busqueda)) {
            double notaEncontrada = mapaEstudiantes.get(busqueda);
            String estado = obtenerEstado(notaEncontrada);

            System.out.println("\n  ✔ Estudiante encontrado:");
            System.out.printf("    Nombre : %s%n", busqueda);
            System.out.printf("    Nota   : %.2f%n", notaEncontrada);
            System.out.printf("    Estado : %s%n", estado);

            // Mostrar también con mostrarInfo(boolean) desde el objeto POO
            for (Estudiante e : listaEstudiantes) {
                if (e.getNombre().equals(busqueda)) {
                    System.out.print("    Info   : ");
                    e.mostrarInfo(true);
                    break;
                }
            }
        } else {
            System.out.printf("%n  ✘ No se encontró al estudiante \"%s\".%n", busqueda);
        }
        System.out.println();
    }

    //  ── PARTE 2 — Métodos estáticos ──

    /**
     * Calcula el promedio de un arreglo de notas.
     * Internamente usa el método recursivo sumarNotas.
     *
     * @param notas Arreglo de calificaciones.
     * @return Promedio aritmético del grupo. Retorna 0.0 si el arreglo está vacío.
     */
    static double calcularPromedio(double[] notas) {
        if (notas.length == 0) return 0.0;
        double suma = sumarNotas(notas, notas.length - 1);
        return suma / notas.length;
    }

    /**
     * Suma los elementos de un arreglo de notas.
     * Caso base: índice 0 retorna el primer elemento.
     * Caso recursivo: suma el elemento en posición i con el resultado de llamarse a sí mismo con i-1.
     *
     * @param notas Arreglo de calificaciones.
     * @param i     Índice actual (comienza en notas.length - 1).
     * @return Suma acumulada de notas[0..i].
     */
    static double sumarNotas(double[] notas, int i) {
        if (i == 0) return notas[0];          // Caso base
        return notas[i] + sumarNotas(notas, i - 1); // Caso recursivo
    }

    /**
     * Determina el estado académico de una nota individual.
     *
     * @param nota Calificación a evaluar.
     * @return "Aprobado" si la nota es >= 6.0, de lo contrario "Reprobado".
     */
    static String obtenerEstado(double nota) {
        if (nota >= 6.0) {
            return "Aprobado";
        } else {
            return "Reprobado";
        }
    }

    /**
     * Encuentra la nota más alta dentro de un arreglo de calificaciones.
     *
     * @param notas Arreglo de calificaciones.
     * @return El valor máximo encontrado. Retorna -1.0 si el arreglo está vacío.
     */
    static double notaMasAlta(double[] notas) {
        if (notas.length == 0) return -1.0;
        double max = notas[0];
        for (int i = 1; i < notas.length; i++) {
            if (notas[i] > max) {
                max = notas[i];
            }
        }
        return max;
    }

    /**
     * Encuentra la nota más baja dentro de un arreglo de calificaciones.
     *
     * @param notas Arreglo de calificaciones.
     * @return El valor mínimo encontrado. Retorna -1.0 si el arreglo está vacío.
     */
    static double notaMasBaja(double[] notas) {
        if (notas.length == 0) return -1.0;
        double min = notas[0];
        for (int i = 1; i < notas.length; i++) {
            if (notas[i] < min) {
                min = notas[i];
            }
        }
        return min;
    }

    /**
     * Cuenta cuántos estudiantes aprobaron (nota >= 6.0).
     *
     * @param notas Arreglo de calificaciones.
     * @return Cantidad de estudiantes con nota aprobatoria.
     */
    static int contarAprobados(double[] notas) {
        int contador = 0;
        for (double nota : notas) {
            if (nota >= 6.0) {
                contador++;
            }
        }
        return contador;
    }
}
