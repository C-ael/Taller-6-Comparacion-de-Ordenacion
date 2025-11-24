package ed.u2.sorting;

import ed.u2.utils.KeyExtractor;

public class BubbleSort {

    private BubbleSort() {}

    /**
     * Ordena un array genérico 'a' usando el algoritmo Bubble Sort y extrayendo
     * la clave de comparación con 'keyExtractor'.
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

        // Bucle principal para las pasadas. En cada pasada se coloca el elemento más grande al final.
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            // Bucle interno para comparar e intercambiar elementos vecinos en la parte no ordenada.
            for (int j = 0; j < n - 1 - i; j++) {
                // Se extrae la clave de comparación de los elementos vecinos.
                Comparable left = keyExtractor.extract(a[j]);
                Comparable right = keyExtractor.extract(a[j + 1]);
                comparisons++;

                // Compara las claves para determinar si se necesita un intercambio.
                if (left.compareTo(right) > 0) {
                    T tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                    swaps++;
                    swapped = true;
                }
            }

            // Si no hubo swaps en una pasada, el array está ordenado y se detiene el algoritmo.
            if (!swapped) break;
        }

        // Retorna las métricas de desempeño que tuvo el algoritmo.
        return new SortMetrics(comparisons, swaps);
    }
}
