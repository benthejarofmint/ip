package cheriie.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a trackable task that spans a specific duration with a clear start and end point.
 * Extends the base Task class to support complex date and time parsing for both time boundaries,
 * allowing for flexible user input formats.
 */
public class Event extends Task {

    protected String start;
    protected String end;
    protected String originalStart;
    protected String originalEnd;
    protected ParsedDateTime startDateTime;
    protected ParsedDateTime endDateTime;

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
            // include to handle hours only without minutes
            DateTimeFormatter.ofPattern("d/M/yyyy ha"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy ha"),
            DateTimeFormatter.ofPattern("d-M-yyyy ha"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy ha"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd ha"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy ha"),
    };

    private static final DateTimeFormatter[] DATE_FORMATS = {
            // date only with various separators
            DateTimeFormatter.ofPattern("d/M/yyyy"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("d-M-yyyy"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy"),
    };

    private static final DateTimeFormatter[] TIME_FORMATS = {
            // time only in various formats
            DateTimeFormatter.ofPattern("Hmm"),
            DateTimeFormatter.ofPattern("HHmm"),
            DateTimeFormatter.ofPattern("H:mm"),
            DateTimeFormatter.ofPattern("HH:mm"),
            DateTimeFormatter.ofPattern("h:mma"),
    };

    // basic formats for displaying
    private static final DateTimeFormatter DISPLAY_DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM yyyy");
    private static final DateTimeFormatter DISPLAY_TIME_FORMAT = DateTimeFormatter.ofPattern("h:mma");

    /**
     * Attempts to convert a raw string into a structured temporal object against predefined format arrays.
     * Grants the application high flexibility, allowing users to input event boundaries in whichever format they prefer without crashing.
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
        return null; // return null if parsing fails
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
     * Standardizes how complex event boundaries are presented in the user-facing console.
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
     * Standardizes how multi-day event boundaries are presented in the user-facing console.
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
     * Standardizes how intraday event boundaries are presented in the user-facing console.
     *
     * @param time The {@code LocalTime} object to be formatted.
     * @return The formatted string (e.g., "2:00pm").
     */
    public static String formatTime(LocalTime time) {
        return time.format(DISPLAY_TIME_FORMAT).toLowerCase();
    }

    /**
     * Initializes a new event task and attempts to interpret its dual time constraints.
     * Preserves the raw user input while parsing it into structured date objects for better UI formatting.
     *
     * @param description The core text detailing the nature of the event.
     * @param start The raw string representing the event's starting boundary.
     * @param end The raw string representing the event's concluding boundary.
     */
    public Event(String description, String start, String end) {
        super(description);
        this.startDateTime = parseDateTime(start);
        this.endDateTime = parseDateTime(end);
        this.originalStart = start;
        this.originalEnd = end;
        if (this.startDateTime == null) {
            this.start = start;
        }
        if (this.endDateTime == null) {
            this.end = end;
        }
    }

    @Override
    public String toString() {
        return " [E]" + super.toString() + " (from: "
                + (startDateTime != null ? startDateTime.format() : start) + " --> to: "
                + (endDateTime != null ? endDateTime.format() : end) + ")";
    }

    @Override
    public String toDataString() {
        return "E | " + getStatusForStorage() + " | " + description + " | "
                + originalStart + " | " + originalEnd;
    }

    private static class ParsedDateTime {
        private LocalDateTime dateTime; // Both date and time
        private LocalDate date;         // Only date
        private LocalTime time;         // Only time

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
