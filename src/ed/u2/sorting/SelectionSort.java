package ed.u2.sorting;

import ed.u2.utils.KeyExtractor;

public class SelectionSort {

    private SelectionSort() {}

    /**
     * Ordena un array genérico 'a' usando el algoritmo Selection Sort (Ordenación por Selección),
     * extrayendo la clave de comparación con 'keyExtractor'.
     *
     * @param <T> El tipo de elementos en el array.
     * @param a El array a ordenar in-place.
     * @param keyExtractor Función para obtener la clave comparable de cada elemento T.
     * @return Un objeto SortMetrics que contiene el número total de comparaciones y swaps.
     */
    public static <T> SortMetrics sort(T[] a, KeyExtractor<T, ? extends Comparable<?>> keyExtractor) {

        // Retorna métricas cero si el array es nulo o tiene menos de 2 elementos.
        if (a == null || a.length < 2) {
            return new SortMetrics(0, 0);
        }

        long comparisons = 0;
        long swaps = 0;
        int n = a.length;

        // recorre el array hasta el penúltimo elemento.
        // i marca el inicio de la sublista no ordenada y la posición correcta para el mínimo.
        for (int i = 0; i < n - 1; i++) {

            // Inicializa el índice del mínimo y el valor mínimo encontrado hasta ahora.
            int minIndex = i;
            Comparable<Object> minValue = (Comparable<Object>) keyExtractor.extract(a[minIndex]);

            // busca el elemento más pequeño en la sublista no ordenada (desde i + 1).
            for (int j = i + 1; j < n; j++) {

                // Extrae la clave del elemento actual para la comparación.
                Comparable<Object> current = (Comparable<Object>) keyExtractor.extract(a[j]);
                comparisons++; // Cada vez que se compara, se incrementa el contador.

                // Si el elemento actual es menor que el mínimo encontrado hasta ahora, se actualiza el índice y el valor mínimo.
                if (current.compareTo(minValue) < 0) {
                    minIndex = j;
                    minValue = current; // Actualiza el valor mínimo para las comparaciones futuras.
                }
            }

            // Después de encontrar el mínimo, se realiza el intercambio si es necesario.
            // Si minIndex es diferente de i, significa que se encontró un elemento menor.
            if (minIndex != i) {
                // Intercambio (swap) del elemento en i con el elemento mínimo encontrado en 'minIndex'.
                T tmp = a[i];
                a[i] = a[minIndex];
                a[minIndex] = tmp;
                swaps++;
            }
        }

        // Retorna las métricas de desempeño (comparaciones y swaps).
        return new SortMetrics(comparisons, swaps);
    }
}
