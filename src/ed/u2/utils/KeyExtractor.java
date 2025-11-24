package ed.u2.utils;

// Interfaz funcional que define cómo extraer la clave de ordenación de un objeto T.
@FunctionalInterface
public interface KeyExtractor<T, K> {
    K extract(T t);
}
