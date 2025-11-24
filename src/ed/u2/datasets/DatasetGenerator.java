package ed.u2.datasets;

import ed.u2.models.Appointment;
import ed.u2.models.Patient;
import ed.u2.models.InventoryItem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Clase de utilidad para la generación de datasets. Genera archivos CSV para ser utilizados en la práctica.
 * Los archivos se generan en la carpeta "datasets/" de la raíz del proyecto una vez se corra el main de esta misma clase.
 */
public final class DatasetGenerator {
    // Generador de números aleatorios con una semilla fija (42) para asegurar que los datasets generados sean los mismos.
    private static final Random RANDOM = new Random(42);

    // Formateador de fechas y horas.
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    // Ruta donde se guardarán todos los archivos CSV.
    private static final Path DATASETS_DIR = Path.of("datasets");

    private DatasetGenerator() {}

    /**
     * Crea el directorio de datasets si no existe y luego llama a los métodos
     * de generación para crear todos los archivos necesarios.
     * @throws IOException Si falla la creación del directorio o la escritura de archivos.
     */
    public static void generateAllDatasets() throws IOException {
        // Verifica si la carpeta 'datasets' existe; si no, la crea.
        if (!Files.exists(DATASETS_DIR)) {
            Files.createDirectories(DATASETS_DIR);
        }

        // rutas completas para cada archivo CSV.
        Path citas100 = DATASETS_DIR.resolve("citas_100.csv");
        Path citas100Casi = DATASETS_DIR.resolve("citas_100_casi_ordenadas.csv");
        Path pacientes500 = DATASETS_DIR.resolve("pacientes_500.csv");
        Path inventario500 = DATASETS_DIR.resolve("inventario_500_inverso.csv");

        // Llamada a la generación de cada dataset, le indica al usuario.
        generateCitas100Csv(citas100);
        System.out.println("Dataset generado: " + citas100);

        generateCitas100CasiOrdenadasCsv(citas100Casi);
        System.out.println("Dataset generado: " + citas100Casi);

        generatePacientes500Csv(pacientes500);
        System.out.println("Dataset generado: " + pacientes500);

        generateInventario500InversoCsv(inventario500);
        System.out.println("Dataset generado: " + inventario500);
    }

    /**
     * 1) Generador de citas_100.csv (Dataset aleatorio)
     * Genera 100 citas con IDs únicos, apellidos aleatorios y fechas/horas aleatorias.
     */
    public static void generateCitas100Csv(Path out) throws IOException {
        List<Appointment> list = new ArrayList<>(100);

        // Pool de apellidos.
        String[] surnames = new String[] {
                "González","Rodríguez","García","Martínez","López","Hernández","Pérez","Sánchez","Ramírez","Torres",
                "Flores","Rivera","Gómez","Díaz","Vargas","Castillo","Morales","Vásquez","Ramos","Ortiz",
                "Cruz","Guerrero","Naranjo","Cedeño","Benítez","Rojas","Acosta","Mendoza","Salazar","Pacheco"
        };

        for (int i = 1; i <= 100; i++) {
            // Genera un ID de cita con formato CITA-001, CITA-002, etc.
            String id = String.format("CITA-%03d", i);
            // Selecciona un apellido de forma aleatoria.
            String apellido = surnames[RANDOM.nextInt(surnames.length)];

            // Generación de fecha/hora:
            int day = 1 + RANDOM.nextInt(31); // día: 1 a 31
            int hour = 8 + RANDOM.nextInt(10); // hora: 8 a 17 (10 horas posibles)
            int minute = RANDOM.nextInt(6) * 10; // minuto: Múltiplos de 10 (0, 10, 20, 30, 40, 50)

            // Crea el objeto LocalDateTime para marzo de 2025.
            LocalDateTime dt = LocalDateTime.of(2025, 3, day, hour, minute);
            // Formatea la fecha/hora como String.
            String dateTimeStr = dt.format(DATE_FORMATTER);

            list.add(new Appointment(id, apellido, dateTimeStr));
        }

        writeAppointmentsCsv(out, list);
    }

    /**
     * 2) Generador de citas_100_casi_ordenadas.csv (Dataset semi-ordenado)
     * Genera 100 citas, las ordena por fecha/hora y luego introduce 5 intercambios aleatorios para simular un dataset casi ordenado.
     */
    public static void generateCitas100CasiOrdenadasCsv(Path out) throws IOException {
        // Se usa una nueva instancia de Random con semilla fija (42) para asegurar que la generación inicial sea igual a la anterior.
        Random r = new Random(42);

        // mismo pool de apellidos.
        String[] surnames = new String[] {
                "González","Rodríguez","García","Martínez","López","Hernández","Pérez","Sánchez","Ramírez","Torres",
                "Flores","Rivera","Gómez","Díaz","Vargas","Castillo","Morales","Vásquez","Ramos","Ortiz",
                "Cruz","Guerrero","Naranjo","Cedeño","Benítez","Rojas","Acosta","Mendoza","Salazar","Pacheco"
        };

        // Generación inicial idéntica a generateCitas100Csv
        List<Appointment> list = new ArrayList<>(100);
        for (int i = 1; i <= 100; i++) {
            String id = String.format("CITA-%03d", i);
            String apellido = surnames[r.nextInt(surnames.length)];
            int day = 1 + r.nextInt(31);
            int hour = 8 + r.nextInt(10);
            int minute = r.nextInt(6) * 10;
            LocalDateTime dt = LocalDateTime.of(2025, 3, day, hour, minute);
            String dateTimeStr = dt.format(DATE_FORMATTER);
            list.add(new Appointment(id, apellido, dateTimeStr));
        }

        // PASO 1: Ordenar la lista.
        list.sort(Comparator.comparing(a -> LocalDateTime.parse(a.dateTimeStr(), DATE_FORMATTER)));

        // PASO 2: Introducir desorden (casi ordenado). Se hacen 5 intercambios (swaps). 5% del tamaño de la lista.
        int swapsNeeded = 5;
        // Set para asegurar que el mismo par de índices no se intercambie dos veces.
        Set<String> swappedPairs = new HashSet<>();
        int attempts = 0;

        while (swapsNeeded > 0 && attempts < 1000) {
            attempts++;
            int i = r.nextInt(list.size());
            int j = r.nextInt(list.size());

            if (i == j) continue; // No intercambiar un elemento consigo mismo.

            // Crea una clave única para el par (índice menor-índice mayor).
            int a = Math.min(i, j);
            int b = Math.max(i, j);
            String key = a + "-" + b;

            if (swappedPairs.contains(key)) continue; // Evita repetir el par.

            Collections.swap(list, a, b); // Realiza el intercambio.
            swappedPairs.add(key);
            swapsNeeded--;
        }

        writeAppointmentsCsv(out, list);
    }

    /**
     * 3) Generador de pacientes_500.csv (Dataset con sesgo)
     * Genera 500 pacientes con IDs únicos y un sesgo en los apellidos, donde algunos apellidos son más frecuentes.
     */
    public static void generatePacientes500Csv(Path out) throws IOException {
        List<Patient> list = new ArrayList<>(500);

        // Pool de 50 apellidos. Se repiten los primeros para crear un sesgo.
        String[] pool = new String[] {
                "Ramírez","Ramírez","Ramírez","Ramírez","González","González","González","González","Pérez","Pérez",
                "López","Hernández","Sánchez","Rodríguez","García","Martínez","Flores","Rivera","Gómez","Díaz",
                "Vargas","Castillo","Morales","Vásquez","Ramos","Ortiz","Cruz","Guerrero","Naranjo","Cedeño",
                "Benítez","Rojas","Acosta","Mendoza","Salazar","Pacheco","Soto","Valencia","Navarro","Suárez",
                "Lozada","Camacho","Arias","Bravo","Montero","Silva","León","Ibarra","Pérez-G","Montaño"
        };

        Random r = new Random(42);
        for (int i = 1; i <= 500; i++) {
            String id = String.format("PAC-%04d", i);
            int pick = r.nextInt(100); // Num aleatorio entre 0 y 99
            String apellido;

            // Lógica de sesgo:
            if (pick < 40) { // 40% de probabilidad (0-39)
                // Se elige de los primeros 10 apellidos (más comunes)
                apellido = pool[r.nextInt(10)];
            } else if (pick < 70) { // 30% de probabilidad (40-69)
                // Se elige de los siguientes 20 apellidos
                apellido = pool[10 + r.nextInt(20)];
            } else { // 30% de probabilidad (70-99)
                // Se elige de los 20 apellidos restantes (menos comunes)
                apellido = pool[30 + r.nextInt(20)];
            }

            // Prioridad aleatoria entre 1 y 3 (1 = Alta, 2 = Media, 3 = Baja).
            int prioridad = 1 + r.nextInt(3);
            list.add(new Patient(id, apellido, prioridad));
        }

        writePatientsCsv(out, list);
    }

    /**
     * 4) Generador de inventario_500_inverso.csv (Dataset ordenado inversamente)
     * Genera 500 items de inventario con IDs únicos y stock en orden descendente. Inversamente ordenado (500, 499, ..., 1).
     */
    public static void generateInventario500InversoCsv(Path out) throws IOException {
        List<InventoryItem> list = new ArrayList<>(500);

        // Pool de 20 insumos que se repiten cíclicamente.
        String[] insumos = new String[] {
                "Guante Nitrilo Talla M","Alcohol 70% 1L","Gasas 10x10","Mascarilla Quirúrgica",
                "Jeringa 5ml","Catéter 14G","Venda Elástica","Termómetro Digital","Bata Desechable",
                "Papel Gasas","Alcohol 70% 500ml","Tiritas","Compresas Estériles","Guantes Látex Talla L",
                "Gasa Hidrocoloide","Silla Ruedas Plegable","Bolsa Suero","Tubos Ensayo","Aguja 21G","Solución Salina 0.9%"
        };

        for (int i = 0; i < 500; i++) {
            // Genera ID ITEM-0001, ITEM-0002, etc.
            String id = String.format("ITEM-%04d", i + 1);
            // El insumo se selecciona cíclicamente (i % insumos.length).
            String insumo = insumos[i % insumos.length];
            // El stock se asigna en orden inverso.
            int stock = 500 - i; // Genera valores: 500, 499, 498, ..., 1
            list.add(new InventoryItem(id, insumo, stock));
        }

        writeInventoryCsv(out, list);
    }

    //Escribe la lista de citas en el archivo CSV, incluyendo el encabezado.
    private static void writeAppointmentsCsv(Path out, List<Appointment> list) throws IOException {
        // try-with-resources para que el writter se cierre.
        try (BufferedWriter w = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            w.write("id;apellido;fechaHora"); // Escribe el encabezado.
            w.newLine(); // Salto de línea.
            for (Appointment a : list) {
                // Escribe los campos de la cita separados por punto y coma.
                w.write(String.format("%s;%s;%s", a.id(), a.lastName(), a.dateTimeStr()));
                w.newLine();
            }
        }
    }

    // Escribe la lista de pacientes en el archivo CSV, incluyendo el encabezado.
    private static void writePatientsCsv(Path out, List<Patient> list) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            w.write("id;apellido;prioridad"); // Escribe el encabezado.
            w.newLine();
            for (Patient p : list) {
                // Escribe los campos del paciente. La prioridad se formatea como entero (%d).
                w.write(String.format("%s;%s;%d", p.getId(), p.getApellido(), p.getPrioridad()));
                w.newLine();
            }
        }
    }

    //Escribe la lista de inventario en el archivo CSV, incluyendo el encabezado.
    private static void writeInventoryCsv(Path out, List<InventoryItem> list) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(out, StandardCharsets.UTF_8)) {
            w.write("id;insumo;stock"); // Escribe el encabezado.
            w.newLine();
            for (InventoryItem it : list) {
                // Escribe los campos del inventario. El stock se formatea como entero (%d).
                w.write(String.format("%s;%s;%d", it.getId(), it.getInsumo(), it.getStock()));
                w.newLine();
            }
        }
    }

    // Quick runner para generar los datasets (de forma rapida)
    public static void main(String[] args) throws IOException {
        generateAllDatasets();
    }
}
