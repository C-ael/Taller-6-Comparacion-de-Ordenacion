package ed.u2.sorting;

/**
 * Resultado completo del benchmarking de un algoritmo de ordenación.
 *
 * @param algorithmName Nombre del algoritmo (p. ej., "BubbleSort").
 * @param datasetName Nombre identificador del dataset.
 * @param datasetType Tipo del dataset: random, nearly_sorted, reversed, duplicates.
 * @param size Tamaño del arreglo procesado.
 * @param comparisons Total de comparaciones realizadas.
 * @param swaps Total de intercambios realizados.
 * @param timeNano Tiempo de ejecución en nanosegundos (mediana de R corridas).
 */
public record BenchmarkResult(String algorithmName, String datasetName, String datasetType, int size, long comparisons, long swaps,
        long timeNano) {

    //Convierte el resultado a formato CSV.
    public String toCsvRow() {
        return String.format("%s;%s;%s;%d;%d;%d;%d", algorithmName, datasetName, datasetType, size, comparisons, swaps, timeNano);
    }

    //Representación en formato tabla para consola.
    @Override
    public String toString() {
        return "%s | %s | %s | N=%d | comp=%d | swaps=%d | time=%dns".formatted(algorithmName, datasetName, datasetType, size,
                comparisons, swaps, timeNano);
    }
}
