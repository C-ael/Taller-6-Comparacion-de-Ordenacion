package ed.u2.sorting;

import ed.u2.utils.KeyExtractor;
import java.util.Arrays;

public class SortingUtils {

    private SortingUtils() {}

    // Intercambia dos elementos del arreglo genérico T en las posiciones dadas.
    public static <T> void swap(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // Verifica si el arreglo genérico T (que debe ser Comparable) está ordenado de forma ascendente.
    public static <T extends Comparable<T>> boolean isSorted(T[] a) {
        if (a == null || a.length < 2) return true;

        for (int i = 0; i < a.length - 1; i++) {
            if (a[i].compareTo(a[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    // Crea y devuelve una copia superficial del arreglo original.
    @SuppressWarnings("unchecked")
    public static <T> T[] copy(T[] original) {
        return (original == null) ? null : (T[]) original.clone();
    }

    // Ejecuta el algoritmo de ordenación R veces para obtener métricas y tiempos confiables.
    public static <T> BenchmarkResult runBenchmark(String algorithmName, String datasetName, String datasetType, T[] original,
                                                   KeyExtractor<T, ? extends Comparable<?>> keyExtractor, SortingAlgorithm<T> algorithm) {

        final int R = 10; // Número de repeticiones para la medición.
        long[] times = new long[R];
        long totalComparisons = 0;
        long totalSwaps = 0;

        // Bucle de ejecuciones: corre el algoritmo R veces para obtener promedios.
        for (int i = 0; i < R; i++) {
            T[] copy = copy(original); // Utiliza una copia para no alterar el arreglo original.

            long start = System.nanoTime();
            SortMetrics metrics = algorithm.sort(copy, keyExtractor); // Ejecuta el algoritmo de ordenación.
            long end = System.nanoTime();

            times[i] = end - start;
            totalComparisons += metrics.comparisons(); // Acumula el total de comparaciones.
            totalSwaps += metrics.swaps(); // Acumula el total de intercambios.
        }

        // Descarta las primeras 3 corridas (fase de "calentamiento") y calcula la mediana del tiempo.
        long[] valid = Arrays.copyOfRange(times, 3, R);
        Arrays.sort(valid);
        long median = valid[valid.length / 2];

        // Retorna el resultado, promediando las métricas de todas las corridas.
        return new BenchmarkResult(algorithmName, datasetName, datasetType, original.length, totalComparisons / R,
                totalSwaps / R, median);
    }
}
