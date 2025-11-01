package usedcarsproject.inventoryservice.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

/**
 * Custom LocalDate deserializer that accepts multiple common date formats.
 * Accepted examples: 1-11-24, 1-11-2024, 2024-11-01, or epoch millis (e.g., 1761999911063)
 */
public class MultiFormatLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final List<DateTimeFormatter> FORMATTERS = Arrays.asList(
            DateTimeFormatter.ofPattern("d-M-uu"),     // e.g., 1-11-24
            DateTimeFormatter.ofPattern("d-M-uuuu"),   // e.g., 1-11-2024
            DateTimeFormatter.ofPattern("d/M/uuuu"),   // e.g., 1/11/2024
            DateTimeFormatter.ISO_LOCAL_DATE            // e.g., 2024-11-01
    );

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken token = p.currentToken();
        if (token == null) {
            return null;
        }

        // Support numeric epoch timestamps (millis or seconds)
        if (token.isNumeric()) {
            long value = p.getLongValue();
            // Heuristic: values below 10^12 are likely seconds; otherwise millis
            if (Math.abs(value) < 1_000_000_000_000L) { // seconds
                value = value * 1000L;
            }
            Instant instant = Instant.ofEpochMilli(value);
            return instant.atZone(ZoneId.systemDefault()).toLocalDate();
        }

        // Fallback to string-based parsing
        String text = p.getText();
        if (text == null || text.isBlank()) {
            return null;
        }
        String trimmed = text.trim();
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDate.parse(trimmed, formatter);
            } catch (DateTimeParseException ignored) {
                // Try the next formatter
            }
        }
        throw InvalidFormatException.from(p, "Unsupported date format for LocalDate: " + text, text, LocalDate.class);
    }
}
