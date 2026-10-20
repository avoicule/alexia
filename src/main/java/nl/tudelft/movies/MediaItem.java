package nl.tudelft.movies;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base type for anything that can be consumed in the catalog, such as a
 * {@link Movie} or a {@link Series}.
 *
 * <p>A media item always has a title, one or more genres, a content rating, a
 * duration and one or more cast members. Concrete subclasses add their own
 * specific attributes (for example the box office revenue of a movie or the
 * number of episodes of a series) and define how they are formatted for
 * display.</p>
 */
public abstract class MediaItem {

    private final String title;
    private final List<String> genres;
    private final ContentRating contentRating;
    private final Duration duration;
    private final List<CastMember> cast;

    /**
     * Creates a media item with its shared attributes.
     *
     * @param title         the title of the item
     * @param genres        one or more genres describing the item
     * @param contentRating the content rating of the item
     * @param duration      the playtime of the item (per episode for a series)
     * @param cast          one or more cast members
     */
    protected MediaItem(String title, List<String> genres,
                        ContentRating contentRating, Duration duration,
                        List<CastMember> cast) {
        this.title = title;
        this.genres = new ArrayList<>(genres);
        this.contentRating = contentRating;
        this.duration = duration;
        this.cast = new ArrayList<>(cast);
    }

    /**
     * Returns the title of the item.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns an unmodifiable view of the genres of the item.
     *
     * @return the genres
     */
    public List<String> getGenres() {
        return List.copyOf(genres);
    }

    /**
     * Returns the content rating of the item.
     *
     * @return the content rating
     */
    public ContentRating getContentRating() {
        return contentRating;
    }

    /**
     * Returns the duration of the item (per episode for a series).
     *
     * @return the duration
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Returns an unmodifiable view of the cast members of the item.
     *
     * @return the cast
     */
    public List<CastMember> getCast() {
        return List.copyOf(cast);
    }

    /**
     * Indicates whether this item has the given genre (case-insensitive).
     *
     * @param genre the genre to look for
     * @return {@code true} if one of this item's genres matches
     */
    public boolean hasGenre(String genre) {
        for (String g : genres) {
            if (g.equalsIgnoreCase(genre.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Formats the comma-separated list of genres, for example
     * {@code "Adventure, Comedy, Fantasy"}.
     *
     * @return the formatted genres
     */
    protected String formatGenres() {
        return String.join(", ", genres);
    }

    /**
     * Formats the comma-separated list of cast members, for example
     * {@code "Margot Robbie (Barbie), Ryan Gosling (Ken)"}.
     *
     * @return the formatted cast
     */
    protected String formatCast() {
        List<String> formatted = new ArrayList<>();
        for (CastMember member : cast) {
            formatted.add(member.format());
        }
        return String.join(", ", formatted);
    }

    /**
     * Formats this item into a nicely readable, multi-line representation for
     * display to the user. Implementations must not rely on {@link #toString()}.
     *
     * @return the formatted, human-readable representation
     */
    public abstract String format();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MediaItem that = (MediaItem) o;
        return Objects.equals(title, that.title)
                && Objects.equals(genres, that.genres)
                && contentRating == that.contentRating
                && Objects.equals(duration, that.duration)
                && Objects.equals(cast, that.cast);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(title);
        result = 31 * result + Objects.hashCode(genres);
        result = 31 * result + Objects.hashCode(contentRating);
        result = 31 * result + Objects.hashCode(duration);
        result = 31 * result + Objects.hashCode(cast);
        return result;
    }
}
