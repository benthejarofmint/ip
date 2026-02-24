package cheriie.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a trackable task that must be completed by a specific time boundary.
 * Extends the base Task class to support complex date and time parsing, allowing for flexible user input formats.
 */
public class Deadline extends Task {

    protected String by;
    protected String originalBy;
    protected ParsedDateTime dueDateTime;

    // credits to NCF3535 in helping me implement Dates and Times
    private static final DateTimeFormatter[] DATETIME_FORMATS = {
            // date and time with various separators and formats
            DateTimeFormatter.ofPattern("d/M/yyyy Hmm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy Hmm"),
            DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("d-M-yyyy Hmm"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy Hmm"),
            DateTimeFormatter.ofPattern("d-M-yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd Hmm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
            // include patterns with AM/PM
            DateTimeFormatter.ofPattern("d/M/yyyy h:mma"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy h:mma"),
            DateTimeFormatter.ofPattern("d-M-yyyy h:mma"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy h:mma"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd h:mma"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy h:mma"),
            // include handle hours without minutes
            DateTimeFormatter.ofPattern("d/M/yyyy ha"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy ha"),
            DateTimeFormatter.ofPattern("d-M-yyyy ha"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy ha"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd ha"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy ha"),
    };

    private static final DateTimeFormatter[] DATE_FORMATS = {
            // Date only with various separators
            DateTimeFormatter.ofPattern("d/M/yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("d-M-yyyy"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy"),
    };

    private static final DateTimeFormatter[] TIME_FORMATS = {
            // Time only in various formats
            DateTimeFormatter.ofPattern("Hmm"),
            DateTimeFormatter.ofPattern("HHmm"),
            DateTimeFormatter.ofPattern("H:mm"),
            DateTimeFormatter.ofPattern("HH:mm"),
            DateTimeFormatter.ofPattern("h:mma"),
    };

    // Basic formats for displaying
    private static final DateTimeFormatter DISPLAY_DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM yyyy");
    private static final DateTimeFormatter DISPLAY_TIME_FORMAT = DateTimeFormatter.ofPattern("h:mma");



    /**
     * Initializes a new deadline task and attempts to interpret its time constraints.
     * Preserves the raw user input while simultaneously trying to parse it
     * into a structured date object for better UI formatting.
     *
     * @param description The core text detailing what the task entails.
     * @param by The raw string representing the deadline boundary provided by the user.
     */
    public Deadline(String description, String by) {
        super(description);
        this.originalBy = by;
        this.dueDateTime = parseDateTime(by);  // try to parse the input
        if (this.dueDateTime == null) {
            this.by = by;  // if the parsing fails, fallback and treat it as a string
        }
    }

    /**
     * Attempts to convert a raw string into a structured temporal object against predefined format arrays.
     * Grants the application high flexibility, allowing users to input dates and times
     * in whichever format they prefer without causing crashes.
     *
     * @param dateTimeStr The raw string to be parsed.
     * @return A {@code ParsedDateTime} object if a format matches, or null if the string cannot be interpreted as a date/time.
     */
    private ParsedDateTime parseDateTime(String dateTimeStr) {
        String parsingStr = dateTimeStr.toUpperCase();
        // try to parse as LocalDateTime
        for (DateTimeFormatter formatter : DATETIME_FORMATS) {
            try {
                LocalDateTime dt = LocalDateTime.parse(parsingStr, formatter);
                return new ParsedDateTime(dt);
            } catch (DateTimeParseException e) {
                // ignore and continue
            }
        }
        // try to parse as LocalDate
        for (DateTimeFormatter formatter : DATE_FORMATS) {
            try {
                LocalDate date = LocalDate.parse(parsingStr, formatter);
                return new ParsedDateTime(date);
            } catch (DateTimeParseException e) {
                // ignore and continue
            }
        }
        // try to parse as LocalTime
        for (DateTimeFormatter formatter : TIME_FORMATS) {
            try {
                LocalTime time = LocalTime.parse(parsingStr, formatter);
                return new ParsedDateTime(time);
            } catch (DateTimeParseException e) {
                // ignore and continue
            }
        }
        return null; // if parsing fails
    }

    /**
     * Appends the appropriate English ordinal suffix (st, nd, rd, th) to a numeric day of the month.
     * Enhances the UI display logic so dates read naturally (e.g., "1st" instead of "1").
     *
     * @param dayOfMonth The integer value of the day (1-31).
     * @return The day formatted as a string with its ordinal suffix.
     */
    private static String getDayWithOrdinalSuffix(int dayOfMonth) {
        if (dayOfMonth >= 11 && dayOfMonth <= 13) {
            return dayOfMonth + "th";
        }
        switch (dayOfMonth % 10) {
        case 1:  return dayOfMonth + "st";
        case 2:  return dayOfMonth + "nd";
        case 3:  return dayOfMonth + "rd";
        default: return dayOfMonth + "th";
        }
    }

    /**
     * Translates a complete date and time object into a human-readable string with ordinal suffixes.
     * Standardizes how complex deadlines are presented in the user-facing console.
     *
     * @param dateTime The {@code LocalDateTime} object to be formatted.
     * @return The formatted string (e.g., "1st of January 2026, 2:00pm").
     */
    public static String formatDateTimeWithOrdinal(LocalDateTime dateTime) {
        String dayWithSuffix = getDayWithOrdinalSuffix(dateTime.getDayOfMonth());
        return dayWithSuffix + " of " + dateTime.format(DISPLAY_DATE_FORMAT) + ", " + dateTime.format(DISPLAY_TIME_FORMAT).toLowerCase();
    }

    /**
     * Translates a date-only object into a human-readable string with ordinal suffixes.
     * Standardizes how daily deadlines are presented in the user-facing console.
     *
     * @param date The {@code LocalDate} object to be formatted.
     * @return The formatted string (e.g., "1st of January 2026").
     */
    public static String formatDateWithOrdinal(LocalDate date) {
        String dayWithSuffix = getDayWithOrdinalSuffix(date.getDayOfMonth());
        return dayWithSuffix + " of " + date.format(DISPLAY_DATE_FORMAT);
    }

    /**
     * Translates a time-only object into a human-readable string.
     * Standardizes how intraday deadlines are presented in the user-facing console.
     *
     * @param time The {@code LocalTime} object to be formatted.
     * @return The formatted string (e.g., "2:00pm").
     */
    public static String formatTime(LocalTime time) {
        return time.format(DISPLAY_TIME_FORMAT).toLowerCase();
    }

    @Override
    public String toString() {
        return " [D]" + super.toString() + " (by: " +
                (dueDateTime != null ? dueDateTime.format() : by) + ")";
    }

    @Override
    public String toDataString() {
        return "D | " + getStatusForStorage() + " | " + description + " | " + originalBy;
    }

    /**
     * An additional wrapper class for Java's various temporal objects.
     * Centralizes the formatting logic so the main class does not need to constantly check whether
     * the user provided a full date-time, just a date, or just a time.
     */
    private static class ParsedDateTime {
        private LocalDateTime dateTime; // both date and time
        private LocalDate date;         // only date
        private LocalTime time;         // only time

        public ParsedDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public ParsedDateTime(LocalDate date) {
            this.date = date;
        }

        public ParsedDateTime(LocalTime time) {
            this.time = time;
        }

        public String format() {
            if (dateTime != null) {
                return formatDateTimeWithOrdinal(dateTime);
            } else if (date != null) {
                return formatDateWithOrdinal(date);
            } else if (time != null) {
                return formatTime(time);
            } else {
                return "";
            }
        }
    }
}
