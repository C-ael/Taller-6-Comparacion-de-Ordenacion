package ed.u2.sorting;

// Clase simple para almacenar métricas de un algoritmo de ordenación.
// Contiene: número de comparaciones / número de swaps
public record SortMetrics(long comparisons, long swaps) {

    public SortMetrics {
        // Validación mínima
        if (comparisons < 0 || swaps < 0) {
            throw new IllegalArgumentException("Las métricas no pueden ser negativas.");
        }
    }
}
