package ed.u2.sorting;

import ed.u2.utils.KeyExtractor;

// Interfaz funcional que representa un algoritmo de ordenación.
@FunctionalInterface
public interface SortingAlgorithm<T> {
    SortMetrics sort(T[] arr, KeyExtractor<T, ? extends Comparable<?>> keyExtractor);
}
