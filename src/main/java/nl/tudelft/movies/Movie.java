package nl.tudelft.movies;

import java.util.List;
import java.util.Objects;

/**
 * A movie: a media item with a box office revenue (in millions) in addition to
 * the common attributes defined by {@link MediaItem}.
 */
public class Movie extends MediaItem {

    private final long boxOfficeMillions;

    /**
     * Creates a movie.
     *
     * @param title             the title of the movie
     * @param genres            one or more genres
     * @param contentRating     the content rating
     * @param duration          the duration of the movie
     * @param boxOfficeMillions the box office revenue in millions
     * @param cast              one or more cast members
     */
    public Movie(String title, List<String> genres, ContentRating contentRating,
                 Duration duration, long boxOfficeMillions, List<CastMember> cast) {
        super(title, genres, contentRating, duration, cast);
        this.boxOfficeMillions = boxOfficeMillions;
    }

    /**
     * Returns the box office revenue of the movie in millions.
     *
     * @return the box office revenue in millions
     */
    public long getBoxOfficeMillions() {
        return boxOfficeMillions;
    }

    @Override
    public String format() {
        return "Movie: " + getTitle()
                + " - Genres: " + formatGenres()
                + ". Maturity rating: " + getContentRating().getLabel()
                + System.lineSeparator()
                + "Duration: " + getDuration().format()
                + System.lineSeparator()
                + "Box office revenue: " + boxOfficeMillions + " million"
                + System.lineSeparator()
                + "Cast members: " + formatCast();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Movie movie = (Movie) o;
        return boxOfficeMillions == movie.boxOfficeMillions;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Long.hashCode(boxOfficeMillions);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{title='" + getTitle() + "', boxOfficeMillions="
                + boxOfficeMillions + "}";
    }
}
