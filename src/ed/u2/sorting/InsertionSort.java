package ed.u2.sorting;

import ed.u2.utils.KeyExtractor;

public class InsertionSort {

    private InsertionSort() {}

    /**
     * Ordena un array genérico 'a' usando el algoritmo Insertion Sort, extrayendo
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

        // Bucle principal: recorre el array desde el segundo elemento (i=1), considerando que la sublista a[0...i-1] ya está ordenada.
        for (int i = 1; i < n; i++) {
            // Guarda el elemento actual a insertar en la parte ordenada.
            T keyElem = a[i];
            // Extrae la clave de comparación para el elemento actual.
            Comparable<Object> keyVal = (Comparable<Object>) keyExtractor.extract(keyElem);

            int j = i - 1;

            // compara keyVal con elementos previos en la sublista ordenada y desplaza los mayores una posición a la derecha.
            while (j >= 0) {
                comparisons++;

                // Extrae la clave del elemento en la posición j para la comparación.
                Comparable<Object> current =
                        (Comparable<Object>) keyExtractor.extract(a[j]);

                // Si el elemento actual (a[j]) es mayor que keyVal, se desplaza a la derecha.
                if (current.compareTo(keyVal) > 0) {
                    a[j + 1] = a[j];
                    swaps++;
                    j--; // Se mueve a la posición anterior para seguir comparando.
                } else {
                    // Si el elemento actual es menor o igual a keyVal, entonces ha encontrado su posición.
                    break;
                }
            }

            // Coloca el elemento keyElem en su posición final dentro de la sublista ordenada.
            a[j + 1] = keyElem;
        }

        // Retorna las métricas de desempeño que tuvo el algoritmo.
        return new SortMetrics(comparisons, swaps);
    }
}
