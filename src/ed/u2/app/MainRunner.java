package ed.u2.app;

import ed.u2.datasets.CsvDataLoader;
import ed.u2.models.Appointment;
import ed.u2.models.InventoryItem;
import ed.u2.models.Patient;
import ed.u2.sorting.*;
import ed.u2.utils.KeyExtractor;

import java.io.IOException;
import java.time.LocalDateTime;

public class MainRunner {
    public static void main(String[] args) {

        try {
            // 1) CARGA DE DATASETS
            Appointment[] citas100 = CsvDataLoader.loadAppointments("datasets/citas_100.csv");
            Appointment[] citas100Casi = CsvDataLoader.loadAppointments("datasets/citas_100_casi_ordenadas.csv");
            Patient[] pacientes500 = CsvDataLoader.loadPatients("datasets/pacientes_500.csv");
            InventoryItem[] inventario500 = CsvDataLoader.loadInventory("datasets/inventario_500_inverso.csv");

            // 2) EXTRACTORES CORRECTOS
            KeyExtractor<Appointment, LocalDateTime> appointmentKey = ap -> ap.getDateTime();
            KeyExtractor<Patient, Integer> patientKey = p -> p.getPrioridad();
            KeyExtractor<InventoryItem, Integer> inventoryKey = item -> item.getStock();

            // 3) BENCHMARK
            System.out.println("\n=== Citas 100 ===");
            runAllAlgorithms(citas100, "citas_100", "appointments", appointmentKey);

            System.out.println("\n=== Citas 100 Casi Ordenadas ===");
            runAllAlgorithms(citas100Casi, "citas_100_casi", "appointments", appointmentKey);

            System.out.println("\n=== Pacientes 500 ===");
            runAllAlgorithms(pacientes500, "pacientes_500", "patients", patientKey);

            System.out.println("\n=== Inventario 500 Inverso ===");
            runAllAlgorithms(inventario500, "inventario_500", "inventory", inventoryKey);

        } catch (IOException e) {
            System.err.println("Error cargando archivos CSV: " + e.getMessage());
        }
    }

    // Ejecuta los 3 algoritmos sobre un dataset
    private static <T> void runAllAlgorithms(T[] dataset, String datasetName, String datasetType,
            KeyExtractor<T, ? extends Comparable<?>> extractor) {

        BenchmarkResult b1 = SortingUtils.runBenchmark("BubbleSort", datasetName, datasetType, dataset, extractor,
                BubbleSort::sort);

        BenchmarkResult b2 = SortingUtils.runBenchmark("InsertionSort", datasetName, datasetType, dataset, extractor,
                InsertionSort::sort);

        BenchmarkResult b3 = SortingUtils.runBenchmark("SelectionSort", datasetName, datasetType, dataset, extractor,
                SelectionSort::sort);

        System.out.println(b1);
        System.out.println(b2);
        System.out.println(b3);
    }
}
