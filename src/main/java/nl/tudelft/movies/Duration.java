package nl.tudelft.movies;

import java.util.Objects;

/**
 * The playtime of a media item, stored as a number of hours and minutes.
 *
 * <p>For a movie this represents the length of the movie; for a series this
 * represents the length of a single episode.</p>
 */
public class Duration {

    private static final int MINUTES_PER_HOUR = 60;

    private final int hours;
    private final int minutes;

    /**
     * Creates a duration.
     *
     * @param hours   the number of whole hours (must be zero or positive)
     * @param minutes the number of minutes (must be between 0 and 59 inclusive)
     * @throws IllegalArgumentException if the values are out of range
     */
    public Duration(int hours, int minutes) {
        if (hours < 0 || minutes < 0 || minutes >= MINUTES_PER_HOUR) {
            throw new IllegalArgumentException(
                    "Invalid duration: " + hours + ":" + minutes);
        }
        this.hours = hours;
        this.minutes = minutes;
    }

    /**
     * Parses a duration from a {@code "H:MM"} formatted string such as
     * {@code "1:54"} or {@code "0:25"}.
     *
     * @param text the text to parse
     * @return the parsed duration
     * @throws IllegalArgumentException if the text is not in {@code "H:MM"} format
     */
    public static Duration parse(String text) {
        String[] parts = text.trim().split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid duration format: " + text);
        }
        try {
            int hours = Integer.parseInt(parts[0].trim());
            int minutes = Integer.parseInt(parts[1].trim());
            return new Duration(hours, minutes);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid duration format: " + text, e);
        }
    }

    /**
     * Returns the number of whole hours.
     *
     * @return the hours
     */
    public int getHours() {
        return hours;
    }

    /**
     * Returns the number of minutes (0-59).
     *
     * @return the minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Returns the total duration expressed in minutes.
     *
     * @return the total number of minutes
     */
    public int totalMinutes() {
        return hours * MINUTES_PER_HOUR + minutes;
    }

    /**
     * Formats this duration in a human-readable way, for example
     * {@code "1 hour and 54 minutes"} or {@code "25 minutes"}.
     *
     * @return a human-readable representation
     */
    public String format() {
        StringBuilder builder = new StringBuilder();
        if (hours > 0) {
            builder.append(hours).append(hours == 1 ? " hour" : " hours");
        }
        if (minutes > 0) {
            if (builder.length() > 0) {
                builder.append(" and ");
            }
            builder.append(minutes).append(minutes == 1 ? " minute" : " minutes");
        }
        if (builder.length() == 0) {
            builder.append("0 minutes");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Duration duration = (Duration) o;
        return hours == duration.hours && minutes == duration.minutes;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(hours);
        result = 31 * result + Integer.hashCode(minutes);
        return result;
    }

    @Override
    public String toString() {
        return "Duration{hours=" + hours + ", minutes=" + minutes + "}";
    }
}
