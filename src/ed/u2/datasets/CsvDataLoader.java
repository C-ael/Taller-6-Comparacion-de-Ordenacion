package ed.u2.datasets;

import ed.u2.models.Appointment;
import ed.u2.models.Patient;
import ed.u2.models.InventoryItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Clase de utilidad para cargar datos de archivos CSV en arrays de objetos de modelo.
public class CsvDataLoader {

    // Constante para definir el delimitador usado en los archivos CSV.
    private static final String DELIMITER = ";";

    private CsvDataLoader() {}

    /**
     * Carga datos de citas desde un archivo CSV y los convierte en un array de objetos Appointment.
     * El archivo debe tener el formato: id;apellido;fechaHora
     *
     * @param filename La ruta del archivo CSV.
     * @return Un array de objetos Appointment.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static Appointment[] loadAppointments(String filename) throws IOException {
        List<Appointment> appointments = new ArrayList<>();

        // Uso de try-with-resources para asegurar que el BufferedReader se cierre automáticamente.
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Saltar la primera línea (encabezado del archivo CSV)
            br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                // Ignorar líneas vacías
                if (line.isEmpty()) continue;

                // Divide la línea usando el delimitador (;) y maneja espacios opcionales alrededor.
                String[] values = line.split("\\s*;\\s*");

                // Valida que la línea tenga el número correcto de campos (3 para Appointment: id, apellido, fechaHora)
                if (values.length == 3) {
                    appointments.add(new Appointment(values[0], values[1], values[2]));
                }
            }
        }
        // Convierte la lista dinámica de citas a un array estático (más fácil para algoritmos de ordenación).
        return appointments.toArray(new Appointment[0]);
    }

    /**
     * Carga datos de pacientes desde un archivo CSV y los convierte en un array de objetos Patient.
     * El archivo debe tener el formato: id;nombre;prioridad
     *
     * @param filename La ruta del archivo CSV.
     * @return Un array de objetos Patient.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static Patient[] loadPatients(String filename) throws IOException {
        List<Patient> patients = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Saltar el encabezado

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] values = line.split("\\s*;\\s*");

                // Valida que la línea tenga 3 campos
                if (values.length == 3) {
                    // El campo de prioridad es un entero (se debe parsear).
                    int prioridad = Integer.parseInt(values[2]);
                    patients.add(new Patient(values[0], values[1], prioridad));
                }
            }
        }
        return patients.toArray(new Patient[0]);
    }

    /**
     * Carga datos de inventario desde un archivo CSV y los convierte en un array de objetos InventoryItem.
     * El archivo debe tener el formato: id;nombre;stock
     *
     * @param filename La ruta del archivo CSV.
     * @return Un array de objetos InventoryItem.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public static InventoryItem[] loadInventory(String filename) throws IOException {
        List<InventoryItem> items = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Saltar el encabezado

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] values = line.split("\\s*;\\s*");

                // Valida que la línea tenga 3 campos
                if (values.length == 3) {
                    // El campo de stock es un entero (igual se debe parsear).
                    int stock = Integer.parseInt(values[2]);
                    items.add(new InventoryItem(values[0], values[1], stock));
                }
            }
        }
        return items.toArray(new InventoryItem[0]);
    }
}
