package ed.u2.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Clase que representa una cita
// Uso de record para crear clases de datos inmutables de forma concisa.
public record Appointment(String id, String lastName, String dateTimeStr) {

    // Formateador estático y final para definir el patrón fecha/hora.
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    // Convierte la cadena de texto dateTimeStr en un objeto LocalDateTime.
    public LocalDateTime getDateTime() {
        return LocalDateTime.parse(dateTimeStr, FORMATTER);
    }

    @Override
    public String toString() {
        return "%s %s (%s)".formatted(id, lastName, dateTimeStr);
    }
}
