package nl.tudelft.movies;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Loads media items from a text file in the {@code actors.txt} format and turns
 * them into a list of {@link MediaItem} objects.
 *
 * <p>Each item in the file is a block of lines in a fixed order:</p>
 * <pre>
 * ENTRY MOVIE &lt;title&gt;; &lt;genre&gt;[, &lt;genre&gt;...]; &lt;content rating&gt;
 * DURATION &lt;H:MM&gt;                         (movie: playtime)
 * BOX OFFICE &lt;amount&gt; million              (movies only)
 * CAST &lt;name&gt;; &lt;role&gt;                      (one or more lines)
 * </pre>
 * <p>or, for a series:</p>
 * <pre>
 * ENTRY SERIES &lt;title&gt;; &lt;genre&gt;[, &lt;genre&gt;...]; &lt;content rating&gt;
 * DURATION &lt;episodes&gt; episodes; &lt;H:MM&gt;     (series: episode count + per-episode playtime)
 * CAST &lt;name&gt;; &lt;role&gt;                      (one or more lines)
 * </pre>
 */
public final class MediaFileLoader {

    private static final String ENTRY_PREFIX = "ENTRY ";
    private static final String DURATION_PREFIX = "DURATION ";
    private static final String BOX_OFFICE_PREFIX = "BOX OFFICE ";
    private static final String CAST_PREFIX = "CAST ";

    private MediaFileLoader() {
        // Utility class; not meant to be instantiated.
    }

    /**
     * Reads all media items from the given file.
     *
     * <p>This method directly interacts with a file and is therefore a thin
     * wrapper around {@link #parse(List)}, which contains the actual parsing
     * logic.</p>
     *
     * @param filePath the path to the input file
     * @return the list of media items read from the file
     * @throws FileNotFoundException if the file does not exist
     */
    public static List<MediaItem> load(String filePath) throws FileNotFoundException {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        }
        return parse(lines);
    }

    /**
     * Parses a list of raw text lines into media items.
     *
     * <p>Blank lines between items are ignored. Items may appear in any order
     * in the file, but the lines within a single item follow the fixed order
     * described in the class documentation.</p>
     *
     * @param lines the raw lines of the input file
     * @return the parsed media items
     * @throws IllegalArgumentException if the content does not follow the expected format
     */
    public static List<MediaItem> parse(List<String> lines) {
        List<MediaItem> items = new ArrayList<>();
        int index = 0;
        while (index < lines.size()) {
            String line = lines.get(index).trim();
            if (line.isEmpty()) {
                index++;
                continue;
            }
            if (!line.startsWith(ENTRY_PREFIX)) {
                throw new IllegalArgumentException(
                        "Expected an ENTRY line but found: " + line);
            }

            List<String> block = new ArrayList<>();
            block.add(line);
            index++;
            // Collect lines until the next ENTRY or end of file.
            while (index < lines.size()) {
                String next = lines.get(index).trim();
                if (next.isEmpty()) {
                    index++;
                    continue;
                }
                if (next.startsWith(ENTRY_PREFIX)) {
                    break;
                }
                block.add(next);
                index++;
            }
            items.add(parseItem(block));
        }
        return items;
    }

    /**
     * Parses a single item from the lines belonging to one block.
     *
     * @param block the lines of a single item, starting with its ENTRY line
     * @return the parsed media item
     * @throws IllegalArgumentException if the block does not follow the expected format
     */
    private static MediaItem parseItem(List<String> block) {
        String entryLine = block.get(0).substring(ENTRY_PREFIX.length()).trim();
        int firstSpace = entryLine.indexOf(' ');
        if (firstSpace < 0) {
            throw new IllegalArgumentException("Malformed ENTRY line: " + block.get(0));
        }
        String type = entryLine.substring(0, firstSpace).trim();
        String rest = entryLine.substring(firstSpace + 1).trim();

        String[] parts = rest.split(";");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Malformed ENTRY line: " + block.get(0));
        }
        String title = parts[0].trim();
        List<String> genres = parseGenres(parts[1]);
        ContentRating rating = ContentRating.fromLabel(parts[2]);

        if ("MOVIE".equalsIgnoreCase(type)) {
            return parseMovie(title, genres, rating, block);
        } else if ("SERIES".equalsIgnoreCase(type)) {
            return parseSeries(title, genres, rating, block);
        }
        throw new IllegalArgumentException("Unknown item type: " + type);
    }

    /**
     * Parses a movie from an item block.
     *
     * @param title  the title
     * @param genres the genres
     * @param rating the content rating
     * @param block  the lines of the item
     * @return the parsed movie
     */
    private static Movie parseMovie(String title, List<String> genres,
                                    ContentRating rating, List<String> block) {
        Duration duration = null;
        Long boxOffice = null;
        List<CastMember> cast = new ArrayList<>();

        for (int i = 1; i < block.size(); i++) {
            String line = block.get(i);
            if (line.startsWith(DURATION_PREFIX)) {
                duration = Duration.parse(line.substring(DURATION_PREFIX.length()));
            } else if (line.startsWith(BOX_OFFICE_PREFIX)) {
                boxOffice = parseBoxOffice(line.substring(BOX_OFFICE_PREFIX.length()));
            } else if (line.startsWith(CAST_PREFIX)) {
                cast.add(parseCast(line.substring(CAST_PREFIX.length())));
            }
        }

        if (duration == null || boxOffice == null || cast.isEmpty()) {
            throw new IllegalArgumentException(
                    "Incomplete movie definition for: " + title);
        }
        return new Movie(title, genres, rating, duration, boxOffice, cast);
    }

    /**
     * Parses a series from an item block.
     *
     * @param title  the title
     * @param genres the genres
     * @param rating the content rating
     * @param block  the lines of the item
     * @return the parsed series
     */
    private static Series parseSeries(String title, List<String> genres,
                                      ContentRating rating, List<String> block) {
        Duration episodeDuration = null;
        int episodes = -1;
        List<CastMember> cast = new ArrayList<>();

        for (int i = 1; i < block.size(); i++) {
            String line = block.get(i);
            if (line.startsWith(DURATION_PREFIX)) {
                String value = line.substring(DURATION_PREFIX.length());
                String[] durationParts = value.split(";");
                if (durationParts.length != 2) {
                    throw new IllegalArgumentException(
                            "Malformed series DURATION line: " + line);
                }
                episodes = parseEpisodes(durationParts[0]);
                episodeDuration = Duration.parse(durationParts[1]);
            } else if (line.startsWith(CAST_PREFIX)) {
                cast.add(parseCast(line.substring(CAST_PREFIX.length())));
            }
        }

        if (episodeDuration == null || episodes < 0 || cast.isEmpty()) {
            throw new IllegalArgumentException(
                    "Incomplete series definition for: " + title);
        }
        return new Series(title, genres, rating, episodeDuration, episodes, cast);
    }

    /**
     * Parses a semicolon/comma separated list of genres into a list of trimmed
     * genre names.
     *
     * @param text the raw genre text, for example {@code " Adventure, Comedy, Fantasy"}
     * @return the parsed genres
     */
    private static List<String> parseGenres(String text) {
        List<String> genres = new ArrayList<>();
        for (String genre : text.split(",")) {
            String trimmed = genre.trim();
            if (!trimmed.isEmpty()) {
                genres.add(trimmed);
            }
        }
        if (genres.isEmpty()) {
            throw new IllegalArgumentException("An item must have at least one genre");
        }
        return genres;
    }

    /**
     * Parses a {@code "CAST"} value such as {@code "Margot Robbie; Barbie"} into
     * a {@link CastMember}.
     *
     * @param text the raw cast text (without the {@code CAST } prefix)
     * @return the parsed cast member
     */
    private static CastMember parseCast(String text) {
        String[] parts = text.split(";");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Malformed CAST line: " + text);
        }
        return new CastMember(parts[0].trim(), parts[1].trim());
    }

    /**
     * Parses a box office value such as {@code "1442 million"} into a number of
     * millions.
     *
     * @param text the raw box office text (without the {@code BOX OFFICE } prefix)
     * @return the box office revenue in millions
     */
    private static long parseBoxOffice(String text) {
        String digits = text.trim().replaceAll("[^0-9]", "");
        if (digits.isEmpty()) {
            throw new IllegalArgumentException("Malformed BOX OFFICE line: " + text);
        }
        return Long.parseLong(digits);
    }

    /**
     * Parses the number of episodes from a value such as {@code "153 episodes"}.
     *
     * @param text the raw episodes text
     * @return the number of episodes
     */
    private static int parseEpisodes(String text) {
        String digits = text.trim().replaceAll("[^0-9]", "");
        if (digits.isEmpty()) {
            throw new IllegalArgumentException("Malformed episodes value: " + text);
        }
        return Integer.parseInt(digits);
    }
}
