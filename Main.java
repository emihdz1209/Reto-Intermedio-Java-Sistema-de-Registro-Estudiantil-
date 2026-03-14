import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    // Lista de nombres de estudiantes registrados.
    static ArrayList<String> nombres = new ArrayList<>();
    // Arreglo de notas.
    static double[] notas = new double[0];
    // Mapa nombre a nota para búsqueda rápida.
    static HashMap<String, Double> mapaEstudiantes = new HashMap<>();
    // Lista de objetos tipo Estudiante.
    static ArrayList<Estudiante> listaEstudiantes = new ArrayList<>();

    //  MAIN MENU
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int opcion;

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

    // Opción 1 — Registrar estudiante
    static void registrarEstudiante(Scanner sc) {
        System.out.println("\n── Registrar Estudiante ──────────────────");

        System.out.print("  Nombre: ");
        String nombre = sc.nextLine().trim();

        System.out.print("  Edad  : ");
        while (!sc.hasNextInt()) {
            System.out.print("  ⚠ Ingrese un número entero para la edad: ");
            sc.next();
        }
        int edad = sc.nextInt();
        sc.nextLine();

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

        // Polimorfismo
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

    // Opción 2 — Ver calificaciones
    static void verCalificaciones() {
        System.out.println("\n── Lista de Calificaciones ───────────────");

        if (listaEstudiantes.isEmpty()) {
            System.out.println("  (No hay estudiantes registrados aún.)");
            return;
        }

        System.out.printf("  %-3s %-20s %-8s %-12s %-10s%n",
                "#", "Nombre", "Nota", "Estado", "Tipo");
        System.out.println("  " + "─".repeat(56));

        for (int i = 0; i < listaEstudiantes.size(); i++) {
            System.out.printf("  %-3d ", i + 1);
            listaEstudiantes.get(i).mostrarInfo(true);
        }
        System.out.println();
    }

    // Opción 3 — Ver estadísticas


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

    // Opción 4 — Buscar estudiante

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

    //Análisis de notas

    static double calcularPromedio(double[] notas) {
        if (notas.length == 0) return 0.0;
        double suma = sumarNotas(notas, notas.length - 1);
        return suma / notas.length;
    }

    static double sumarNotas(double[] notas, int i) {
        if (i == 0) return notas[0];          // Caso base
        return notas[i] + sumarNotas(notas, i - 1); // Caso recursivo
    }

    static String obtenerEstado(double nota) {
        if (nota >= 6.0) {
            return "Aprobado";
        } else {
            return "Reprobado";
        }
    }

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


/*
              --      --
            .:"  | .:'" |
          --  ___   ___  -
        /:.  /  .\ /.  \ .\
       |:|. ;\___/O\___/  :|     Java está mas cool de lo que esperaba 
       |:|. |  `__|__'  | .|
       |:|.  \_,     ,_/  /
        \______       |__/
         |:.           \
        /.:,|  |        \
       /.:,.|  |         \
       |::.. \_;_\-;       |
 _____|::..    .::|       |
/   ----,     .::/__,    /__,
\_______|,...____;_;_|../_;_|

*/